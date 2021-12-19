package com.pinche.domain.response;

import java.io.Serializable;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 6037946147158442253L;
    private String message;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
