package com.hklk.oplatform.util;

import java.util.HashMap;
import java.util.Map;

public class StatusCode {
    //session 认证
    public static final String AUTHENTICATION_KEY = "SESSION_AUTHENTICATION";

    // 通用错误以9开头
    public final static int APPLICATION_ERROR = 9000;// 应用级错误
    public final static int VALIDATE_ERROR = 9001;// 参数验证错误
    public final static int SERVICE_ERROR = 9002;// 业务逻辑验证错误
    public final static int CACHE_ERROR = 9003;// 缓存访问错误
    public final static int DAO_ERROR = 9004;// 数据访问错误


    public static final int SUCCESS = 200;//成功
    public static final int ERROR = 300;    //操作失败
    public static final int OVER_TIME = 301;//超时
    public static final int SYS_ERROR = 9999;//系统错误

    //登录名或者密码错误
    public static final int LOGIN_NAME_OR_PWD_ERROR = 1001;
    //用户名已存在
    public static final int ADDUSER_USERNAME_EX = 1002;
    //管理员数超过限制
    public static final int ADMIN_NUM_VALIDATE = 1003;
    //学校已存在
    public static final int SCHOOLNAME_EX = 1004;
    //学校已存在
    public static final int SCHOOLNAME_UNEX = 1005;
    // TOKEN未授权或已过期
    public final static int SSO_TOKEN_ERROR = 1006;
    // 没有访问权限
    public final static int SSO_PERMISSION_ERROR = 1007;

    public static String getStatusMsg(Object code) {

        Map<Object, String> map = new HashMap<Object, String>();
        map.put(SUCCESS, "成功");
        map.put(ERROR, "操作失败，请重试！");

        map.put(SYS_ERROR, "您的操作有误！");
        map.put(ADMIN_NUM_VALIDATE, "管理员数超过限制！");
        map.put(SCHOOLNAME_EX, "学校名已存在！");
        map.put(SCHOOLNAME_UNEX, "学校名不存在");
        map.put(OVER_TIME, "Session Timeout! Please re-sign in！");
        map.put(LOGIN_NAME_OR_PWD_ERROR, "登录名或者密码错误");
        map.put(ADDUSER_USERNAME_EX, "用户名已存在");
        return map.get(code);
    }
}

