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


    private String userTokenKey = "userToken:";
    /**
     * 是否需要扩展token过期时间
     */
    private Set<String> tokenSet = new CopyOnWriteArraySet<String>();

    public void addToken(String token, LoginUser loginUser) {
        RedisCache.putValue(userTokenKey + token, JsonUtil.toJson(loginUser), tokenTimeout);
    }

    public LoginUser validate(String token) {
        String json = RedisCache.get(userTokenKey + token);
        return JsonUtil.jsonToBean(json, LoginUser.class);
    }

    public void remove(String token) {
        RedisCache.remove(userTokenKey + token);
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
