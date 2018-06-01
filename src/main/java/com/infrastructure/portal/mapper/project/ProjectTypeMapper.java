package com.infrastructure.portal.mapper.project;

import java.util.List;
import com.infrastructure.portal.entity.po.project.ProjectType;

public interface ProjectTypeMapper {

    int deleteProjectTypeConfigById(Integer id);

    int insert(ProjectType record);

    int addProjectTypeConfig(ProjectType record);

    ProjectType selectByPrimaryKey(Integer id);

    List<ProjectType> selectByPage(ProjectType projectType);
    
    List<ProjectType> queryProjectTypeList();
    
    List<ProjectType> queryProjectTypeNameList();
    
    int modifyProjectTypeConfig(ProjectType record);

    int updateByPrimaryKey(ProjectType record);
}