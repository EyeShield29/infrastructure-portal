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
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.vo.QueryPortalRoleVo;
import com.infrastructure.portal.service.portal.PortalFunctionService;
import com.infrastructure.portal.service.portal.PortalRoleService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class PortalRoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalRoleController.class);
    private static final String PORTAL_ROLE_SEARCH = "PORTAL_ROLE_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private PortalRoleService portalRoleService;
	@Autowired
    private PortalFunctionService portalFunctionService;

	/**
	 * 获取角色列表
	 */
	@RequiresPermissions("portal:role:list")
	@RequestMapping(value = "portal_role/show_role_list",method = RequestMethod.GET)
    public String showPortalRoleList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        PortalRole portalRole = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(PORTAL_ROLE_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(PORTAL_ROLE_SEARCH);
            if (obj != null) {
                portalRole = (PortalRole) obj;
            }
        }
        PageInfo<PortalRole> pageInfo = portalRoleService.queryPortalRoleListByPage(pageid, PAGESIZE, portalRole);
        List<PortalRole> portalRoleList = pageInfo.getList();
        List<QueryPortalRoleVo> portalRoleVoList = portalRoleService.getPortalRoleVoList(portalRoleList);
        List<PortalFunction> functionList = portalFunctionService.getFunctionList();
        List<PortalRole> portalRoleNameList = portalRoleService.getRoleListName();
        model.addAttribute("portalRoleNameList", portalRoleNameList);
        model.addAttribute("functionList", functionList);
        model.addAttribute("portalRoleList", portalRoleVoList);
        model.addAttribute("portalRole", portalRole);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/portal_role/show_role_list?");
        //return "portal_user/show_user_list";
        return "portal_role_list";
    }
	
	/**
	 * 查询角色
	 */
	@RequiresPermissions("portal:role:list")
	@RequestMapping(value = "portal_role/search", method = RequestMethod.POST)
    public String searchPortalRole(PortalRole portalRole,Model model) throws Exception {
	    int pageid = 0;
        if (portalRole != null) {
            MVCUtil.setSessionAttribute(PORTAL_ROLE_SEARCH, portalRole);
            pageid = 1;
        }
        return "redirect:/portal_role/show_role_list?pageid=" + pageid;
    }
	
	/**
	 * <pre>
	 * TODO 删除角色
	 * </pre>
	 */
	@RequiresPermissions("portal:role:list")
	@RequestMapping(value = "portal_role/delete", method = RequestMethod.GET)
    public String deletePortalRole(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           portalRoleService.deletePortalRole(id);
       return "redirect:/portal_role/show_role_list";
    }
	
	/**
	 * 添加角色
	 */
	@RequiresPermissions("portal:role:list")
    @RequestMapping(value = "portal_role/addPortalRole", method = RequestMethod.POST)
	@ResponseBody
    public void addPortalRole(PortalRole portalRole,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
	   int[] functions = MVCUtil.getIntParamArray("functions[]");
      
       if (StringUtils.isBlank(portalRole.getRoleName())) {
           msg = "角色名称不能为空！";
       } else if (StringUtils.isBlank(portalRole.getRoleCode())) {
           msg = "角色code不能为空！";
       } else if (null==functions) {
           msg = "请选择角色所对应的功能权限！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalRole.setCreateTime(new Date());
           portalRoleService.addPortalRole(portalRole, functions);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该角色已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改角色信息
     */
    @RequiresPermissions("portal:role:list")
    @RequestMapping(value = "portal_role/modifyPortalRole", method = RequestMethod.POST)
    @ResponseBody
    public void modifyPortalRole(PortalRole portalRole, Model model){
       AjaxData ajaxdata;
       String msg = null;
       int[] functions = MVCUtil.getIntParamArray("functions[]");
       
       if (StringUtils.isBlank(portalRole.getRoleName())) {
           msg = "角色名称不能为空！";
       } else if (StringUtils.isBlank(portalRole.getRoleCode())) {
           msg = "角色code不能为空！";
       } else if (null==functions) {
           msg = "请选择角色所对应的功能权限！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalRoleService.modifyPortalRole(portalRole, functions);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
