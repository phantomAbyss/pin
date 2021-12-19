package com.pinche.domain.chat;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class MessageDO {
    private Integer id;
    private Integer userId;
    private String time;
    private String message;
    private Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "MessageDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
