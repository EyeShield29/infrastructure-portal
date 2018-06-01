package com.infrastructure.portal.entity.vo;

import org.springframework.format.annotation.DateTimeFormat;

public class QueryProjectVo
{

    private int id;

    private String projectName;

    private String projectNum;

    private String typeName;

    private String entryStaffAccount;

    private String description;

    private Integer leaderId;

    private String userName;

    private String company;
    
    private Integer status;//项目状态【1-7，其中1-6未相关阶段，0为已完成，7为已删除】
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date projectStartDate;//remark:项目起始时间;length:19
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date projectEndDate;//remark:项目结束时间;length:19

    private java.util.Date createTime;//remark:创建时间;length:19

    private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getEntryStaffAccount() {
		return entryStaffAccount;
	}
	public void setEntryStaffAccount(String entryStaffAccount) {
		this.entryStaffAccount = entryStaffAccount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public java.util.Date getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(java.util.Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public java.util.Date getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(java.util.Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
    
    
    
}
