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
    public static final int UPLOAD_ERROR = 302;//上传文件失败,最大不得超过200M

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
    // 导入数据存在失败项,请确认数据是否正确
    public final static int IMPORTERROR_STUDENT = 1015;
    //订单已处理
    public final static int ORDER_IS_HANDLE = 1016;
    //该班级非自己创建无法删除
    public final static int VALIDATE_CLASS_IS_TEACHER_CREATE = 1017;
    //老师不存在
    public final static int TEACHER_IS_NOT_EX = 1018;
    //您选择的文件不符合模板规则，导入失败！
    public final static int FILE_IS_NOT_RIGHT = 1019;

    //您的账号暂未绑定，请先绑定用户！
    public final static int ERROR_MSG = 1020;
    //操作失败，该记录已被审核！
    public final static int UPDATE_ERROR_FOR_IS_EXAMINE = 1021;
    //添加失败，您已经申报过该课程
    public final static int INSERT_ERROR_FOR_IS_APPLY = 1022;

    //您输入的手机号和学生学号不符！
    public final static int NO_FOUND_STUDENT = 1024;
    //该学生已经被绑定！
    public final static int ACCOUNT_WAS_BINDING = 1025;
    //申请课程失败！
    public final static int BUY_CURR_FOR_PARENT = 1026;
    //订单金额为0，无需支付!
    public final static int DONOT_NEED_PAY = 1027;
    //您的账号尚未绑定学生，无法报名
    public final static int NO_BINDING_STUDENT = 1028;
    //您的孩子不符合该课程的申报条件，该课程不属于您孩子的学校或不符合年级
    public final static int STUDENT_IS_NO_QUALIFIED = 1029;
    //您申请的课程人数已满
    public final static int PARENT_APPLY_CURR_FOR_VER_NUM = 1030;
    //该课程已有学生申报，修改失败！
    public final static int HAS_STUDENT = 1031;

    public final static int IS_APPLY_CURRICULUM = 1032;

    public static String getStatusMsg(Object code) {
        Map<Object, String> map = new HashMap<Object, String>();
        map.put(SUCCESS, "成功");
        map.put(ERROR, "操作失败，请重试！");
        map.put(SYS_ERROR, "您的操作有误！");

        map.put(SSO_TOKEN_ERROR, "TOKEN未授权或已过期！");

        map.put(OVER_TIME, "Session Timeout! Please re-sign in！");

        map.put(ADDUSER_USERNAME_EX, "用户名已存在");
        map.put(LOGIN_NAME_OR_PWD_ERROR, "登录名或者密码错误");

        map.put(LOGIN_DISABLE, "账号已被禁用！");

        map.put(ADMIN_NUM_VALIDATE, "管理员数超过限制！");
        map.put(SCHOOLNAME_EX, "学校名已存在！");
        map.put(SCHOOLNAME_UNEX, "学校名不存在");
        map.put(SCHOOL_STATUS, "学校已经停用！");

        map.put(TEACHER_EX, "老师已存在！");
        map.put(CHECK_OPERATOR, "其他管理员已操作！");
        map.put(USER_UNFIND, "账号不存在请联系管理员！");
        map.put(PASSWORD_ERROR, "原密码输入错误！");
        map.put(CLASS_EX, "班级已存在！");
        map.put(STUDENT_EX, "学生已存在！");
        map.put(IMPORTERROR_STUDENT, "导入数据存在失败项,请确认数据是否正确！");
        map.put(ORDER_IS_HANDLE, "订单已处理！");
        map.put(UPLOAD_ERROR, "上传文件失败,最大不得超过200M！");
        map.put(VALIDATE_CLASS_IS_TEACHER_CREATE, "该班级不是自己创建的，不能删除！");
        map.put(TEACHER_IS_NOT_EX, "您输入的账号不存在！");
        map.put(FILE_IS_NOT_RIGHT, "您选择的文件不符合模板规则，导入失败！");
        map.put(UPDATE_ERROR_FOR_IS_EXAMINE, "操作失败，该记录已被审核！");
        map.put(INSERT_ERROR_FOR_IS_APPLY, "添加失败，您已申报过该课程！");
        map.put(ERROR_MSG, "您的账号暂未绑定，请先绑定用户！");
        map.put(NO_FOUND_STUDENT, "您输入的手机号和学生学号不符！");
        map.put(ACCOUNT_WAS_BINDING, "该学生已经被绑定！");
        map.put(DONOT_NEED_PAY, "订单金额为0，无需支付！");

        map.put(NO_BINDING_STUDENT, "您的账号尚未绑定学生，无法报名！");
        map.put(STUDENT_IS_NO_QUALIFIED, "您的孩子不符合该课程的申报条件，该课程不属于您孩子的学校或不符合年级!");
        map.put(HAS_STUDENT, "该课程已有学生申报，修改失败！");
        map.put(PARENT_APPLY_CURR_FOR_VER_NUM, "您申请的课程不在选课时间内或选课人数已满！");
        map.put(IS_APPLY_CURRICULUM, "该老师已经申请了课程并通过了审核，如需继续操作，请先取消开课！");
        return map.get(code);
    }

}

