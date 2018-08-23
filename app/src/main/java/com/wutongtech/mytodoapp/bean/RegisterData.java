package com.wutongtech.mytodoapp.bean;

import java.util.List;

/**
 * Created by wutongtech_shengmao on 18-8-23 17:13.
 * 作用：注册信息
 */
public class RegisterData {

    private List<Integer> collectIds;
    private String email;
    private String icon;
    private int id;
    private String username;
    private String password;
    private String token;
    private int type;

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "collectIds=" + collectIds +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}
