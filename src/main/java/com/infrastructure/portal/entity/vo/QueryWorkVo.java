package com.infrastructure.portal.entity.vo;

import org.springframework.format.annotation.DateTimeFormat;


public class QueryWorkVo
{

    private int id;

    private String projectName;

    private String projectNum;

    private Integer projectId;

    private Integer periodLeaderId;

    private String periodName;
    private Integer period;
    private Integer status;//项目状态【0:未完成；1:已完成】
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date periodStartDate;//remark:阶段起始时间;length:19
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date periodEndDate;//remark:阶段结束时间;length:19

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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getPeriodLeaderId() {
		return periodLeaderId;
	}
	public void setPeriodLeaderId(Integer periodLeaderId) {
		this.periodLeaderId = periodLeaderId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public java.util.Date getPeriodStartDate() {
		return periodStartDate;
	}
	public void setPeriodStartDate(java.util.Date period_start_date) {
		this.periodStartDate = period_start_date;
	}
	public java.util.Date getPeriodEndDate() {
		return periodEndDate;
	}
	public void setPeriodEndDate(java.util.Date periodEndDate) {
		this.periodEndDate = periodEndDate;
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
