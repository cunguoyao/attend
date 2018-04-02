package com.linkage.study.attend.data.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by cunguoyao on 2017/8/30.
 */
@Table(name = "user")
public class User {

    /**
     * 注解含义
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "_id", isId = true, autoGen = true, property = "NOT NULL")
    private long _id;
    @Column(name = "id",property = "NOT NULL")
    private long id;
    @Column(name = "username",property = "NOT NULL")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "default_account")
    private int defaultAccount;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "status")
    private int status;//1正常
    @Column(name = "last_login_time")
    private long lastLoginTime;
    @Column(name = "token")
    private String token;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(int defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
