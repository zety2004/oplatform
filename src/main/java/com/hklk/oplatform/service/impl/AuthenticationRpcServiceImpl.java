package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.comm.LoginUser;
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
    public boolean validate(String key, String token) {
        return tokenManager.validate(key, token) != null;
    }

    @Override
    public Object findAuthInfo(String key, String token) {
        Object obj = tokenManager.validate(key, token);
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
