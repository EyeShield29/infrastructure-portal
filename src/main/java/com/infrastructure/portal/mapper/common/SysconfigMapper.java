package com.infrastructure.portal.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.infrastructure.portal.entity.po.common.Sysconfig;


public interface SysconfigMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Sysconfig record);

    int insertSelective(Sysconfig record);
    
    List<Sysconfig> listSysconfig(@Param("servercode")String servercode);
    
    Sysconfig getSysconfigByCkey(String ckey);

    Sysconfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sysconfig record);

    int updateByPrimaryKey(Sysconfig record);
}