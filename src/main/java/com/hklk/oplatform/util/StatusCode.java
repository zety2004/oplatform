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

    public static final String SUCCESS = "200";//成功

    public static final String ERROR = "300";    //操作失败

    public static final String OVER_TIME = "301";     //超时
    //操作失败
    public static final String SYS_ERROR = "9999";

    //登录名或者密码错误
    public static final String LOGIN_NAME_OR_PWD_ERROR = "0001";

    //登录账号错误
    public static final String LOGIN_NAME_ERROR = "0002";

    //用户不存在
    public static final String USER_NOT_EXISTS = "0003";

    //未登录
    public static final String USER_NO_LOGIN = "0004";

    //非法参数
    public static final String PARAMTER_ERROR = "0005";
    //课程不存在
    public static final String COURSES_NOT_EXSIT = "0006";
    //手机号已存在
    public static final String MOBILEPHONE_EXSIT = "0007";
    //文件格式错误
    public static final String FILE_TYPE_ERROR = "0008";
    //记录不存在
    public static final String DATA_NOT_EXIT = "0009";
    //资源已收藏或者引用
    public static final String RESOURCE_EXIT = "0010";
    //已是最后一页
    public static final String ERROR_LAST_PAGE = "0011";
    //该目录下第一页已存在
    public static final String FIRST_PAGE_EXSIT = "0012";
    //删除页的时候该目录下必须保留一页存在
    public static final String ERROR_DELETE_PAGE = "0013";

    public static String getStatusMsg(String code) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("200", "成功");
        map.put("300", "操作失败，请重试！");
        map.put("301", "Session Timeout! Please re-sign in！");
        map.put("0001", "登录名或者密码错误");
        map.put("0002", "登录账号错误");
        map.put("0003", "用户不存在");
        map.put("0004", "未登陆");
        map.put("0005", "非法参数");
        map.put("0006", "课程不存在");
        map.put("0007", "手机号已存在");
        map.put("0008", "文件格式错误");
        map.put("0009", "记录不存在");
        map.put("0010", "资源已收藏或者引用");
        map.put("0011", "最后一页,无下一页");
        map.put("0012", "该目录下第一页已存在");
        map.put("0013", "目录下最后一页不能删除，只可修改");
        return map.get(code);
    }
}

