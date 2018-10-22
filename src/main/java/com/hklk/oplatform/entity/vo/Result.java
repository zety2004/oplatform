package com.hklk.oplatform.entity.vo;

import com.hklk.oplatform.util.ResultCode;

/**
 * hklk_spring_cloud
 * 2018/10/8 15:34
 *
 * @author 曹良峰
 * @since
 **/
public class Result<T> {

    private int resultCode;
    private String resultMsg;
    private T resultData;

    public Result() {
    }

    public Result(ResultCode resultCode, T data) {
        this(resultCode);
        this.resultData = data;
    }

    public Result(ResultCode resultCode) {
        this.resultCode = resultCode.getResultCode();
        this.resultMsg = resultCode.getResultMsg();
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
