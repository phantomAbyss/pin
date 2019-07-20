package com.yangkw.pin.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.yangkw.pin.domain.address.GeoAddress;
import com.yangkw.pin.domain.address.PublishResult;
import com.yangkw.pin.domain.order.OrderVO;
import com.yangkw.pin.domain.order.OrderDO;
import com.yangkw.pin.domain.order.TimeDTO;
import com.yangkw.pin.domain.relation.UserOrderRelDO;
import com.yangkw.pin.domain.request.AdviceOrderRequest;
import com.yangkw.pin.domain.request.FuzzyOrderRequest;
import com.yangkw.pin.domain.request.PartnerOrderRequest;
import com.yangkw.pin.domain.request.PublishOrderRequest;
import com.yangkw.pin.infrastructure.cache.OrderCache;
import com.yangkw.pin.infrastructure.cache.RedisLock;
import com.yangkw.pin.infrastructure.cache.TemplateCache;
import com.yangkw.pin.infrastructure.repository.OrderRepository;
import com.yangkw.pin.infrastructure.repository.UserOrderRelRepository;
import com.yangkw.pin.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类OrderService.java
 *
 * @author kaiwen.ykw 2018-12-27
 */
@Service
public class OrderService {
    private static Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private CacheService cacheService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserOrderRelRepository userOrderRelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WxMaService wxMaService;
    @Value("${templateId}")
    private String templateId;
    @Autowired
    private TemplateCache templateCache;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private OrderCache orderCache;

    public List<OrderVO> findOrderList(FuzzyOrderRequest request) {
        List<Integer> orderIds = cacheService.findNearOrderId(request);
        if (orderIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<OrderDO> orderDOList = orderRepository.findAll(orderIds);
        if (orderIds.isEmpty()) {
            LOG.error("orderDOList is null,orderIds :{}", orderIds);
            return Collections.emptyList();
        }
        return orderDOList.stream().map(this::assemble).collect(Collectors.toList());
    }

    public List<OrderVO> findOwnOrderList(String token) {
        Integer userId = cacheService.getUserId(token);

        List<Integer> orderIdList = userOrderRelRepository.queryOwnOrderList(userId);
        if (orderIdList.isEmpty()) {
            return Collections.emptyList();
        }
        List<OrderDO> orderDOList = orderRepository.findAll(orderIdList);
        LocalDateTime deadLine = LocalDateTime.now().plusHours(1);
        Iterator<OrderDO> iterator = orderDOList.iterator();
        while (iterator.hasNext()) {
            OrderDO temp = iterator.next();
            if (temp.getTargetTime().isBefore(deadLine)) {
                Integer id = temp.getId();
                userOrderRelRepository.logicDelete(id, userId);
                orderRepository.logicDelete(id);
                iterator.remove();
            }
        }
        return orderDOList.stream().map(o -> assembleLeader(o, userId)).collect(Collectors.toList());
    }

    public OrderVO findOrder(Integer id, Integer userId) {
        OrderDO orderDO = orderRepository.find(id);
        return assembleLeader(orderDO, userId);
    }

    public Boolean publish(PublishOrderRequest request) {
        Integer userId = cacheService.getUserId(request.getToken());
        boolean condition = redisLock.getLock(userId);
        if (!condition) {
            return false;
        }
        PublishResult publishResult = addressService.publish(request.getStartAddress(), request.getEndAddress());
        OrderDO orderDO = assemble(request, publishResult, userId);
        if (orderDO.getTargetTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        orderRepository.insert(orderDO);
        userOrderRelRepository.insert(construct(orderDO.getId(), userId, true));
        cacheService.publishCache(request, orderDO.getId());
        return true;
    }

    public Boolean join(PartnerOrderRequest request) {
        Integer orderId = request.getOrderId();
        Integer userId = cacheService.getUserId(request.getToken());
        Integer exitRow = userOrderRelRepository.exit(orderId, userId);
        if (exitRow != null) {
            return false;
        }
        orderRepository.addCurrentNum(orderId);
        userOrderRelRepository.insert(construct(orderId, userId, false));
        orderCache.clear(orderId);
        notifyService(orderId, userId);
        return true;
    }

    public void cancel(PartnerOrderRequest request) {
        Integer orderId = request.getOrderId();
        Integer userId = cacheService.getUserId(request.getToken());
        orderRepository.delCurrentNum(orderId);
        OrderDO orderDO = orderRepository.find(orderId);
        if (orderDO.getCurrentNum() < 1) {
            orderRepository.logicDelete(orderId);
        } else {
            boolean isLeader = userOrderRelRepository.isLeader(orderId, userId) != null;
            if (isLeader) {
                List<Integer> ids = userOrderRelRepository.queryPartner(orderId);
                Integer id = ids.stream().filter(x -> !x.equals(userId)).findFirst().get();
                LOG.info("update leader orderId:{}, newId:{},oldId:{}", orderId, id, userId);
                userOrderRelRepository.updateLeader(orderId, id);
                userOrderRelRepository.cancelMember(orderId, id);
                orderRepository.updateLeader(orderId, id);
            }
        }
        orderCache.clear(orderId);
        userOrderRelRepository.logicDelete(orderId, userId);
    }

    public List<OrderVO> adviceOrderS(AdviceOrderRequest request) {
        List<Integer> orderIds = cacheService.startAdvice(request.getDot());
        if (orderIds.isEmpty()) {
            return Collections.emptyList();
        }
        return orderRepository.findAll(orderIds).stream().map(this::assemble).collect(Collectors.toList());
    }

    private UserOrderRelDO construct(Integer orderId, Integer userId, Boolean leader) {
        UserOrderRelDO userOrderRelDO = new UserOrderRelDO();
        userOrderRelDO.setOrderId(orderId);
        userOrderRelDO.setUserId(userId);
        userOrderRelDO.setLeader(leader);
        userOrderRelDO.setDeleted(false);
        return userOrderRelDO;
    }

    private OrderDO assemble(PublishOrderRequest request, PublishResult result, Integer userId) {
        OrderDO orderDO = new OrderDO();
        TimeDTO time = request.getTimeDTO();
        orderDO.setStartAddressId(result.getStartId());
        orderDO.setEndAddressId(result.getEndId());
        orderDO.setCreator(userId);
        orderDO.setLeader(userId);
        orderDO.setTargetTime(LocalDateTime.of(time.getYear(), time.getMonth(), time.getDay(), time.getHour(), time.getMinute()));
        orderDO.setTargetNum(request.getTargetNum());
        orderDO.setCurrentNum(1);
        orderDO.setDeleted(false);
        return orderDO;
    }

    private OrderVO assembleLeader(OrderDO orderDO, Integer userId) {
        OrderVO orderVO = assemble(orderDO);
        orderVO.setLeader(orderDO.getLeader().equals(userId));
        return orderVO;
    }

    private OrderVO assemble(OrderDO orderDO) {
        return orderCache.getOrder(orderDO);
    }

    @Async
    public void notifyService(Integer orderId, Integer userId) {
        OrderDO orderDO = orderRepository.find(orderId);
        if (orderDO.getLeader().equals(userId)) {
            return;
        }
        List<Integer> userIds = userOrderRelRepository.queryPartner(orderId);
        if (userIds.isEmpty()) {
            return;
        }
        GeoAddress startAddress = addressService.queryGeoAddress(orderDO.getStartAddressId());
        GeoAddress endAddress = addressService.queryGeoAddress(orderDO.getEndAddressId());
        String title = startAddress.getName() + " ==> " + endAddress.getName() + "的拼车队伍";
        String name = userRepository.findChatInfo(userId).getNickName();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String warn = "请尽快与拼车友联系哦 ^_^!";
        String[] params = new String[]{title, name, time, warn};
        userIds.stream().filter(x -> !x.equals(userId)).forEach(x -> {
                    templateNotify(userRepository.findOpenId(x), templateCache.getTemplateId(x), params);
                }
        );
    }

    private void templateNotify(String receiver, String formId, String[] params) {
        if (formId == null) {
            LOG.info("formId is null receiver:{}", receiver);
            return;
        }
        WxMaTemplateMessage msg = new WxMaTemplateMessage();
        msg.setTemplateId(templateId);
        msg.setToUser(receiver);
        msg.setFormId(formId);
        msg.setData(createMsgData(params));
        try {
            wxMaService.getMsgService().sendTemplateMsg(msg);
        } catch (Exception e) {
            LOG.error("send Template Message error,receiver:{}", receiver);
        }
    }

    private List<WxMaTemplateData> createMsgData(String[] parms) {
        List<WxMaTemplateData> dataList = new ArrayList<>();
        for (int i = 1; i <= parms.length; i++) {
            dataList.add(new WxMaTemplateData("keyword" + i, parms[i - 1]));
        }
        return dataList;
    }
}
