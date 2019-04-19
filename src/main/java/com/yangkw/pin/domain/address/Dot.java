package com.yangkw.pin.domain.address;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 类Point.java
 *
 * @author kaiwen.ykw 2018-12-27
 */
@Data
@AllArgsConstructor
public class Dot implements Serializable {
    private static final long serialVersionUID = 779891169234313335L;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
}
