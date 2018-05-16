package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.entity.vo.RpcUser;
import com.hklk.oplatform.service.AuthenticationRpcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("authenticationRpcService")
public class AuthenticationRpcServiceImpl implements AuthenticationRpcService {

    @Resource
    private TokenManager tokenManager;

    @Override
    public boolean validate(String token) {
        return tokenManager.validate(token) != null;
    }

    @Override
    public RpcUser findAuthInfo(String token) {
        LoginUser user = tokenManager.validate(token);
        if (user != null) {
            return new RpcUser(user.getAccount());
        }
        return null;
    }


}
