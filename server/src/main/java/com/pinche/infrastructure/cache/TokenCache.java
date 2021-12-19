package com.pinche.infrastructure.cache;

import com.pinche.domain.user.UserToken;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Component
@CacheConfig(cacheNames = "tokenMap")
public class TokenCache {
    @CachePut(key = "#p0")
    public UserToken generateToken(String token, Integer id, String sessionKey, String openid) {
        UserToken userToken = new UserToken();
        userToken.setSessionKey(sessionKey);
        userToken.setOpenid(openid);
        userToken.setUserId(id);
        userToken.setToken(token);
        return userToken;
    }

    @Cacheable(key = "#p0")
    public UserToken getInfo(String token) {
        return null;
    }

}
