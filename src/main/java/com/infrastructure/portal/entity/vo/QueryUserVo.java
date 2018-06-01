package com.infrastructure.portal.entity.vo;

public class QueryUserVo
{
    private int id;
    private String account;
    private String userName;
    private String role;
    private String roleId;
    private int sex;//remark:性别；1:男，2:女
    private String phoneNum;//remark:手机号码
    private String email;//remark:邮箱
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String user_name) {
        this.userName = user_name;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
