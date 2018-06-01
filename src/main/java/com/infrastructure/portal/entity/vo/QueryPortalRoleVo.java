package com.infrastructure.portal.entity.vo;


/*  */
public class QueryPortalRoleVo {
	private int id;//remark:id，自增;length:10; not null,default:null
	private String roleName;//remark:角色名称;length:16; not null,default:null
	private String roleCode;//remark:角色code;length:16; not null,default:null
	private String functionName;
	private String functionId;
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public QueryPortalRoleVo() {
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
	
	
	
}