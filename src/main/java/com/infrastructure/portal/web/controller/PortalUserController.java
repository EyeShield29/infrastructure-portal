package com.infrastructure.portal.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.vo.QueryUserVo;
import com.infrastructure.portal.service.user.PortalUserService;
import com.infrastructure.portal.web.common.AjaxData;
import com.infrastructure.portal.web.common.MVCUtil;

@Controller
public class PortalUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalUserController.class);
    private static final String USER_SEARCH = "USER_SEARCH";
    private static final int PAGESIZE = 15;
    
	@Autowired
	private PortalUserService portalUserService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
//		PaymentUser paymentUser = (PaymentUser) SecurityUtils.getSubject()
//				.getPrincipal();
//		if (paymentUser != null) {
//			return "redirect:/index";
//		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String check(Model model) throws Exception {
		String uname = MVCUtil.getParam("uname");
		String passwd = MVCUtil.getParam("passwd");	
		if (StringUtils.isBlank(uname) || StringUtils.isBlank(passwd)) {
			model.addAttribute("msg", "用户名或密码不能为空!");
			return "login";
		}
		passwd = DigestUtils.md5Hex(passwd);
		//得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		UsernamePasswordToken token = new UsernamePasswordToken(uname, passwd);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			PortalUser user = (PortalUser)subject.getPrincipal();
			MVCUtil.setSessionAttribute("userid", user.getId());
			MVCUtil.setSessionAttribute("userName", user.getUserName());
			MVCUtil.setSessionAttribute("account", user.getAccount());
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名或密码错误");
			return "login";
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "用户名或密码错误");
			return "login";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}

	@RequestMapping(value = "/index")
	public String index(Model model) {
		PortalUser portalUser = (PortalUser) SecurityUtils.getSubject()
				.getPrincipal();
		
		model.addAttribute("loginInfo", portalUser);
		return "index";
	}

	/*@RequestMapping(value = "/home")
	public String home(Model model) {
		return "home";
	}*/
	
	/**
	 * 获取用户列表
	 */
	@RequiresPermissions("portal:user:list")
	@RequestMapping(value = "portal_user/show_user_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws Exception {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryUserVo queryUserVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(USER_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(USER_SEARCH);
            if (obj != null) {
                queryUserVo = (QueryUserVo) obj;
            }
        }
        //查询时没有添加roleId条件，设置为空
        if(queryUserVo !=null && queryUserVo.getRoleId().equals("")) {
        	queryUserVo.setRoleId(null);
        }
        PageInfo<PortalUser> pageInfo = portalUserService.queryPortalUserListByPage(pageid, PAGESIZE, queryUserVo);
        List<PortalUser> portaltUserList = pageInfo.getList();
        List<QueryUserVo> userVoList=portalUserService.getQueryUserVoList(portaltUserList);
        List<PortalRole> roleList = portalUserService.getRoleList();
        List<PortalRole> portalRoleNameList = portalUserService.getRoleListName();
        model.addAttribute("portalRoleNameList", portalRoleNameList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("userVoList", userVoList);
        model.addAttribute("queryUserVo", queryUserVo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageuri", "/portal_user/show_user_list?");
        //return "portal_user/show_user_list";
        return "user_list";
    }
	
	/**
	 * 查询用户
	 */
	@RequiresPermissions("portal:user:list")
	@RequestMapping(value = "portal_user/search", method = RequestMethod.POST)
    public String searchUser(QueryUserVo queryUserVo,Model model) throws Exception {
	    int pageid = 0;
        if (queryUserVo != null) {
            MVCUtil.setSessionAttribute(USER_SEARCH, queryUserVo);
            pageid = 1;
        }
        return "redirect:/portal_user/show_user_list?pageid=" + pageid;
    }
	
	/**
	 * <pre>
	 * TODO 删除用户
	 * </pre>
	 */
	@RequiresPermissions("portal:user:list")
	@RequestMapping(value = "portal_user/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws Exception {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           portalUserService.deletePortalUser(id);
       return "redirect:/portal_user/show_user_list";
    }
	
	/**
     * 跳转到添加新用户页面
     */
	@RequiresPermissions("portal:user:add")
    @RequestMapping(value = "portal_user/add_user", method = RequestMethod.GET)
    public String goAddPaymentUser(Model model) throws Exception {
        
        List<PortalRole> roleList = portalUserService.getRoleList();
        model.addAttribute("roleList", roleList);
        return "add_user";
    }
	
	/**
	 * 添加新用户
	 */
	@RequiresPermissions("portal:user:add")
    @RequestMapping(value = "portal_user/addPortalUser", method = RequestMethod.POST)
	@ResponseBody
    public void addPortalUser(PortalUser portalUser,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
	   String rePasswd=MVCUtil.getParam("rePasswd");
	   String role=MVCUtil.getParam("role");
      
       if (StringUtils.isBlank(portalUser.getAccount())) {
           msg = "账号不能为空！";
       } else if (StringUtils.isBlank(portalUser.getUserName())) {
           msg = "用户姓名不能为空！";
       } else if(StringUtils.isBlank(role)){
           msg = "请选择用户的角色！";
       } else if (StringUtils.isBlank(portalUser.getPasswd())) {
           msg = "密码不能为空！";       
       } else if (StringUtils.isBlank(rePasswd)) {
           msg = "新密码没有重复输入！";
       } else if (!portalUser.getPasswd().equals(rePasswd)) {
           msg = "两次输入的密码不相同！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalUser.setCreateTime(new Date());
           portalUserService.addPortalUser(portalUser,role);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "该帐号已经存在！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
	
	/**
     * 修改用户基本信息，列表页面通过管理员修改
     */
    @RequiresPermissions("portal:user:list")
    @RequestMapping(value = "portal_user/modifyPortalUser", method = RequestMethod.POST)
    @ResponseBody
    public void modifyPortalUser(PortalUser portalUser,Model model){
       AjaxData ajaxdata;
       String msg = null;
       int roleId = MVCUtil.getIntParam("roleId");
      
       if (StringUtils.isBlank(portalUser.getAccount())) {
           msg = "账号不能为空！";
       } else if (StringUtils.isBlank(portalUser.getUserName())) {
           msg = "用户姓名不能为空！";
       } else if (roleId<=0) {
           msg = "请选择用户角色！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalUserService.modifyPortalUser(portalUser, roleId);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * 修改用户基本信息，用户自行修改
     */
    @RequestMapping(value = "portal_user/modifyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public void modifyUserInfo(PortalUser portalUser,Model model){
       AjaxData ajaxdata;
       String msg = null;
      
       if (StringUtils.isBlank(portalUser.getAccount())) {
           msg = "账号不能为空！";
       } else if (StringUtils.isBlank(portalUser.getUserName())) {
           msg = "用户姓名不能为空！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalUserService.modifyPortalUser(portalUser, 0);
           ajaxdata = new AjaxData(true, null, null);
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
    
    /**
     * <pre>
     * TODO 跳转到个人信息页面
     * </pre>
     */
    @RequestMapping(value = "portal_user/user_info", method = RequestMethod.GET)
    public String queryUserInfo(Model model) throws Exception {
        //获取当前用户
        PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
        
        QueryUserVo portalUserVo = portalUserService.queryUserVoInfo(portalUser.getId());
        
        model.addAttribute("portalUserVo", portalUserVo);
        return "user_info";
    }
    
    /**
     * 跳转到修改密码页面
     */
    @RequestMapping(value = "portal_user/modify_passwd", method = RequestMethod.GET)
    public String goModifyPasswd(Model model) throws Exception {
        
        return "reset_passwd";
    }
    
    /**
     * 修改用户密码
     */
    @RequestMapping(value = "portal_user/modify_passwd", method = RequestMethod.POST)
    @ResponseBody
    public void modifyUserPasswd(Model model){
       AjaxData ajaxdata;
       String msg = null;
       String oldPasswd = MVCUtil.getParam("oldPasswd");
       String newPasswd = MVCUtil.getParam("newPasswd");
       String rePasswd = MVCUtil.getParam("rePasswd");
      
       if (StringUtils.isBlank(oldPasswd)) {
           msg = "请输入原始密码！";
       } else if (StringUtils.isBlank(newPasswd)) {
           msg = "请输入新密码！";
       } else if (StringUtils.isBlank(rePasswd)) {
           msg = "请输入确认的新密码！";
       } else if (!newPasswd.equals(rePasswd)) {
           msg = "请输入确认的新密码！";
       } 

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       PortalUser portalUser = (PortalUser) SecurityUtils.getSubject().getPrincipal();
       int userid = portalUser.getId();
       try {
           if(portalUserService.checkOldPasswd(userid,oldPasswd)){
               portalUserService.modifyUserPasswd(userid, newPasswd);
               ajaxdata = new AjaxData(true, null, null);
           }else{
               ajaxdata = new AjaxData(false, null, "原密码错误！");
           }
       } catch (Exception e){
           ajaxdata = new AjaxData(false, null, "修改失败！");
           LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }
}
