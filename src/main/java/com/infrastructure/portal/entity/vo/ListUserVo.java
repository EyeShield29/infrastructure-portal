package com.infrastructure.portal.entity.vo;

import java.io.Serializable;



/*  */

public class ListUserVo implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	private java.lang.String account;//remark:账号;length:16; not null,default:null

	private java.lang.String userName;//remark:姓名;length:16; not null,default:null

	public ListUserVo() {
	}

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getAccount() {
        return account;
    }

    public void setAccount(java.lang.String account) {
        this.account = account;
    }

    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

	
	
}