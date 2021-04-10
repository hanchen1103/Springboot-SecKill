package com.kill.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class water implements Serializable {

    private int id;

    private double electric_frequenct;

    private double accuracy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private double flow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getElectric_frequenct() {
        return electric_frequenct;
    }

    public void setElectric_frequenct(double electric_frequenct) {
        this.electric_frequenct = electric_frequenct;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;




}
