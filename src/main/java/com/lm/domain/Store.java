package com.lm.domain;

import java.sql.Timestamp;

/**
 * 库存实体
 * Created by Louie on 2017-08-12.
 */
public class Store {
    private long id;
    private String name;
    private long size;
    private String sizeUnit;
    private long total;
    private String totalUnit;
    private long safeCount;
    private Integer state;
    private long afterAdd;
    private long used;
    private Timestamp afterAddTime;
    private Timestamp createTime;
    private String creator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(String totalUnit) {
        this.totalUnit = totalUnit;
    }

    public long getSafeCount() {
        return safeCount;
    }

    public void setSafeCount(long safeCount) {
        this.safeCount = safeCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public long getAfterAdd() {
        return afterAdd;
    }

    public void setAfterAdd(long afterAdd) {
        this.afterAdd = afterAdd;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public Timestamp getAfterAddTime() {
        return afterAddTime;
    }

    public void setAfterAddTime(Timestamp afterAddTime) {
        this.afterAddTime = afterAddTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
