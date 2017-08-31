package com.lm.domain.shiro;

import com.lm.domain.Schedule;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体
 * Created by Louie on 2017-08-21.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private long uid;
    private String username;
    private String nickname;
    private String password;
    private String salt;    // 密码盐
    private Integer state;     // 状态:0-未激活;1-正常;2-已锁定;3-已停用
    private String email;
    private List<Schedule> schedules;
    private int issch;      // 是否参与排班

    // 重写getCredentialsSalt，重新定义盐
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    // 一个用户对应多个角色
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIssch() {
        return issch;
    }

    public void setIssch(int issch) {
        this.issch = issch;
    }
}
