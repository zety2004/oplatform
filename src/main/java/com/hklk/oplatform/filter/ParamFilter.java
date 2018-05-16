package com.hklk.oplatform.filter;

import com.hklk.oplatform.service.AuthenticationRpcService;

/**
 * 参数注入Filter
 *
 * @author Joe
 */
public class ParamFilter {

    // 是否单点登录服务端，默认为false
    protected boolean isServer = false;
    // 单点登录服务端URL
    protected String ssoServerUrl;
    // 单点登录服务端提供的RPC服务，由Spring容器注入
    protected AuthenticationRpcService authenticationRpcService;

    public void setSsoServerUrl(String ssoServerUrl) {
        this.ssoServerUrl = ssoServerUrl;
    }

    public void setAuthenticationRpcService(AuthenticationRpcService authenticationRpcService) {
        this.authenticationRpcService = authenticationRpcService;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }
}