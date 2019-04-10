package com.yangkw.pin.domain.address;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 类Point.java
 *
 * @author kaiwen.ykw 2018-12-27
 */
@Data
@AllArgsConstructor
public class Dot {
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
}
