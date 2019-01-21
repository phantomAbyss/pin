package com.yangkw.pin.service.util;

import com.yangkw.pin.domain.BaseResponse;

/**
 * 类ResponseUtil.java的实现描述：TODO
 *
 * @author kaiwen.ykw 2018-12-22
 */
public class ResponseUtil {
    private ResponseUtil() {
    }

    public static BaseResponse errorResponse(String errorMessage) {
        BaseResponse response = new BaseResponse();
        response.setMessage(errorMessage);
        response.setSuccess(false);
        return response;
    }
}
