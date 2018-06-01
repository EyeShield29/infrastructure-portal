package com.infrastructure.portal.web.controller;

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
import com.infrastructure.portal.entity.po.document.DocumentInfo;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.vo.ListProjectInfoVo;
import com.infrastructure.portal.entity.vo.QueryDocumentInfoVo;
import com.infrastructure.portal.service.document.DocumentInfoService;
import com.infrastructure.portal.service.project.ProjectService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private static final String DOCUMENT_SEARCH = "DOCUMENT_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private DocumentInfoService documentInfoService;
	@Autowired
    private ProjectService projectService;

	
	/**
	 * 获取项目列表
	 */
	@RequiresPermissions("portal:document:list")
	@RequestMapping(value = "document/show_document_list",method = RequestMethod.GET)
    public String showDocumentInfoList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryDocumentInfoVo queryDocumentInfoVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(DOCUMENT_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(DOCUMENT_SEARCH);
            if (obj != null) {
                queryDocumentInfoVo = (QueryDocumentInfoVo) obj;
            }
        }
        PageInfo<QueryDocumentInfoVo> pageInfo = documentInfoService.queryDocumentInfoListByPage(pageid, PAGESIZE, queryDocumentInfoVo);
        List<QueryDocumentInfoVo> documentInfoList = pageInfo.getList();
        List<ListProjectInfoVo> projectList = projectService.queryProjectVoList((Integer)(MVCUtil.getSessionAttribute("userid")));        
        model.addAttribute("projectList", projectList);
        model.addAttribute("documentInfoList", documentInfoList);
        model.addAttribute("queryDocumentInfoVo", queryDocumentInfoVo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/documentInfo/show_document_list?");
        //return "portal_user/show_user_list";
        return "document_list";
    }
	
	/**
	 * 查询附件
	 */
	@RequiresPermissions("portal:document:list")
	@RequestMapping(value = "document/search", method = RequestMethod.POST)
    public String searchUser(QueryDocumentInfoVo queryDocumentInfoVo,Model model) throws Exception {
	    int pageid = 0;
        if (queryDocumentInfoVo != null) {
            MVCUtil.setSessionAttribute(DOCUMENT_SEARCH, queryDocumentInfoVo);
            pageid = 1;
        }
        return "redirect:/document/show_document_list?pageid=" + pageid;
    }
	
	//@RequiresPermissions("portal:user:manage")
	@RequestMapping(value = "document/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           documentInfoService.deleteDocumentInfo(id);
       return "redirect:/document/show_document_list";
    }
	
	/**
     * 跳转到上传附件页面
     *//*
	//@RequiresPermissions("portal:user:manage")
    @RequestMapping(value = "document/add_document", method = RequestMethod.GET)
    public String goAddDocumentInfo(Model model) throws Exception {
       return "add_document";
    }*/
	
	/**
	 * 上传附件
	 */
	@RequiresPermissions("portal:document:list")
    @RequestMapping(value = "document/upload_document", method = RequestMethod.POST)
	@ResponseBody
    public void addDocumentInfo(DocumentInfo documentInfo, Model model){ 
	   AjaxData ajaxdata;
	   String msg = null;
      
       if (StringUtils.isBlank(documentInfo.getDocumentName())) {
           msg = "附件名称不能为空！";
       } else if (StringUtils.isBlank(documentInfo.getDocumentUri())) {
           msg = "请先上传文件！";
       } else if (null==documentInfo.getProjectId()) {
           msg = "请选择所属的项目！";
       } else if (null==documentInfo.getPeriod()) {
           msg = "请选择所属的阶段！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       
       try {
           //设置上传者
           PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
           documentInfo.setUploaderId(portalUser.getId());
           documentInfoService.addDocumentInfo(documentInfo);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "上传附件失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
	/**
     * 修改文件基本信息
     */
	@RequiresPermissions("portal:document:list")
    @RequestMapping(value = "document/modifyDocumentInfo", method = RequestMethod.POST)
    @ResponseBody
    public void modifyDocumentInfo(DocumentInfo documentInfo,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(documentInfo.getDocumentName())) {
           msg = "附件名称不能为空！";
       } else if (StringUtils.isBlank(documentInfo.getDocumentUri())) {
           msg = "附件链接地址不能为空！";       
       } else if (null==documentInfo.getProjectId()) {
           msg = "请选择所属的项目！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           documentInfoService.modifyDocumentInfo(documentInfo);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改项目信息失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
}
