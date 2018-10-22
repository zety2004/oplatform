package com.hklk.oplatform.util;

import com.hklk.oplatform.entity.vo.Result;

/**
 * hklk_spring_cloud
 * 2018/10/8 15:37
 *
 * @author 曹良峰
 * @since
 **/
public class ResultUtils {
    private static String dateFormate = "yyyy-MM-dd HH:mm:ss";

    public static Result success(Object data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static String successStr(Object data) {
        return JsonUtil.toJson(new Result<>(ResultCode.SUCCESS, data), dateFormate);
    }

    public static String successStr() {
        return JsonUtil.toJson(new Result<>(ResultCode.SUCCESS));
    }

    public static Result warn(ResultCode resultCode, String msg) {
        Result<Object> result = new Result<>(resultCode);
        result.setResultMsg(msg);
        return result;
    }

    public static Result warn(ResultCode resultCode) {
        return new Result(resultCode);
    }

    public static String warnStr(ResultCode resultCode) {
        return JsonUtil.toJson(new Result(resultCode));
    }

    public static String warnStr(ResultCode resultCode, Object data) {
        Result<Object> result = new Result<>(resultCode);
        result.setResultData(data);
        return JsonUtil.toJson(result);
    }

    public static String warnStr(ResultCode resultCode, String msg) {
        Result<Object> result = new Result<>(resultCode);
        result.setResultMsg(msg);
        return JsonUtil.toJson(result);
    }
}
