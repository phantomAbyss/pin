package com.yangkw.pin.domain.order;

import com.yangkw.pin.domain.address.GeoAddress;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类Order.java
 *
 * @author kaiwen.ykw 2018-12-26
 */
@Data
public class Order {
    /**
     * 是否是队长
     */
    private Boolean leader;
    /**
     * 出行单id
     */
    private Integer id;
    /**
     * 起点
     */
    private GeoAddress startAddress;
    /**
     * 终点
     */
    private GeoAddress endAddress;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 最后更新时间
     */
    private String updateTime;
    /**
     * 目标出行时间
     */
    private String targetTime;

    private LocalDateTime orderItem;
    /**
     * 队伍人数
     */
    private Integer targetNum;
    /**
     * 当前人数
     */
    private Integer currentNum;

    private String leaderName;
}
