package com.lm.domain;

import com.lm.domain.shiro.User;

import java.util.Date;

/**
 * 排班实体
 * Created by Louie on 2017-08-21.
 */
public class Schedule {
    private long sid;
    private Date schtime;
    private long uid;
    private long cid;
    private Class aClass;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public Date getSchtime() {
        return schtime;
    }

    public void setSchtime(Date schtime) {
        this.schtime = schtime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
