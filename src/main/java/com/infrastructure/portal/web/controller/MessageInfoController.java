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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.message.MessageInfo;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.vo.ListUserVo;
import com.infrastructure.portal.service.message.MessageInfoService;
import com.infrastructure.portal.service.user.PortalUserService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class MessageInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageInfoController.class);
    private static final String MESSAGE_SEARCH = "MESSAGE_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private MessageInfoService messageInfoService;
	
	@Autowired
	private PortalUserService userService;
	/**
	 * 获取信件列表
	 * type区分发送列表或接收列表，0为发送列表，1为接收列表
	 */
	@RequiresPermissions("portal:message:manage")
	@RequestMapping(value = "message/show_message_list",method = RequestMethod.GET)
    public String showMessageList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        int type = MVCUtil.getIntParam("type");
        int isReaded = MVCUtil.getIntParam("isReaded");      
        MessageInfo messageInfo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(MESSAGE_SEARCH);
            messageInfo = new MessageInfo();
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(MESSAGE_SEARCH);
            if (obj != null) {
                messageInfo = (MessageInfo) obj;
            }else{
                messageInfo = new MessageInfo();
            }
        }       
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
        String account = portalUser.getAccount();
        if(1==type){
            messageInfo.setSenderAccount(account);
        }else if(0==type){
            messageInfo.setReceiverAccount(account);
            if(0==isReaded){
                messageInfo.setIsReaded(isReaded);
            }
        }
        PageInfo<MessageInfo> pageInfo = messageInfoService.queryMessageInfoListByPage(pageid, PAGESIZE, messageInfo);
        List<MessageInfo> messageInfoList = pageInfo.getList();
        model.addAttribute("messageInfoList", messageInfoList);
        model.addAttribute("messageInfo", messageInfo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/message/show_message_list?");
        //return "message/show_message_list";
        if(1==type){
            return "message_sent_list";
            
        }else if(0==type){
            return "message_received_list";
        }
        return null;
    }
	
	/**
     * 异步获取未读信件列表
     */
	//@RequiresPermissions("portal:message:manage")
    @RequestMapping(value = "message/show_unread_message_list",method = RequestMethod.GET)
    @ResponseBody
    public void showUnreadMessageList(Model model) throws Exception {
        MessageInfo messageInfo = new MessageInfo();
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
                .getPrincipal();
        messageInfo.setReceiverAccount(portalUser.getAccount());
        messageInfo.setIsReaded(0);
        List<MessageInfo> messageInfoList = messageInfoService
                .queryAllUnreadMessageInfo(messageInfo);
        AjaxData ajaxdata = null;
        
        JsonObject messageList = new JsonObject();
        Gson gson = new Gson();
        String messageListStr = gson.toJson(messageInfoList);
        //将list存入jsonObject
        messageList.addProperty("messageCount", messageInfoList.size());
        messageList.addProperty("unreadMessageList", messageListStr);
        ajaxdata = new AjaxData(true, messageList, null);
        MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
	 * 查询发送信件
	 */
    @RequiresPermissions("portal:message:manage")
	@RequestMapping(value = "message/search_sent", method = RequestMethod.POST)
    public String searchUserBySent(MessageInfo messageInfo,Model model) throws Exception {
	    int pageid = 0;
	    int type = 1;
        if (messageInfo != null) {
            MVCUtil.setSessionAttribute(MESSAGE_SEARCH, messageInfo);
            pageid = 1;
        }
        return "redirect:/message/show_message_list?pageid=" + pageid + "&type=" + type;
    }
    
    /**
	 * 查询接收信件
	 */
    @RequiresPermissions("portal:message:manage")
	@RequestMapping(value = "message/search_receive", method = RequestMethod.POST)
    public String searchUserByReceive(MessageInfo messageInfo,Model model) throws Exception {
	    int pageid = 0;
	    int type = 0;
        if (messageInfo != null) {
            MVCUtil.setSessionAttribute(MESSAGE_SEARCH, messageInfo);
            pageid = 1;
        }
        return "redirect:/message/show_message_list?pageid=" + pageid + "&type=" + type;
    }
    
    /**
     * <pre>
     * TODO 删除信件
     * </pre>
     */
    @RequiresPermissions("portal:message:manage")
	@RequestMapping(value = "message/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       int type = MVCUtil.getIntParam("type");
       if(id>0)
           messageInfoService.deleteMessageInfo(id,type);
       return "redirect:/message/show_message_list";
    }
	
	/**
     * 跳转到添加信件页面
     */
	@RequiresPermissions("portal:message:send")
    @RequestMapping(value = "message/sendMessage", method = RequestMethod.GET)
    public String goSendMessage(Model model) throws Exception {
		List<ListUserVo> userList = userService.queryUserVoList();
		model.addAttribute("userList", userList);
       return "send_message";
    }
	
	/**
	 * 发送信件
	 */
	@RequiresPermissions("portal:message:send")
    @RequestMapping(value = "message/sendMessage", method = RequestMethod.POST)
	@ResponseBody
    public void addMessageInfo(MessageInfo messageInfo, Model model){
	   AjaxData ajaxdata;
	   String msg = null;
	   int type = 1;
      
       if (StringUtils.isBlank(messageInfo.getMessageTitle())) {
           msg = "信件标题不能为空！";
       } else if (StringUtils.isBlank(messageInfo.getContent())) {
           msg = "信件内容不能为空！";
       } else if(StringUtils.isBlank(messageInfo.getReceiverAccount())){
           msg = "请选择所发送的用户账号！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
           if(StringUtils.isBlank(messageInfo.getTransId())){
               messageInfo.setTransId("111111");
               type = 0;
           }
           
           messageInfo.setSenderAccount(portalUser.getAccount());
           messageInfo.setSendTime(new Date());
           messageInfo.setSenderVisible(1);
           messageInfo.setReceiverVisible(1);
           messageInfo.setIsReaded(0);
           messageInfo.setCreateTime(new Date());
           int id = messageInfoService.addMessageInfo(messageInfo);
           if(type==1){
               int messageId = 123456;
               messageInfoService.updateMessageInfo(messageId,id);
           }
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该信件已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
    /**
     * 获取信件详情
     * type区分发送列表或接收列表，0为发送列表，1为接收列表
     */
    @RequiresPermissions("portal:message:manage")
    @RequestMapping(value = "message/show_message_info",method = RequestMethod.GET)
    public String showMessageInfo(Model model) throws Exception {
        int id = MVCUtil.getIntParam("id");
        
        MessageInfo messageInfo = messageInfoService.getMessageInfo(id);
        
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
        
        if(messageInfo.getIsReaded()==0&&(portalUser.getAccount().equals(messageInfo.getReceiverAccount()))){
            messageInfoService.updateMessageInfo(messageInfo.getId());
        }
        
        model.addAttribute("messageInfo", messageInfo);
        model.addAttribute("ownAccount", portalUser.getAccount());
        return "message_content";
    }
    
    /**
     * 获取信件详情，暂时【废弃】
     * type区分发送列表或接收列表，0为发送列表，1为接收列表
     */
    @RequiresPermissions("portal:message:manage")
    @RequestMapping(value = "message/show_related_message",method = RequestMethod.GET)
    public String showRelatedMessageInfo(Model model) throws Exception {
        int id = MVCUtil.getIntParam("id");
        int transId = MVCUtil.getIntParam("transId");
        
        MessageInfo messageInfo = messageInfoService.getMessageInfo(id);
        List<MessageInfo> relatedMessageList = messageInfoService.queryRelatedMessageList(id, transId);
        
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
        
        model.addAttribute("relatedMessageList", relatedMessageList);
        model.addAttribute("messageInfo", messageInfo);
        model.addAttribute("ownAccount", portalUser.getAccount());
        model.addAttribute("originalId",id);
        return "MessageInfo";
    }
}
