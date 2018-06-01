package com.infrastructure.portal.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.infrastructure.portal.entity.po.config.ProcessConfig;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.po.project.ProjectInfo;
import com.infrastructure.portal.entity.po.project.ProjectPeriod;
import com.infrastructure.portal.entity.po.project.ProjectProcess;
import com.infrastructure.portal.entity.po.project.ProjectType;
import com.infrastructure.portal.entity.vo.ListUserVo;
import com.infrastructure.portal.entity.vo.PeriodListVo;
import com.infrastructure.portal.entity.vo.ProjectAjaxVo;
import com.infrastructure.portal.entity.vo.QueryProjectVo;
import com.infrastructure.portal.entity.vo.QueryWorkVo;
import com.infrastructure.portal.service.config.ProcessConfigService;
import com.infrastructure.portal.service.config.ProjectTypeConfigService;
import com.infrastructure.portal.service.project.ProjectService;
import com.infrastructure.portal.service.user.PortalUserService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class ProjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    private static final String PROJECT_SEARCH = "PROJECT_SEARCH";
    private static final String PERIOD_SEARCH = "PERIOD_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private ProjectService projectService;
	@Autowired
    private PortalUserService userService;
	@Autowired
    private ProjectTypeConfigService projectTypeService;
	@Autowired
    private ProcessConfigService processConfigService;

	
	/**
	 * 获取项目列表
	 */
	@RequiresPermissions("portal:project:list")
	@RequestMapping(value = "project/show_project_list",method = RequestMethod.GET)
    public String showProjectList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryProjectVo queryProjectVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(PROJECT_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(PROJECT_SEARCH);
            if (obj != null) {
                queryProjectVo = (QueryProjectVo) obj;
            }
        }
        PageInfo<QueryProjectVo> pageInfo = projectService.queryProjectInfoListByPage(pageid, PAGESIZE, queryProjectVo);
        List<QueryProjectVo> projectInfoList = pageInfo.getList();
        List<ProjectType> projectTypeNameList = projectTypeService
        		.queryProjectTypeNameConfigList();
        model.addAttribute("projectTypeList",projectTypeNameList);
        model.addAttribute("projectInfoList", projectInfoList);
        model.addAttribute("queryProjectVo", queryProjectVo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/project/show_project_list?");
        return "project_list";
    }
	
	/**
	 * 查询项目
	 */
	@RequiresPermissions("portal:project:list")
	@RequestMapping(value = "project/search", method = RequestMethod.POST)
    public String searchProject(QueryProjectVo queryProjectVo,Model model) throws Exception {
	    int pageid = 0;
        if (queryProjectVo != null) {
            MVCUtil.setSessionAttribute(PROJECT_SEARCH, queryProjectVo);
            pageid = 1;
        }
        return "redirect:/project/show_project_list?pageid=" + pageid;
    }
	
	/**
     * 获取我的工作
     */
    @RequiresPermissions("portal:work:list")
    @RequestMapping(value = "work/show_work_list",method = RequestMethod.GET)
    public String showPeriodList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryWorkVo queryWorkVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(PERIOD_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(PERIOD_SEARCH);
            if (obj != null) {
                queryWorkVo = (QueryWorkVo) obj;
            }
        }
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
        int leaderId = portalUser.getId();
        PageInfo<QueryWorkVo> pageInfo = projectService.queryPeriodInfoListByPage(pageid, PAGESIZE, leaderId, queryWorkVo);
        List<QueryWorkVo> workList = pageInfo.getList();
        model.addAttribute("workList", workList);
        model.addAttribute("queryWorkVo", queryWorkVo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/work/show_work_list?");
        //return "portal_user/show_user_list";
        return "work_list";
    }
    
    /**
     * 查询项目
     */
    @RequiresPermissions("portal:work:list")
    @RequestMapping(value = "work/search", method = RequestMethod.POST)
    public String searchPeriod(QueryWorkVo queryWorkVo,Model model) throws Exception {
        int pageid = 0;
        if (queryWorkVo != null) {
            MVCUtil.setSessionAttribute(PERIOD_SEARCH, queryWorkVo);
            pageid = 1;
        }
        return "redirect:/work/show_work_list?pageid=" + pageid;
    }
	
    /**
     * <pre>
     * TODO 删除项目，暂时【废弃】
     * </pre>
     */
	@RequiresPermissions("portal:project:manage")
	@RequestMapping(value = "project/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           projectService.deleteProjectInfo(id);
       return "redirect:/project/show_project_list";
    }
	
	/**
     * 跳转到添加项目页面
     */
	@RequiresPermissions("portal:project:add")
    @RequestMapping(value = "project/add_project", method = RequestMethod.GET)
    public String goAddProjectInfo(Model model) throws Exception {
        
        List<ListUserVo> userList = userService.queryUserVoList();
        List<ProjectType> typeList = projectTypeService.queryProjectTypeList();
        List<ProcessConfig> processList1 = processConfigService.queryProcessConfigList(1);
        List<ProcessConfig> processList2 = processConfigService.queryProcessConfigList(2);
        List<ProcessConfig> processList3 = processConfigService.queryProcessConfigList(3);
        List<ProcessConfig> processList4 = processConfigService.queryProcessConfigList(4);
        List<ProcessConfig> processList5 = processConfigService.queryProcessConfigList(5);
        List<ProcessConfig> processList6 = processConfigService.queryProcessConfigList(6);
        
        model.addAttribute("userList", userList);
        model.addAttribute("typeList", typeList);
        model.addAttribute("processList1", processList1);
        model.addAttribute("processList2", processList2);
        model.addAttribute("processList3", processList3);
        model.addAttribute("processList4", processList4);
        model.addAttribute("processList5", processList5);
        model.addAttribute("processList6", processList6);
        return "add_project";
    }
	
	/**
	 * 添加新项目
	 */
	@RequiresPermissions("portal:project:add")
    @RequestMapping(value = "project/addProjectInfo", method = RequestMethod.POST)
	@ResponseBody
    public void addProjectInfo(ProjectInfo projectInfo,Model model){ 
	   AjaxData ajaxdata;
	   String msg = null;
	   /*if (StringUtils.isBlank(projectInfo.getProjectName())) {
           msg = "项目名称不能为空！";
       } else if (StringUtils.isBlank(projectInfo.getProjectNum())) {
           msg = "项目编号不能为空！";
       } else if (StringUtils.isBlank(projectInfo.getTypeName())) {
           msg = "请选择项目的类型！";
       }  else if (StringUtils.isBlank(projectInfo.getDescription())) {
           msg = "项目描述不能为空！";       
       } else if (null==projectInfo.getLeaderId()) {
           msg = "请选择项目负责人！";
       } */

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
           
           projectInfo.setEntryStaffAccount(portalUser.getAccount());
           projectInfo.setCreateTime(new Date());
           int projectId = projectService.addProjectInfo(projectInfo);
           projectService.addProjectPeriod(projectId, projectInfo.getLeaderId(), projectInfo.getProjectStartDate(), projectInfo.getProjectEndDate());
           JsonObject data = new JsonObject();
           data.addProperty("projectId", projectId);
           ajaxdata = new AjaxData(true, data, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "添加项目失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 添加项目流程
     */
    @RequiresPermissions("portal:project:add")
    @RequestMapping(value = "project/addProjectProcess", method = RequestMethod.POST)
    @ResponseBody
    public void addProjectProcess(Model model){ 
       AjaxData ajaxdata;
       String msg = null;
       int projectID = MVCUtil.getIntParam("projectID");
      
       String[] processOfPeriod1 = MVCUtil.getParamArray("period1[]");
       String[] processOfPeriod2 = MVCUtil.getParamArray("period2[]");
       String[] processOfPeriod3 = MVCUtil.getParamArray("period3[]");
       String[] processOfPeriod4 = MVCUtil.getParamArray("period4[]");
       String[] processOfPeriod5 = MVCUtil.getParamArray("period5[]");
       String[] processOfPeriod6 = MVCUtil.getParamArray("period6[]");
       
       System.out.println();

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           if(null!=processOfPeriod1){
               projectService.addProjectProcess(processOfPeriod1, projectID, 1);
           }
           if(null!=processOfPeriod2){
               projectService.addProjectProcess(processOfPeriod2, projectID, 2);
           }
           if(null!=processOfPeriod3){
               projectService.addProjectProcess(processOfPeriod3, projectID, 3);
           }
           if(null!=processOfPeriod4){
               projectService.addProjectProcess(processOfPeriod4, projectID, 4);
           }
           if(null!=processOfPeriod5){
               projectService.addProjectProcess(processOfPeriod5, projectID, 5);
           }
           if(null!=processOfPeriod6){
               projectService.addProjectProcess(processOfPeriod6, projectID, 6);
           }
           
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "添加项目流程失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 添加项目阶段信息
     */
    @RequiresPermissions("portal:project:add")
    @RequestMapping(value = "project/addProjectPeriod", method = RequestMethod.POST)
    @ResponseBody
    public void addProjectPeriod(PeriodListVo periodList, Model model){ 
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           projectService.addProjectPeriod(periodList);
           
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "添加项目流程失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改项目基本信息
     */
    @RequiresPermissions("portal:project:manage")
    @RequestMapping(value = "project/modifyProjectInfo", method = RequestMethod.POST)
    @ResponseBody
    public void modifyProjectInfo(ProjectInfo projectInfo,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(projectInfo.getProjectName())) {
           msg = "项目名称不能为空！";
       } else if (StringUtils.isBlank(projectInfo.getTypeName())) {
           msg = "请选择项目的类型！";
       }  else if (StringUtils.isBlank(projectInfo.getDescription())) {
           msg = "项目描述不能为空！";       
       } else if (null==projectInfo.getLeaderId()) {
           msg = "请选择项目负责人！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           projectService.modifyProjectInfo(projectInfo);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改项目信息失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 修改项目流程
     */
    @RequiresPermissions("portal:project:manage")
    @RequestMapping(value = "project/modifyProjectProcess", method = RequestMethod.POST)
    @ResponseBody
    public void modifyProjectProcess(Model model){ 
       AjaxData ajaxdata;
       String msg = null;
       int projectId = MVCUtil.getIntParam("projectId");
      
       //String[] processOfPeriod1 = MVCUtil.getParamArray("period1");
       String[] processOfPeriod2 = MVCUtil.getParamArray("period2");
       String[] processOfPeriod3 = MVCUtil.getParamArray("period3");
       String[] processOfPeriod4 = MVCUtil.getParamArray("period4");
       String[] processOfPeriod5 = MVCUtil.getParamArray("period5");
       String[] processOfPeriod6 = MVCUtil.getParamArray("period6");

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           //获取项目当前所处流程，即project_info中的
           QueryProjectVo project = projectService.getProjectInfoById(projectId);
           int status = project.getStatus();
           
           if(status==0||status==7){
               msg = "该项目所有流程已完成，无法修改！";
               ajaxdata = new AjaxData(false, null, msg);
               MVCUtil.ajaxJson(ajaxdata);
               return;
           }
           
           if(status<6){
               projectService.modifyProjectProcess(processOfPeriod6, projectId, 6);
           }
           if(status<5){
               projectService.modifyProjectProcess(processOfPeriod5, projectId, 5);
           }
           if(status<4){
               projectService.modifyProjectProcess(processOfPeriod4, projectId, 4);
           }
           if(status<3){
               projectService.modifyProjectProcess(processOfPeriod3, projectId, 3);
           }
           if(status<2){
               projectService.modifyProjectProcess(processOfPeriod2, projectId, 2);
           }
           
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改项目流程失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 修改项目基本信息
     */
    @RequiresPermissions("portal:project:manage")
    @RequestMapping(value = "period/modifyPeriodInfo", method = RequestMethod.POST)
    @ResponseBody
    public void modifyPeriodInfo(ProjectPeriod periodInfo,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (null==periodInfo.getPeriodStartDate()) {
           msg = "请选择阶段开始日期！";
       } else if (null==periodInfo.getPeriodEndDate()) {
           msg = "请选择阶段完成日期！";
       }  

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           projectService.modifyPeriodInfo(periodInfo);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改项目信息失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 修改项目流程状态
     */
    @RequiresPermissions("portal:project:manage")
    @RequestMapping(value = "project/finishProjectProcess", method = RequestMethod.POST)
    @ResponseBody
    public void finishProjectProcess(Model model){ 
       AjaxData ajaxdata;
       String msg = null;
       int projectId = MVCUtil.getIntParam("projectId");
       String remark = MVCUtil.getParam("remark");
       int processId = MVCUtil.getIntParam("processId");
       int period = MVCUtil.getIntParam("period");
       int periodId = MVCUtil.getIntParam("periodId");
       
       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           
           //修改该流程为已完成
           projectService.updateProjectProcess(processId, remark);
           
           //检查该阶段是否仍有未完成流程
           int count = projectService.checkUnfinishProcess(projectId, period);
           
           //若不存在未完成流程，则修改项目信息中的状态为下一阶段
           if(count==0){
               projectService.updateProjectStatus(projectId,period+1);
               projectService.updatePeriodStatus(periodId);
           }
           
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "项目流程状态修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 查看项目详情
     */
    @RequiresPermissions("portal:project:manage")
    @RequestMapping(value = "project/view_project", method = RequestMethod.GET)
    public String goViewProjectInfo(Model model) throws Exception {
        
        int projectId = MVCUtil.getIntParam("projectId");
        
        QueryProjectVo projectInfo = projectService.getProjectInfoById(projectId);
        
        List<ProjectProcess> period1 = projectService.queryProjectProcessList(projectId, 1);
        List<ProjectProcess> period2 = projectService.queryProjectProcessList(projectId, 2);
        List<ProjectProcess> period3 = projectService.queryProjectProcessList(projectId, 3);
        List<ProjectProcess> period4 = projectService.queryProjectProcessList(projectId, 4);
        List<ProjectProcess> period5 = projectService.queryProjectProcessList(projectId, 5);
        List<ProjectProcess> period6 = projectService.queryProjectProcessList(projectId, 6);
        
        
        List<ListUserVo> userList = userService.queryUserVoList();
        List<ProjectType> typeList = projectTypeService.queryProjectTypeList();
        
        int processId = projectService.queryOnProjectProcess(projectId, projectInfo.getStatus());
        
        model.addAttribute("userList", userList);
        model.addAttribute("typeList", typeList);
        
        model.addAttribute("processId", processId);
        model.addAttribute("projectInfo", projectInfo);
        model.addAttribute("period1", period1);
        model.addAttribute("period2", period2);
        model.addAttribute("period3", period3);
        model.addAttribute("period4", period4);
        model.addAttribute("period5", period5);
        model.addAttribute("period6", period6);
        
        return "project_info";
    }
    
    /**
     * <pre>
     * TODO 异步获取未完成的项目
     * </pre>
     */
    @RequestMapping(value = "project/show_unfinish_project_list",method = RequestMethod.GET)
    @ResponseBody
    public void showUnfinishProjectList(Model model) throws Exception {
        
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
                .getPrincipal();
        
        List<ProjectInfo> projectList = projectService.queryUnfinishPorject(portalUser.getId());
        
        List<ProjectAjaxVo> projectVoList = projectService.getProjectAjaxVo(projectList);
        
        AjaxData ajaxdata = null;
        
        JsonObject data = new JsonObject();
        Gson gson = new Gson();
        String projectListStr = gson.toJson(projectVoList);
        //将list存入jsonObject
        data.addProperty("projectCount", projectVoList.size());
        data.addProperty("unfinishProjectList", projectListStr);
        ajaxdata = new AjaxData(true, data, null);
        MVCUtil.ajaxJson(ajaxdata);
        
    }
    
    /**
     * 查看我的阶段任务详情
     */
    @RequiresPermissions("portal:work:list")
    @RequestMapping(value = "work/view_work", method = RequestMethod.GET)
    public String goViewPeriodInfo(Model model) throws Exception {
        
        int projectId = MVCUtil.getIntParam("projectId");
        int periodId = MVCUtil.getIntParam("periodId");
        int period = MVCUtil.getIntParam("period");
        
        QueryProjectVo projectInfo = projectService.getProjectInfoById(projectId);
        QueryWorkVo workInfo = projectService.getProjectPeriodById(periodId);
        
        List<ProjectProcess> processList = projectService.queryProjectProcessList(projectId, period);
        
        int processId = projectService.queryOnProjectProcess(projectId, projectInfo.getStatus());
        
        model.addAttribute("processId", processId);
        model.addAttribute("projectInfo", projectInfo);
        model.addAttribute("workInfo", workInfo);
        model.addAttribute("processList", processList);
        
        return "work_info";
    }
}
