package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {

    private Integer credentialsId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;

    public Credentials(Integer credentialsId, String url, String username, String key, String password, Integer userId) {
        this.credentialsId = credentialsId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserid(Integer userId) {
        this.userId = userId;
    }
}
