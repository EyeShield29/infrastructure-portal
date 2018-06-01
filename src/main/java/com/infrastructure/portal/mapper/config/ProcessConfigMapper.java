package com.infrastructure.portal.mapper.config;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.infrastructure.portal.entity.po.config.ProcessConfig;


public interface ProcessConfigMapper {

    int deleteProcessConfigById(Integer id);

    int insert(ProcessConfig record);

    int addProcessConfig(ProcessConfig record);
    
    List<ProcessConfig> selectByPage(ProcessConfig processConfig);
    
    List<ProcessConfig> selectBySelective(ProcessConfig processConfig);
    
    List<ProcessConfig> queryProcessConfigList(int period);
    
    List<ProcessConfig> getProcessList(@Param("period")int period, @Param("serialNum")int serialNum);
    
    ProcessConfig selectByPrimaryKey(Integer id);

    int modifyProcessConfig(ProcessConfig record);

    int updateByPrimaryKey(ProcessConfig record);
}