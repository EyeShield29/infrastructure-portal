package com.infrastructure.portal.entity.vo;

import java.io.Serializable;



public class ListProjectInfoVo implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	private int id;

    private String projectName;

	private String projectNum;
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
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
	
	
    
}
