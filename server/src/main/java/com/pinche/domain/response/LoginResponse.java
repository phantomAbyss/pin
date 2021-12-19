package com.pinche.domain.response;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class LoginResponse extends BaseResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
