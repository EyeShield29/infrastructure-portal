package com.infrastructure.portal.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.infrastructure.portal.entity.po.project.ProjectType;
import com.infrastructure.portal.service.config.ProjectTypeConfigService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class ProjectTypeConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTypeConfigController.class);
    private static final String PROJECT_TYPE_CONFIG_SEARCH = "PROJECT_TYPE_CONFIG_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private ProjectTypeConfigService projectTypeConfigService;

	/**
	 * 获取项目类别配置列表
	 */
	@RequiresPermissions("portal:config:type")
	@RequestMapping(value = "project_type_config/show_config_list",method = RequestMethod.GET)
    public String showProjectTypeConfigList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        ProjectType projectTypeConfig = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(PROJECT_TYPE_CONFIG_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(PROJECT_TYPE_CONFIG_SEARCH);
            if (obj != null) {
                projectTypeConfig = (ProjectType) obj;
            }
        }
        PageInfo<ProjectType> pageInfo = projectTypeConfigService.queryProjectTypeConfigListByPage(pageid, PAGESIZE, projectTypeConfig);
        List<ProjectType> projectTypeConfigList = pageInfo.getList();
        model.addAttribute("projectTypeConfigList", projectTypeConfigList);
        model.addAttribute("projectTypeConfig", projectTypeConfig);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/project_type_config/show_config_list?");
        //return "portal_user/show_user_list";
        return "project_type_config_list";
    }
	
	/**
	 * 查询项目类别配置
	 */
	@RequiresPermissions("portal:config:type")
	@RequestMapping(value = "project_type_config/search", method = RequestMethod.POST)
    public String searchProjectTypeConfig(ProjectType projectTypeConfig,Model model) throws Exception {
	    int pageid = 0;
        if (projectTypeConfig != null) {
            MVCUtil.setSessionAttribute(PROJECT_TYPE_CONFIG_SEARCH, projectTypeConfig);
            pageid = 1;
        }
        return "redirect:/project_type_config/show_config_list?pageid=" + pageid;
    }
	
	/**
     * 删除项目类别配置
     */
	@RequiresPermissions("portal:config:type")
	@RequestMapping(value = "project_type_config/delete", method = RequestMethod.GET)
    public String deleteProjectTypeConfig(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           projectTypeConfigService.deleteProjectTypeConfig(id);
       return "redirect:/project_type_config/show_config_list";
    }
	
	/**
	 * 添加项目类别配置
	 */
	@RequiresPermissions("portal:config:type")
    @RequestMapping(value = "project_type_config/addProjectTypeConfig", method = RequestMethod.POST)
	@ResponseBody
    public void addProjectTypeConfig(ProjectType projectTypeConfig,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
      
       if (StringUtils.isBlank(projectTypeConfig.getTypeName())) {
           msg = "职称名称不能为空！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           projectTypeConfig.setCreateTime(new Date());
           projectTypeConfigService.addProjectTypeConfig(projectTypeConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该职称已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改项目类别配置
     */
    @RequiresPermissions("portal:config:type")
    @RequestMapping(value = "project_type_config/modifyProjectTypeConfig", method = RequestMethod.POST)
    @ResponseBody
    public void modifyProjectTypeConfig(ProjectType projectTypeConfig,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(projectTypeConfig.getTypeName())) {
           msg = "职称名称不能为空！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           projectTypeConfigService.modifyProjectTypeConfig(projectTypeConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
