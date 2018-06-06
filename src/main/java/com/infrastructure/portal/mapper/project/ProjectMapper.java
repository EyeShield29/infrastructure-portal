package com.infrastructure.portal.mapper.project;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.infrastructure.portal.entity.po.config.ProcessConfig;
import com.infrastructure.portal.entity.po.project.ProjectInfo;
import com.infrastructure.portal.entity.po.project.ProjectPeriod;
import com.infrastructure.portal.entity.po.project.ProjectProcess;
import com.infrastructure.portal.entity.po.project.ProjectType;
import com.infrastructure.portal.entity.vo.ListProjectInfoVo;
import com.infrastructure.portal.entity.vo.ProcessProgressVo;
import com.infrastructure.portal.entity.vo.ProjectCountVo;
import com.infrastructure.portal.entity.vo.QueryProjectVo;
import com.infrastructure.portal.entity.vo.QueryWorkVo;


public interface ProjectMapper {

	QueryProjectVo getProjectById(int id);
	
	List<ProjectProcess> listProjectProcess(int projectId);
	
	List<QueryProjectVo> queryProjectByPage(QueryProjectVo queryProjectVo);
	
	List<ProjectProcess> queryProjectProcess(int projectId);
	
	List<ListProjectInfoVo> queryProjectVoList(int id);
	
	List<ProjectCountVo> queryPeriodCountList();
	
	List<ProcessProgressVo> queryLatesProcess();
	
	Integer queryFinishTotalCount();
	
	Integer queryProcessCount(@Param("projectId")int projectId, @Param("status")int status);
	
	Integer queryPeriodCount(int period);
	
	Integer queryAllProcessCount(int projectId);
	
	List<ProjectInfo> queryUnfinishPorject(int id);
	
	ProcessConfig getProcessDescription(@Param("processName")String processName);
	
	List<QueryWorkVo> queryPeriodByPage(@Param("qwv")QueryWorkVo queryWorkVo, @Param("leaderId")int leaderId);
	
	List<ProjectProcess> checkUnfinishProcess(@Param("projectId")int projectId, @Param("period")int period);
	
	List<ProjectProcess> queryProjectProcessList(@Param("projectId")int projectId, @Param("period")int period);
	
	List<ProjectProcess> queryOnProjectProcess(@Param("projectId")int projectId, @Param("period")int period);
	
	QueryWorkVo getPeriodById(int periodId);
	
	Integer deleteProjectProcessById(int projectId);
	
	Integer deleteProjectProcessList(@Param("projectId")int projectId, @Param("period")int period);
	
	Integer addProjectInfo(ProjectInfo projectInfo);
	
	Integer addProjectPeriod(ProjectPeriod projectPeriod);
	
	Integer addProjectType(ProjectType projectType);
	
	Integer addProjectProcess(ProjectProcess projectProcess);
	
	Integer modifyProjectInfo(ProjectInfo projectInfo);
	
	Integer modifyProjectProcess(ProjectProcess projectProcess);
	
	Integer updateProjectProcess(@Param("processId")int processId, @Param("remark")String remark);
	
	Integer updateProjectStatus(@Param("projectId")int projectId, @Param("period")int period);
	
	Integer updatePeriodStatus(int periodId);
	
	Integer updatePeriodInfo(ProjectPeriod projectPeriod);
	
	Integer deleteUserById(int id);
	
	Integer projectTotalCount();
}