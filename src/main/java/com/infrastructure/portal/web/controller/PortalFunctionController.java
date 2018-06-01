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
import com.infrastructure.portal.entity.po.portal.PortalFunction;
import com.infrastructure.portal.service.portal.PortalFunctionService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class PortalFunctionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalFunctionController.class);
    private static final String PORTAL_FUNCTION_SEARCH = "PORTAL_FUNCTION_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private PortalFunctionService portalFunctionService;

	/**
	 * 获取功能权限列表
	 */
	@RequiresPermissions("portal:function:list")
	@RequestMapping(value = "portal_function/show_function_list",method = RequestMethod.GET)
    public String showPortalFunctionList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        PortalFunction portalFunction = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(PORTAL_FUNCTION_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(PORTAL_FUNCTION_SEARCH);
            if (obj != null) {
                portalFunction = (PortalFunction) obj;
            }
        }
        PageInfo<PortalFunction> pageInfo = portalFunctionService.queryPortalFunctionListByPage(pageid, PAGESIZE, portalFunction);
        List<PortalFunction> portalFunctionList = pageInfo.getList();
        model.addAttribute("portalFunctionList", portalFunctionList);
        model.addAttribute("portalFunction", portalFunction);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/portal_function/show_function_list?");
        //return "portal_user/show_user_list";
        return "portal_function_list";
    }
	
	/**
	 * 查询功能权限
	 */
	@RequiresPermissions("portal:function:list")
	@RequestMapping(value = "portal_function/search", method = RequestMethod.POST)
    public String searchPortalFunction(PortalFunction portalFunction,Model model) throws Exception {
	    int pageid = 0;
        if (portalFunction != null) {
            MVCUtil.setSessionAttribute(PORTAL_FUNCTION_SEARCH, portalFunction);
            pageid = 1;
        }
        return "redirect:/portal_function/show_function_list?pageid=" + pageid;
    }
	
	/**
	 * <pre>
	 * TODO 删除功能权限。暂时【废弃】，不能删除，会造成页面shiro控制报错
	 * </pre>
	 */
	@RequiresPermissions("portal:function:list")
	@RequestMapping(value = "portal_function/delete", method = RequestMethod.GET)
    public String deletePortalFunction(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           portalFunctionService.deletePortalFunction(id);
       return "redirect:/portal_function/show_function_list";
    }
	
	/**
	 * 添加功能权限，暂时【废弃】，由开发人员增加页面菜单并手工插入db
	 */
	@RequiresPermissions("portal:function:list")
    @RequestMapping(value = "portal_function/addPortalFunction", method = RequestMethod.POST)
	@ResponseBody
    public void addPortalFunction(PortalFunction portalFunction,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
      
       if (StringUtils.isBlank(portalFunction.getFunctionName())) {
           msg = "功能名称不能为空！";
       } else if (StringUtils.isBlank(portalFunction.getPermission())) {
           msg = "权限标识不能为空！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalFunction.setCreateTime(new Date());
           portalFunctionService.addPortalFunction(portalFunction);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该功能已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改功能权限信息
     */
    @RequiresPermissions("portal:role:list")
    @RequestMapping(value = "portal_function/modifyPortalFunction", method = RequestMethod.POST)
    @ResponseBody
    public void modifyPortalFunction(PortalFunction portalFunction,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(portalFunction.getFunctionName())) {
           msg = "功能名称不能为空！";
       } else if (StringUtils.isBlank(portalFunction.getPermission())) {
           msg = "权限标识不能为空！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalFunctionService.modifyPortalFunction(portalFunction);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
