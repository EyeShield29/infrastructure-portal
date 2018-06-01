package com.infrastructure.portal.mapper.portal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.infrastructure.portal.entity.po.portal.PortalFunction;




public interface PortalFunctionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PortalFunction record);

    int insertSelective(PortalFunction record);

    List<PortalFunction> selectByPage(PortalFunction portalFunction);
    
    List<PortalFunction> getFunctionList();
    
    List<PortalFunction> listPortalRoleFunctions(@Param("roleId") Integer id);
    
    PortalFunction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PortalFunction record);

    int updateByPrimaryKey(PortalFunction record);
}