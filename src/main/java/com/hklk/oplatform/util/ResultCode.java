package com.hklk.oplatform.util;

/**
 * hklk_spring_cloud
 * 2018/10/8 15:35
 *
 * @author 曹良峰
 * @since
 **/
public enum ResultCode {
    APPLICATION_ERROR(9000, "应用级错误"),
    VALIDATE_ERROR(9001, "参数验证错误"),
    SERVICE_ERROR(9002, "业务逻辑验证错误"),
    CACHE_ERROR(9003, "缓存访问错误"),
    DAO_ERROR(9004, "数据访问错误"),
    SUCCESS(200, "成功"),
    ERROR(300, "操作失败"),
    OVER_TIME(301, "超时"),
    UPLOAD_ERROR(302, "上传文件失败,最大不得超过200M"),
    SYS_ERROR(9999, "系统错误"),
    LOGIN_DISABLE(1000, "账户已被禁用"),
    LOGIN_NAME_OR_PWD_ERROR(1001, "登录名或者密码错误"),
    ADDUSER_USERNAME_EX(1002, "用户名已存在"),
    ADMIN_NUM_VALIDATE(1003, "管理员数超过限制"),
    SCHOOLNAME_EX(1004, "学校已存在"),
    SCHOOLNAME_UNEX(1005, "学校已存在"),
    SSO_TOKEN_ERROR(1006, "TOKEN未授权或已过期"),
    SSO_PERMISSION_ERROR(1007, "没有访问权限"),
    SCHOOL_STATUS(1008, "该学校已经停用"),
    TEACHER_EX(1009, "老师已存在"),
    CHECK_OPERATOR(1010, "其他管理员已操作"),
    USER_UNFIND(1011, "账号不存在请联系管理员"),
    PASSWORD_ERROR(1012, "原密码输入错误"),
    CLASS_EX(1013, "班级已存在"),
    STUDENT_EX(1014, "学生已存在"),
    IMPORTERROR_STUDENT(1015, "导入数据存在失败项,请确认数据是否正确"),
    ORDER_IS_HANDLE(1016, "订单已处理"),
    VALIDATE_CLASS_IS_TEACHER_CREATE(1017, "该班级非自己创建无法删除"),
    TEACHER_IS_NOT_EX(1018, "老师不存在"),
    FILE_IS_NOT_RIGHT(1019, "您选择的文件不符合模板规则，导入失败！"),
    ERROR_MSG(1020, "您的账号暂未绑定，请先绑定用户！"),
    UPDATE_ERROR_FOR_IS_EXAMINE(1021, "操作失败，该记录已被审核！"),
    INSERT_ERROR_FOR_IS_APPLY(1022, "添加失败，您已经申报过该课程"),
    NO_FOUND_STUDENT(1024, "您输入的手机号和学生学号不符！"),
    ACCOUNT_WAS_BINDING(1025, "该学生已经被绑定！"),
    BUY_CURR_FOR_PARENT(1026, "申请课程失败！"),
    DONOT_NEED_PAY(1027, "订单金额为0，无需支付!"),
    NO_BINDING_STUDENT(1028, "您的账号尚未绑定学生，无法报名"),
    STUDENT_IS_NO_QUALIFIED(1029, "您的孩子不符合该课程的申报条件，该课程不属于您孩子的学校或不符合年级"),
    PARENT_APPLY_CURR_FOR_VER_NUM(1030, "您申请的课程不在选课时间内或选课人数已满！"),
    HAS_STUDENT(1031, "该课程已有学生申报，修改失败！"),
    IS_APPLY_CURRICULUM(1032, "该老师已经申请了课程并通过了审核，如需继续操作，请先取消开课！"),
    ORDER_REPAY_FAIL(1033, "这条订单不满足退款条件哦。");


    private int resultCode;
    private String resultMsg;

    ResultCode(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
