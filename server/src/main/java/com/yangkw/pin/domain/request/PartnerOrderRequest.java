package com.yangkw.pin.domain.request;

import javax.validation.constraints.NotNull;

/**
 * 类JoinOrderRequest.java
 *
 * @author kaiwen.ykw 2019-01-01
 */
public class PartnerOrderRequest extends BaseRequest {
    @NotNull(message = "orderId can't null")
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
