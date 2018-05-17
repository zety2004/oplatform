package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.vo.RpcUser;


/**
 * 身份认证授权服务接口
 * 
 */
public interface AuthenticationRpcService {
	
	/**
	 * 验证是否已经登录
	 * 
	 * @param token
	 *            授权码
	 * @return
	 */
	public boolean validate(String token);

	/**
	 * 根据登录的Token和应用编码获取授权用户信息
	 * 
	 * @param token 授权码
	 * @return
	 */
	public RpcUser findAuthInfo(String token);

}
