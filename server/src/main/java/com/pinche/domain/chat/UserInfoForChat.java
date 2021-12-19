package com.pinche.domain.chat;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class UserInfoForChat {
    private String nickName;
    private String avatarUrl;
    private Byte gender;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserInfoForChat{" +
                "nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                '}';
    }
}
