package com.kill.api.model;

import java.io.Serializable;
import java.util.Date;

public class LoginTicket implements Serializable {
    public int id;
    public int userId;
    public Date expired; //有限日期
    public int status;
    public String ticket;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

