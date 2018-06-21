package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.service.AuthenticationRpcService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("authenticationRpcService")
public class AuthenticationRpcServiceImpl implements AuthenticationRpcService {

    @Resource
    private TokenManager tokenManager;

    @Override
    public <T> boolean validate(String key, String token, Class<T> clazz) {
        return tokenManager.validate(key, token, clazz) != null;
    }

    @Override
    public <T> T findAuthInfo(String key, String token, Class<T> clazz) {
        T obj = tokenManager.validate(key, token, clazz);
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
