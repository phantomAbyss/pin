package com.yangkw.pin.domain.address;

import java.io.Serializable;

/**
 * 类GeoAddress.java的实现描述：TODO
 *
 * @author kaiwen.ykw 2018-12-26
 */
public class GeoAddress implements Serializable {
    private static final long serialVersionUID = 6553955292738253485L;
    private String name;
    private String address;
    private Dot dot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }
}
