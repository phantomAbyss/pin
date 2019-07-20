package com.yangkw.pin.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 类BaseRequest.java的实现描述：TODO
 *
 * @author kaiwen.ykw 2018-12-26
 */
@Data
public class BaseRequest {
    @NotNull(message = "token can't null")
    private String token;
}
