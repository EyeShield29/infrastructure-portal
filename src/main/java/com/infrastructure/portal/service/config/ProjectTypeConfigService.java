package com.infrastructure.portal.service.config;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.project.ProjectType;
import com.infrastructure.portal.mapper.project.ProjectTypeMapper;

@Service
public class ProjectTypeConfigService {
    @Autowired
    private ProjectTypeMapper projectTypeMapper;
    
    /**
     * 根据title_name来分页查询用户
     */
    public PageInfo<ProjectType> queryProjectTypeConfigListByPage(Integer currentPage, Integer pageSize,
    		ProjectType projectTypeConfig) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<ProjectType> projectTypeConfigList = projectTypeMapper.selectByPage(projectTypeConfig);
        PageInfo<ProjectType> pageInfo = new PageInfo<ProjectType>(projectTypeConfigList);
        return pageInfo;
    }
    
    public List<ProjectType> queryProjectTypeList() throws Exception {
        
        List<ProjectType> projectTypeList = projectTypeMapper.queryProjectTypeList();

        return projectTypeList;
    }
    
    public List<ProjectType> queryProjectTypeNameConfigList() throws Exception{
    	List<ProjectType> projectTypeNameList = projectTypeMapper.queryProjectTypeNameList();
    	return projectTypeNameList;
    }

    /**
     * 添加流程
     */
    public void addProjectTypeConfig(ProjectType projectTypeConfig)
            throws Exception {
        
        projectTypeConfig.setCreateTime(new Date());
        projectTypeMapper.addProjectTypeConfig(projectTypeConfig);
    }

    /**
     * 删除用户职称配置
     */
    public void deleteProjectTypeConfig(int id) throws Exception {
        projectTypeMapper.deleteProjectTypeConfigById(id);
    }

    /**
     * 更改用户职称配置信息
     * @throws Exception 
     */
    public void modifyProjectTypeConfig(ProjectType projectTypeConfig) throws Exception {
        projectTypeMapper.modifyProjectTypeConfig(projectTypeConfig);
    }
    
}
