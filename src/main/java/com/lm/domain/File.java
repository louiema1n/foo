package com.lm.domain;

import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * file实体类
 * Created by Louie on 2017-06-26.
 */
public class File {

    private Integer id;
    private String oldname;
    private String newname;
    private String path;
    private String uploadtime;
    private String type;
    private String description;
    private String suffix;
    private Integer size;
    private String formatSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFormatSize() {
        this.formatSize = new DecimalFormat("#.00").format(((double) this.size) / 1024) + "kb";
        return formatSize;
    }

}
