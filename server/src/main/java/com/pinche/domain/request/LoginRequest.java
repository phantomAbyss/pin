package com.pinche.domain.request;

import com.pinche.domain.user.UserInfo;

import javax.validation.constraints.NotNull;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class LoginRequest {
    @NotNull(message = "code can't null")
    private String code;
    @NotNull(message = "userInfo can't null")
    private UserInfo userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "code='" + code + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
