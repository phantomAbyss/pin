package com.pinche.infrastructure.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Component
@CacheConfig(cacheNames = "templateMap")
public class TemplateCache {
    @CachePut(key = "#p0")
    public String setId(Integer userId, String templateId) {
        return templateId;
    }

    @Cacheable(key = "#p0")
    public String getTemplateId(Integer userId) {
        return null;
    }
}
