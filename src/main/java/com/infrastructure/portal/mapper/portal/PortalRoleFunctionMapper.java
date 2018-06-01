package com.infrastructure.portal.mapper.portal;


import com.infrastructure.portal.entity.po.portal.PortalRoleFunction;


public interface PortalRoleFunctionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PortalRoleFunction record);

    int insertSelective(PortalRoleFunction record);

    PortalRoleFunction selectByPrimaryKey(PortalRoleFunction key);

    int updateByPrimaryKeySelective(PortalRoleFunction record);

    int updateByPrimaryKey(PortalRoleFunction record);
    
}