package com.pinche.api;

import com.pinche.domain.order.OrderVO;
import com.pinche.domain.request.AdviceOrderRequest;
import com.pinche.domain.request.OrderRequest;
import com.pinche.domain.request.OwnOrderRequest;
import com.pinche.domain.request.PartnerOrderRequest;
import com.pinche.domain.request.PublishOrderRequest;
import com.pinche.domain.response.AdviceResponse;
import com.pinche.domain.response.FuzzyOrderResponse;
import com.pinche.domain.response.OrderResponse;
import com.pinche.service.CacheService;
import com.pinche.service.OrderService;
import com.pinche.domain.request.FuzzyOrderRequest;
import com.pinche.domain.response.BaseResponse;
import com.pinche.domain.response.OwnOrderResponse;
import com.pinche.service.annotation.ParamCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Parmaze
 * @date 2021/12/16
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CacheService cacheService;

    @PostMapping("fuzzy")
    @ParamCheck
    public FuzzyOrderResponse fuzzy(@RequestBody @Validated FuzzyOrderRequest request, BindingResult bindingResult) {
        FuzzyOrderResponse response = new FuzzyOrderResponse();
        List<OrderVO> orderList = orderService.findOrderList(request);
        response.setSuccess(true);
        response.setOrderList(orderList);
        return response;
    }

    @PostMapping("own")
    @ParamCheck
    public OwnOrderResponse own(@RequestBody @Validated OwnOrderRequest request, BindingResult bindingResult) {
        OwnOrderResponse response = new OwnOrderResponse();
        List<OrderVO> orderList = orderService.findOwnOrderList(request.getToken());
        response.setOrderList(orderList);
        response.setSuccess(true);
        return response;
    }

    @PostMapping("publish")
    @ParamCheck
    public BaseResponse publish(@RequestBody @Validated PublishOrderRequest request, BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(orderService.publish(request));
        return response;
    }

    @PostMapping("query")
    @ParamCheck
    public OrderResponse query(@RequestBody @Validated OrderRequest request, BindingResult bindingResult) {
        OrderResponse response = new OrderResponse();
        Integer userId = cacheService.getUserId(request.getToken());
        response.setOrder(orderService.findOrder(request.getOrderId(), userId));
        response.setSuccess(true);
        return response;
    }

    @PostMapping("join")
    @ParamCheck
    public BaseResponse query(@RequestBody @Validated PartnerOrderRequest request, BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(orderService.join(request));
        return response;
    }

    @PostMapping("cancel")
    @ParamCheck
    public BaseResponse cancel(@RequestBody @Validated PartnerOrderRequest request, BindingResult bindingResult) {
        BaseResponse response = new BaseResponse();
        orderService.cancel(request);
        response.setSuccess(true);
        return response;
    }
    @PostMapping("advice")
    @ParamCheck
    public AdviceResponse advice(@RequestBody @Validated AdviceOrderRequest request, BindingResult bindingResult) {
        AdviceResponse response = new AdviceResponse();
        response.setAdviceList(orderService.adviceOrderS(request));
        response.setSuccess(true);
        return response;
    }
}
