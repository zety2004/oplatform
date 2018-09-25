package com.hklk.oplatform.entity.table;

import java.io.Serializable;
import java.util.Date;

public class StudentChoice implements Serializable {
    private Integer id;

    private Integer studentId;

    private Integer scaId;

    private Date createTime;

    private Date refundTime;

    private Integer payState;

    private Integer isHc;

    private String orderId;

    private Double payMoney;

    private Double payHcMoney;

    private String commodityName;

    private String transactionId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getScaId() {
        return scaId;
    }

    public void setScaId(Integer scaId) {
        this.scaId = scaId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getPayHcMoney() {
        return payHcMoney;
    }

    public void setPayHcMoney(Double payHcMoney) {
        this.payHcMoney = payHcMoney;
    }

    public Integer getIsHc() {
        return isHc;
    }

    public void setIsHc(Integer isHc) {
        this.isHc = isHc;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }
}