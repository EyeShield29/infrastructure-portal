package com.infrastructure.portal.entity.vo;

import java.io.Serializable;



public class QueryDocumentInfoVo implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	private int id;

	private Integer uploader_id;

	private String user_name;

	private String document_name;

    private Integer project_id;

	private String project_name;

    private Integer document_size;

    private java.util.Date upload_time;//remark:上传时间;length:19

    private String document_uri;

    private Integer download_times;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Integer getUploaderId() {
        return uploader_id;
    }
    public void setUploaderId(Integer uploaderId) {
        this.uploader_id = uploaderId;
    }
    
    public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDocumentName() {
        return document_name;
    }
    public void setDocumentName(String documentName) {
        this.document_name = documentName;
    }
    public Integer getProjectId() {
        return project_id;
    }
    public void setProjectId(Integer projectId) {
        this.project_id = projectId;
    }
    public Integer getDocumentSize() {
        return document_size;
    }
    public void setDocumentSize(Integer documentSize) {
        this.document_size = documentSize;
    }
    public java.util.Date getUploadTime() {
        return upload_time;
    }
    public void setUploadTime(java.util.Date uploadTime) {
        this.upload_time = uploadTime;
    }
    public String getDocumentUri() {
        return document_uri;
    }
    public void setDocumentUrl(String documentUri) {
        this.document_uri = documentUri;
    }
    public Integer getDownloadTimes() {
        return download_times;
    }
    public void setDownloadTimes(Integer downloadTimes) {
        this.download_times = downloadTimes;
    }
    public String getProjectName() {
        return project_name;
    }
    public void setProjectName(String projectName) {
        this.project_name = projectName;
    }
    
    
}
