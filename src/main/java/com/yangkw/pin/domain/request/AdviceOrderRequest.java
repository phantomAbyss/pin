package com.yangkw.pin.domain.request;

import com.yangkw.pin.domain.address.Dot;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类AdviceOrderRequest.java 的实现描述：
 *
 * @author kaiwen.ykw 2019-04-10
 */
@Data
@EqualsAndHashCode
public class AdviceOrderRequest extends BaseRequest{
    private Dot dot;
}
