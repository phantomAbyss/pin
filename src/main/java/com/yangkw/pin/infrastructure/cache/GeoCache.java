package com.yangkw.pin.infrastructure.cache;

import com.alibaba.fastjson.JSON;
import com.yangkw.pin.domain.address.Dot;
import com.yangkw.pin.domain.order.OrderCacheDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 类GeoCache.java
 *
 * @author kaiwen.ykw 2018-12-27
 */
@Component
public class GeoCache {
    @Autowired
    protected StringRedisTemplate redisTemplate;
    /**
     * 添加缓存
     *
     * @param isStart        起点
     * @param dot        坐标
     * @param orderCacheDO    订单DO
     */
    public void add(boolean isStart, Dot dot,OrderCacheDO orderCacheDO) {
        String key = isStart ? "start" : "end";
        GeoOperations<String, String> operations = redisTemplate.opsForGeo();
        Point point = new Point(dot.getLongitude(), dot.getLatitude());
        operations.add(key, point, JSON.toJSONString(orderCacheDO));
    }

    public List<Integer> findOrderId(Dot dot, boolean isStart) {
        String key = isStart ? "start" : "end";
        List<Integer> orderIdList = new LinkedList<>();
        GeoOperations<String, String> operations = redisTemplate.opsForGeo();
        Point point = new Point(dot.getLongitude(), dot.getLatitude());
        Distance distance = new Distance(3, RedisGeoCommands.DistanceUnit.KILOMETERS);
        Circle circle = new Circle(point, distance);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = operations.radius(key, circle);
        if (geoResults == null) {
            return Collections.emptyList();
        }
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> x : geoResults.getContent()) {
            OrderCacheDO cache = JSON.parseObject(x.getContent().getName(), OrderCacheDO.class);
            if (cache.getTargetTime() != null && cache.getTargetTime().isBefore(LocalDateTime.now())) {
                operations.remove(key, x.getContent().getName());
            } else {
                orderIdList.add(cache.getOrderId());
            }
        }
        return orderIdList;
    }

}
