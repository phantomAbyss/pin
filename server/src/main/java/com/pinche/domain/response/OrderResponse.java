package com.pinche.domain.response;

import com.pinche.domain.order.OrderVO;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class OrderResponse extends BaseResponse {
    private OrderVO order;

    public OrderVO getOrder() {
        return order;
    }

    public void setOrder(OrderVO order) {
        this.order = order;
    }
}
