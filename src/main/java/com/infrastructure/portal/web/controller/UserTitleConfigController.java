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
import com.infrastructure.portal.entity.po.config.UserTitleConfig;
import com.infrastructure.portal.service.config.UserTitleConfigService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class UserTitleConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTitleConfigController.class);
    private static final String TITLE_CONFIG_SEARCH = "TITLE_CONFIG_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private UserTitleConfigService userTitleConfigService;

	/**
	 * 获取用户职称配置列表
	 */
	@RequiresPermissions("portal:config:title")
	@RequestMapping(value = "user_title_config/show_config_list",method = RequestMethod.GET)
    public String showUserTitleConfigList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        UserTitleConfig userTitleConfig = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(TITLE_CONFIG_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(TITLE_CONFIG_SEARCH);
            if (obj != null) {
                userTitleConfig = (UserTitleConfig) obj;
            }
        }
        PageInfo<UserTitleConfig> pageInfo = userTitleConfigService.queryUserTitleConfigListByPage(pageid, PAGESIZE, userTitleConfig);
        List<UserTitleConfig> userTitleConfigList = pageInfo.getList();
        model.addAttribute("userTitleConfigList", userTitleConfigList);
        model.addAttribute("userTitleConfig", userTitleConfig);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/user_title_config/show_config_list?");
        //return "portal_user/show_user_list";
        return "user_title_config_list";
    }
	
	/**
	 * 查询用户职称
	 */
	@RequiresPermissions("portal:config:title")
	@RequestMapping(value = "user_title_config/search", method = RequestMethod.POST)
    public String searchUserTitleConfig(UserTitleConfig userTitleConfig,Model model) throws Exception {
	    int pageid = 0;

        if (userTitleConfig != null) {
            MVCUtil.setSessionAttribute(TITLE_CONFIG_SEARCH, userTitleConfig);
            pageid = 1;
        }
        return "redirect:/user_title_config/show_config_list?pageid=" + pageid;
    }
	
	//@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "user_title_config/delete", method = RequestMethod.GET)
    public String deleteUserTitleConfig(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           userTitleConfigService.deleteUserTitleConfig(id);
       return "redirect:/user_title_config/show_config_list";
    }
	
	/**
	 * 添加用户职称配置
	 */
	@RequiresPermissions("portal:config:title")
    @RequestMapping(value = "user_title_config/addUserTitleConfig", method = RequestMethod.POST)
	@ResponseBody
    public void addUserTitleConfig(UserTitleConfig userTitleConfig,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
      
       if (StringUtils.isBlank(userTitleConfig.getTitleName())) {
           msg = "职称名称不能为空！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           userTitleConfig.setCreateTime(new Date());
           userTitleConfigService.addUserTitleConfig(userTitleConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该职称已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改用户职称配置
     */
    @RequiresPermissions("portal:config:title")
    @RequestMapping(value = "user_title_config/modifyUserTitleConfig", method = RequestMethod.POST)
    @ResponseBody
    public void modifyUserTitleConfig(UserTitleConfig userTitleConfig,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(userTitleConfig.getTitleName())) {
           msg = "职称名称不能为空！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           userTitleConfigService.modifyUserTitleConfig(userTitleConfig);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
