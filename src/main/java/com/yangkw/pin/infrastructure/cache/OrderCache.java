package com.yangkw.pin.infrastructure.cache;

import com.yangkw.pin.domain.order.Order;
import com.yangkw.pin.domain.order.OrderDO;
import com.yangkw.pin.infrastructure.repository.UserRepository;
import com.yangkw.pin.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 类OrderCache.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-04-19
 */
@Component
@CacheConfig(cacheNames = "OrderMap")
public class OrderCache {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepository userRepository;

    @Cacheable(key = "#p0.id")
    public Order getOrder(OrderDO orderDO) {
        Order order = new Order();
        order.setId(orderDO.getId());
        order.setStartAddress(addressService.queryGeoAddress(orderDO.getStartAddressId()));
        order.setEndAddress(addressService.queryGeoAddress(orderDO.getEndAddressId()));
        order.setPublishTime(transfer(orderDO.getGmtCreate()));
        order.setUpdateTime(transfer(orderDO.getGmtModified()));
        order.setTargetTime(transfer(orderDO.getTargetTime()));
        order.setOrderItem(orderDO.getTargetTime());
        order.setTargetNum(orderDO.getTargetNum());
        order.setCurrentNum(orderDO.getCurrentNum());
        order.setLeaderName(userRepository.findChatInfo(orderDO.getLeader()).getNickName());
        return order;
    }

    private String transfer(LocalDateTime time) {
        return time.toString().replace("T", " ");
    }

}
