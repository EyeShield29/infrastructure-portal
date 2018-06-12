/*
 * Copyright (c) 2007-2015 by CSCW All rights reserved
 */
package com.infrastructure.portal.entity.vo;



/**
 * TODO <br/>
 * <br>=
 * ============================== <br>
 * 团队：CSCW <br>
 * 系统：TODO <br>
 * 研发：cxl <br>
 * 创建时间：2017年5月2日 下午6:10:40 <br>=
 * ==============================
 */
public class ProcessProgressVo {

    private String projectName;

    private String processName;

    private Integer period;

    private java.util.Date updateTime;
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getPeriod() {
        return period;
    }
    public void setPeriod(Integer period) {
        this.period = period;
    }
    
    
}
