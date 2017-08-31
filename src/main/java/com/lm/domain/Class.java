package com.lm.domain;

/**
 * 班次实体
 * Created by Louie on 2017-08-21.
 */
public class Class {
    private long cid;
    private String cname;
    private String cnickname;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCnickname() {
        return cnickname;
    }

    public void setCnickname(String cnickname) {
        this.cnickname = cnickname;
    }
}
