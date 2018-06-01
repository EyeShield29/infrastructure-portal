package com.infrastructure.portal.web.controller;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonObject;
import com.infrastructure.portal.entity.po.message.MessageInfo;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.vo.ProcessProgressVo;
import com.infrastructure.portal.entity.vo.ProjectCountVo;
import com.infrastructure.portal.service.document.DocumentInfoService;
import com.infrastructure.portal.service.message.MessageInfoService;
import com.infrastructure.portal.service.project.ProjectService;
import com.infrastructure.portal.service.user.PortalUserService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class IndexController {
    //private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessageInfoService messageService;
    @Autowired
    private PortalUserService userService;
    @Autowired
    private DocumentInfoService documentService;
    
    @RequestMapping(value="/home")
    public String showOrderList(Model model) throws Exception
    {
        MessageInfo messageInfo = new MessageInfo();
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
                .getPrincipal();
        messageInfo.setReceiverAccount(portalUser.getAccount());
        messageInfo.setIsReaded(0);
        List<MessageInfo> messageList = messageService.queryAllUnreadMessageInfo(messageInfo);
        
        List<ProcessProgressVo> processList = projectService.queryLatesProcess();
        
        int projectTotalCount = projectService.projectTotalCount();
        int userTotalCount = userService.userTotalCount();
        int projectFinishCount = projectService.projectFinishCount();
        int projectFinishPercent = 0;
        if(projectTotalCount!=0) 
            projectFinishPercent = projectFinishCount*100/projectTotalCount;
        int documentDownloadCount = documentService.downloadCount();
        
        //List<ProcessInfoVo> processVoList = null;
        model.addAttribute("projectTotalCount", projectTotalCount);
        model.addAttribute("userTotalCount", userTotalCount);
        model.addAttribute("projectFinishPercent", projectFinishPercent);
        model.addAttribute("documentDownloadCount", documentDownloadCount);
        model.addAttribute("messageList", messageList);
        model.addAttribute("processList", processList);
        return "home";
    }
    
    @RequestMapping(value = "/project_count",method = RequestMethod.GET)
    @ResponseBody
    public void showUnreadMessageList(Model model) throws Exception {
        AjaxData ajaxdata = null;
        JsonObject data = new JsonObject();
        
        List<ProjectCountVo> countList = projectService.periodCount();
        int totalCount = 0;
        for(ProjectCountVo count:countList){
            totalCount+=count.getCount();
            data.addProperty("countOf"+count.getStatus(), count.getCount());
        }
        data.addProperty("totalCount", totalCount);
        //data.addProperty("fileuri", fileuri);
        ajaxdata = new AjaxData(true, data, null);
        MVCUtil.ajaxJson(ajaxdata);
    }
    
}
