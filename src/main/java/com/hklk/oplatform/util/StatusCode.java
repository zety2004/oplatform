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

    //账户已被禁用
    public static final int LOGIN_DISABLE = 1000;
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
    // 该学校已经停用
    public final static int SCHOOL_STATUS = 1008;
    // 老师已存在
    public final static int TEACHER_EX = 1009;
    // 其他管理员已操作
    public final static int CHECK_OPERATOR = 1010;
    // 账号不存在请联系管理员
    public final static int USER_UNFIND = 1011;
    // 原密码输入错误
    public final static int PASSWORD_ERROR = 1012;
    // 班级已存在
    public final static int CLASS_EX = 1013;
    // 学生已存在
    public final static int STUDENT_EX = 1014;
    // 导入学生存在失败项,请确认数据是否正确
    public final static int IMPORTERROR_STUDENT = 1015;
    //订单已处理
    public final static int ORDER_IS_HANDLE = 1016;

    public static String getStatusMsg(Object code) {

        Map<Object, String> map = new HashMap<Object, String>();
        map.put(SUCCESS, "成功");
        map.put(ERROR, "操作失败，请重试！");

        map.put(SYS_ERROR, "您的操作有误！");
        map.put(LOGIN_DISABLE, "账号已被禁用！");
        map.put(ADMIN_NUM_VALIDATE, "管理员数超过限制！");
        map.put(SCHOOLNAME_EX, "学校名已存在！");
        map.put(SSO_TOKEN_ERROR, "TOKEN未授权或已过期！");
        map.put(SCHOOLNAME_UNEX, "学校名不存在");
        map.put(OVER_TIME, "Session Timeout! Please re-sign in！");
        map.put(LOGIN_NAME_OR_PWD_ERROR, "登录名或者密码错误");
        map.put(ADDUSER_USERNAME_EX, "用户名已存在");
        map.put(SCHOOL_STATUS, "该学校已经停用！");
        map.put(TEACHER_EX, "老师已存在！");
        map.put(CHECK_OPERATOR, "其他管理员已操作！");
        map.put(USER_UNFIND, "账号不存在请联系管理员！");
        map.put(PASSWORD_ERROR, "原密码输入错误！");
        map.put(CLASS_EX, "班级已存在！");
        map.put(STUDENT_EX, "学生已存在！");
        map.put(IMPORTERROR_STUDENT, "导入学生存在失败项,请确认数据是否正确！");
        map.put(ORDER_IS_HANDLE, "订单已处理！");
        return map.get(code);
    }
}

