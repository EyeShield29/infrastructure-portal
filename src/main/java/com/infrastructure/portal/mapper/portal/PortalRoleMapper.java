package com.infrastructure.portal.mapper.portal;


import java.util.List;

import com.infrastructure.portal.entity.po.portal.PortalRole;


public interface PortalRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PortalRole record);

    int insertSelective(PortalRole record);
    
    List<PortalRole> selectByPage(PortalRole portalRole);
    
    List<PortalRole> getRoleListName();
    
    List<PortalRole> queryRoleListAll();
    
    List<PortalRole> queryPortalRoleName();

    PortalRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PortalRole record);

    int updateByPrimaryKey(PortalRole record);
}