package com.gachon.santa_admin.entity;

import java.util.Date;

public class Comment {
    private String cid;
    private String publisher;
    private String target;
    private String postId;
    private String content;
    private String url;
    private Boolean read;
    private Date createdAt;

    public Comment(String cid, String publisher, String target, String postId, String content, String url, Boolean read, Date createdAt) {
        this.cid = cid;
        this.publisher = publisher;
        this.target = target;
        this.postId = postId;
        this.content = content;
        this.url = url;
        this.read = read;
        this.createdAt = createdAt;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
