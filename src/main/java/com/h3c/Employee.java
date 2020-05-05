package com.h3c;

import java.util.UUID;

public class Employee {

    private UUID id;
    private String account;
    private String password;
    private String status;
    private String nickName;
    private String job;
    private String mobile;

    private String email;
    private UUID createId;
    private String createTime;

    private UUID modifiedId;

    public Employee() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getCreateId() {
        return createId;
    }

    public void setCreateId(UUID createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public UUID getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(UUID modifiedId) {
        this.modifiedId = modifiedId;
    }

    public Employee(UUID id, String account, String password, String status, String nickName, String job, String mobile, String email, UUID createId, String createTime, UUID modifiedId) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.status = status;
        this.nickName = nickName;
        this.job = job;
        this.mobile = mobile;
        this.email = email;
        this.createId = createId;
        this.createTime = createTime;
        this.modifiedId = modifiedId;
    }
}
