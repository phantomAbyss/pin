package com.pinche.domain.request;

import javax.validation.constraints.NotNull;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class OrderRequest extends BaseRequest {
    @NotNull(message = "orderId is null")
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
