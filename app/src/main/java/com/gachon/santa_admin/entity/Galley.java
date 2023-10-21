package com.gachon.santa_admin.entity;

import java.util.Date;

public class Galley {
    private String uid;
    private String pid;
    private String url;
    private Boolean read;
    private Date createdAt;

    public Galley(String uid, String pid, String url, Boolean read, Date createdAt) {
        this.uid = uid;
        this.pid = pid;
        this.url = url;
        this.read = read;
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
