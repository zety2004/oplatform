package com.hklk.oplatform.service;

import org.apache.poi.ss.formula.functions.T;

/**
 * 身份认证授权服务接口
 */
public interface AuthenticationRpcService {

    /**
     * 验证是否已经登录
     *
     * @param token 授权码
     * @return
     */
    public <T> boolean validate(String key, String token, Class<T> clazz);

    /**
     * 根据登录的Token和应用编码获取授权用户信息
     *
     * @param token 授权码
     * @return
     */
    public <T> T findAuthInfo(String key, String token, Class<T> clazz);

}
