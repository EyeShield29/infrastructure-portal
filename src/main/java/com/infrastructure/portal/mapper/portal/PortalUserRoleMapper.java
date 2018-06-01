package com.infrastructure.portal.mapper.portal;


import com.infrastructure.portal.entity.po.portal.PortalUserRole;


public interface PortalUserRoleMapper {

    int insert(PortalUserRole record);

    int insertSelective(PortalUserRole record);

    PortalUserRole selectByPrimaryKey(PortalUserRole key);

    int updateByPrimaryKeySelective(PortalUserRole record);

    int updateByPrimaryKey(PortalUserRole record);
}