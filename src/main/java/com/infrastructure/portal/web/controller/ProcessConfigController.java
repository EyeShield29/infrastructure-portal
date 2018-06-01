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
import com.infrastructure.portal.entity.po.config.ProcessConfig;
import com.infrastructure.portal.service.config.ProcessConfigService;
import com.infrastructure.portal.utils.PeriodMap;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class ProcessConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessConfigController.class);
    private static final String CONFIG_SEARCH = "CONFIG_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private ProcessConfigService processConfigService;
	
	/**
	 * 获取流程配置列表
	 */
	@RequiresPermissions("portal:config:process")
	@RequestMapping(value = "process_config/show_config_list",method = RequestMethod.GET)
    public String showProcessConfigList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        ProcessConfig processConfig = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(CONFIG_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(CONFIG_SEARCH);
            if (obj != null) {
                processConfig = (ProcessConfig) obj;
            }
        }
        PageInfo<ProcessConfig> pageInfo = processConfigService.queryProcessConfigListByPage(pageid, PAGESIZE, processConfig);
        List<ProcessConfig> processConfigList = pageInfo.getList();
        model.addAttribute("processConfigList", processConfigList);
        model.addAttribute("processConfig", processConfig);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/process_config/show_config_list?");
        //return "portal_user/show_user_list";
        return "process_config_list";
    }
	
	/**
	 * 查询项目流程配置信息
	 */
	@RequiresPermissions("portal:config:process")
	@RequestMapping(value = "process_config/search", method = RequestMethod.POST)
    public String searchProcessConfig(ProcessConfig processConfig,Model model) throws Exception {
	    int pageid = 0;
        if (processConfig != null) {
            MVCUtil.setSessionAttribute(CONFIG_SEARCH, processConfig);
            pageid = 1;
        }
        return "redirect:/process_config/show_config_list?pageid=" + pageid;
    }
	
	/**
	 * <pre>
	 * TODO 删除项目流程配置项
	 * </pre>
	 */
	@RequiresPermissions("portal:config:process")
	@RequestMapping(value = "process_config/delete", method = RequestMethod.GET)
    public String deleteProcessConfig(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           processConfigService.deleteProcessConfig(id);
       return "redirect:/process_config/show_config_list";
    }
	
	/**
	 * 添加流程配置
	 * @throws Exception 
	 */
	@RequiresPermissions("portal:config:process")
    @RequestMapping(value = "process_config/addProcessConfig", method = RequestMethod.POST)
	@ResponseBody
    public void addProcessConfig(ProcessConfig processConfig,Model model) throws Exception{
	   AjaxData ajaxdata;
	   String msg = null;
      
       if (StringUtils.isBlank(processConfig.getProcessName())) {
           msg = "流程名称不能为空！";
       } else if (StringUtils.isBlank(processConfig.getDescription())) {
           msg = "流程描述不能为空！";
       }else if(null==processConfig.getSerialNum()){
           msg = "流程所处阶段中的序号不能为空！";
       }else if(null==processConfig.getPeriod()){
           msg = "请选择流程所属阶段！";
       }else if(processConfigService.checkProcessSerialNum(processConfig.getPeriod(), processConfig.getSerialNum())){
           msg = "该阶段中已存在此序号的流程，请修改流程序号！";
       }
       
       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       
       try {
           processConfig.setPeriodName(PeriodMap.getPeriodName(processConfig.getPeriod()));
           processConfig.setCreateTime(new Date());
           processConfigService.addProcessConfig(processConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该流程已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改流程配置
     */
    @RequiresPermissions("portal:config:process")
    @RequestMapping(value = "process_config/modifyProcessConfig", method = RequestMethod.POST)
    @ResponseBody
    public void modifyProcessConfig(ProcessConfig processConfig,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(processConfig.getProcessName())) {
           msg = "流程名称不能为空！";
       } else if (StringUtils.isBlank(processConfig.getDescription())) {
           msg = "流程描述不能为空！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           processConfig.setPeriodName(PeriodMap.getPeriodName(processConfig.getPeriod()));
           processConfigService.modifyProcessConfig(processConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
