package com.pinche.service.util;

import com.pinche.domain.response.BaseResponse;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class ResponseUtil {
    private ResponseUtil() {
    }

    public static <T extends BaseResponse> T errorResponse(T response, String errorMessage) {
        response.setMessage(errorMessage);
        response.setSuccess(false);
        return response;
    }
}
