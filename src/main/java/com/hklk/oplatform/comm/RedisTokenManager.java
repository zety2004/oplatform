package com.hklk.oplatform.comm;

import com.hklk.oplatform.comm.cache.RedisCache;
import com.hklk.oplatform.util.JsonUtil;
import jdk.nashorn.internal.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 单实例环境令牌管理
 */
public class RedisTokenManager extends TokenManager {


    /**
     * 是否需要扩展token过期时间
     */
    private Set<String> tokenSet = new CopyOnWriteArraySet<String>();

    public void addToken(String key, String token, Object obj) {
        RedisCache.putValue(key + token, JsonUtil.toJson(obj), tokenTimeout);
    }

    public LoginUser validate(String key, String token) {
        String json = RedisCache.get(key + token);
        return JsonUtil.jsonToBean(json, LoginUser.class);
    }

    public void remove(String key, String token) {
        RedisCache.remove(key + token);
    }

    @Override
    public void setTokenTimeout(int tokenTimeout) {
        super.setTokenTimeout(tokenTimeout);
    }

    @Override
    public void verifyExpired() {
        tokenSet.clear();
    }
}