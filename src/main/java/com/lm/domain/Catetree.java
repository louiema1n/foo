package com.lm.domain;

import java.util.List;

/**
 * 类别树实体
 * Created by Louie on 2017-07-03.
 */
public class Catetree {
    private Integer id;
    private Integer pid;
    private String name;
    private String url;
    private byte open;
    private byte checked;
    private String path;
    private List<Catetree> catetrees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte getOpen() {
        return open;
    }

    public void setOpen(byte open) {
        this.open = open;
    }

    public byte getChecked() {
        return checked;
    }

    public void setChecked(byte checked) {
        this.checked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Catetree> getCatetrees() {
        return catetrees;
    }

    public void setCatetrees(List<Catetree> catetrees) {
        this.catetrees = catetrees;
    }
}
