package com.infrastructure.portal.service.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.project.ProjectInfo;
import com.infrastructure.portal.entity.po.project.ProjectPeriod;
import com.infrastructure.portal.entity.po.project.ProjectProcess;
import com.infrastructure.portal.entity.vo.ListProjectInfoVo;
import com.infrastructure.portal.entity.vo.PeriodListVo;
import com.infrastructure.portal.entity.vo.ProcessProgressVo;
import com.infrastructure.portal.entity.vo.ProjectAjaxVo;
import com.infrastructure.portal.entity.vo.ProjectCountVo;
import com.infrastructure.portal.entity.vo.QueryProjectVo;
import com.infrastructure.portal.entity.vo.QueryWorkVo;
import com.infrastructure.portal.mapper.project.ProjectMapper;
import com.infrastructure.portal.utils.PeriodMap;

@Service
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    /**
     * 根据account,user_name,role来分页查询用户
     */
    public PageInfo<QueryProjectVo> queryProjectInfoListByPage(Integer currentPage, Integer pageSize,
    		QueryProjectVo queryProjectVo) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<QueryProjectVo> ProjectVoList = projectMapper.queryProjectByPage(queryProjectVo);
        PageInfo<QueryProjectVo> pageInfo = new PageInfo<QueryProjectVo>(ProjectVoList);
        return pageInfo;
    }
    
    public List<ListProjectInfoVo> queryProjectVoList(int id) throws Exception {
        
        List<ListProjectInfoVo> projectVoList = projectMapper.queryProjectVoList(id);

        return projectVoList;
    }

    /**
     * 
     */
    public List<ProjectProcess> queryProjectProcessList(
            int projectId) throws Exception {
       List<ProjectProcess> projectProcessList = new ArrayList<ProjectProcess>();
       projectProcessList = projectMapper.queryProjectProcess(projectId);
        return projectProcessList;
    }

    /**
     * 添加项目基本信息
     */
    public int addProjectInfo(ProjectInfo projectInfo)
            throws Exception {
        
        projectInfo.setCreateTime(new Date());
        projectInfo.setStatus(1);
        return projectMapper.addProjectInfo(projectInfo);
    }
    
    /**
     * 添加项目基本信息
     */
    public void addProjectProcess(String[] projectProcessList, int projectId, int period)
            throws Exception {
        
        int serialNum = 0;
        String processDescription = null;

        for (String processName : projectProcessList) {
            serialNum++;
            ProjectProcess projectProcess = new ProjectProcess();
            //System.out.println(projectId+"haha");
            projectProcess.setProjectId(projectId);
            projectProcess.setPeriod(period);
            projectProcess.setSerialNum(serialNum);
            processDescription = projectMapper.getProcessDescription(processName).getDescription();
            projectProcess.setDescription(processDescription);
            projectProcess.setProcessName(processName);
            projectProcess.setCreateTime(new Date());
            projectProcess.setStatus(0);
            projectMapper.addProjectProcess(projectProcess);
        }
    }

    /**
     * 删除项目基本信息
     */
    public void deleteProjectInfo(int id) throws Exception {
        projectMapper.deleteUserById(id);
    }
    
    /**
     * 删除项目流程信息
     */
    public void deleteProjectProcess(int projectId) throws Exception {
        projectMapper.deleteProjectProcessById(projectId);
    }

    /**
     * 更改项目基本信息
     * @throws Exception 
     */
    public void modifyProjectInfo(ProjectInfo projectInfo) throws Exception {
          projectMapper.modifyProjectInfo(projectInfo);
    }
    
    /**
     * 通过couponInfoId获取优惠券的信息
     */
    public QueryProjectVo getProjectInfoById(int id) 
            throws Exception{
        return projectMapper.getProjectById(id);
    }

    /**
     * <pre>
     * TODO 修改项目流程
     * </pre>
     */
    public void modifyProjectProcess(String[] processOfPeriod, int projectId, int period) throws Exception {
        
        projectMapper.deleteProjectProcessList(projectId, period);
        
        addProjectProcess(processOfPeriod, projectId, period);
    }

    /**
     * <pre>
     * TODO 修改流程状态为已完成
     * </pre>
     */
    public void updateProjectProcess(int processId, String remark) throws Exception {
        // TODO Auto-generated method stub
        projectMapper.updateProjectProcess(processId, remark);
    }

    /**
     * <pre>
     * TODO 检查该period是否存在未完成的流程
     * </pre>
     */
    public int checkUnfinishProcess(int projectId, int period) throws Exception {
        // TODO Auto-generated method stub
        List<ProjectProcess> projectProcessList = new ArrayList<ProjectProcess>();       
        projectProcessList = projectMapper.checkUnfinishProcess(projectId, period);        
        return projectProcessList.size();
    }

    /**
     * <pre>
     * TODO 更新项目信息中的状态为下一阶段
     * </pre>
     */
    public void updateProjectStatus(int projectId, int period) throws Exception {
        // TODO Auto-generated method stub
        projectMapper.updateProjectStatus(projectId, period);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<ProjectProcess> queryProjectProcessList(
            int projectId, int period) throws Exception {
        // TODO Auto-generated method stub
        List<ProjectProcess> projectProcessList = new ArrayList<ProjectProcess>();      
        projectProcessList = projectMapper.queryProjectProcessList(projectId, period);        
        return projectProcessList;
        
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public int queryOnProjectProcess(int projectId, Integer period) throws Exception {
        // TODO Auto-generated method stub
        List<ProjectProcess> projectProcessList = new ArrayList<ProjectProcess>();
        projectProcessList = projectMapper.queryOnProjectProcess(projectId, period);        
        if(projectProcessList.isEmpty()){
            return 0;
        }else{
            return projectProcessList.get(0).getId();
        }
        
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<ProjectCountVo> periodCount() throws Exception {
        // TODO Auto-generated method stub
        return projectMapper.queryPeriodCountList();
    }
    
    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<ProcessProgressVo> queryLatesProcess() throws Exception {
        // TODO Auto-generated method stub
        return projectMapper.queryLatesProcess();
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public int projectFinishCount() throws Exception {
        // TODO Auto-generated method stub
        return projectMapper.queryFinishTotalCount();
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<ProjectInfo> queryUnfinishPorject(int id) throws Exception {
        // TODO Auto-generated method stub
        return projectMapper.queryUnfinishPorject(id);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<ProjectAjaxVo> getProjectAjaxVo(List<ProjectInfo> projectList) throws Exception {
        // TODO Auto-generated method stub
        List<ProjectAjaxVo> projectVoList = new ArrayList<ProjectAjaxVo>();
        int finishProcessCount = 0;
        int totalProcessCount = 0;
        for(ProjectInfo project:projectList){
            ProjectAjaxVo prjectAjaxVo = new ProjectAjaxVo();
            prjectAjaxVo.setId(project.getId());
            prjectAjaxVo.setProjectName(project.getProjectName());
            finishProcessCount = projectMapper.queryProcessCount(project.getId(),1);
            totalProcessCount = projectMapper.queryAllProcessCount(project.getId());
            if(totalProcessCount!=0){
                prjectAjaxVo.setProcessPercent(finishProcessCount*100/totalProcessCount);
            }else{
                prjectAjaxVo.setProcessPercent(0);
            }
            
            projectVoList.add(prjectAjaxVo);
        }
        
        return projectVoList;
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public void addProjectPeriod(PeriodListVo periodList) throws Exception {
        // TODO Auto-generated method stub
        List<ProjectPeriod> periods = periodList.getPeriods();
        for(ProjectPeriod period:periods){
        	period.setProjectId(period.getProjectId());
        	period.setPeriodLeaderId(period.getPeriodLeaderId());
        	period.setPeriodStartDate(period.getPeriodStartDate());
        	period.setPeriodEndDate(period.getPeriodEndDate());
            period.setCreateTime(new Date());
            period.setStatus(0);
            period.setPeriodName(PeriodMap.getPeriodName(period.getPeriod()));
            projectMapper.addProjectPeriod(period);
        }
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public void addProjectPeriod(int projectId, int periodLeaderId, Date startDate, Date endDate) throws Exception {
        // TODO Auto-generated method stub
        ProjectPeriod projectPeriod = new ProjectPeriod();
        projectPeriod.setPeriod(0);
        projectPeriod.setStatus(0);
        projectPeriod.setPeriodName("总负责人");
        projectPeriod.setProjectId(projectId);
        projectPeriod.setPeriodStartDate(startDate);
        projectPeriod.setPeriodEndDate(endDate);
        projectPeriod.setPeriodLeaderId(periodLeaderId);
        projectPeriod.setCreateTime(new Date());
        projectMapper.addProjectPeriod(projectPeriod);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public PageInfo<QueryWorkVo> queryPeriodInfoListByPage(Integer currentPage, Integer  pageSize, Integer leaderId,
    		QueryWorkVo queryWorkVo) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<QueryWorkVo> workList = projectMapper.queryPeriodByPage(queryWorkVo, leaderId);
        PageInfo<QueryWorkVo> pageInfo = new PageInfo<QueryWorkVo>(workList);
        return pageInfo;
    }
    
    /**
     * <pre>
     * TODO
     * </pre>
     */
    public QueryWorkVo getProjectPeriodById(int periodId) throws Exception {
        // TODO Auto-generated method stub
        return projectMapper.getPeriodById(periodId);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public void updatePeriodStatus(int periodId) throws Exception {
        // TODO Auto-generated method stub
        projectMapper.updatePeriodStatus(periodId);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public void modifyPeriodInfo(ProjectPeriod periodInfo) throws Exception {
        // TODO Auto-generated method stub
        projectMapper.updatePeriodInfo(periodInfo);
    }
    
    public int projectTotalCount() {
    	return projectMapper.projectTotalCount();
    }

}
