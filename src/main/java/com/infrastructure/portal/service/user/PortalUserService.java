package com.infrastructure.portal.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.po.portal.PortalUserRole;
import com.infrastructure.portal.entity.vo.ListUserVo;
import com.infrastructure.portal.entity.vo.QueryUserVo;
import com.infrastructure.portal.mapper.portal.PortalRoleMapper;
import com.infrastructure.portal.mapper.user.PortalUserMapper;

@Service
public class PortalUserService {
    @Autowired
    private PortalUserMapper portalUserMapper;
    @Autowired
    private PortalRoleMapper portalRoleMapper;
    /**
     * 根据account,user_name,role来分页查询用户
     */
    public PageInfo<PortalUser> queryPortalUserListByPage(Integer currentPage, Integer pageSize, 
    		QueryUserVo queryUserVo) throws Exception {
    	PageHelper.startPage(currentPage,pageSize);
    	List<PortalUser> portalUserList = portalUserMapper.selectByPage(queryUserVo);
    	PageInfo<PortalUser> pageInfo = new PageInfo<PortalUser>(portalUserList);
        return pageInfo;
    }
    
    /**
     * 查询所有用户列表
     */
    public List<ListUserVo> queryUserVoList() throws Exception {
        
        List<ListUserVo> portalUserList = portalUserMapper.queryUserVoListAll();

        return portalUserList;
    }
    
    
    /**
     * 查询所有用户角色列表
     */
    public List<PortalRole> getRoleList() throws Exception {
        
        List<PortalRole> portalRoleList = portalRoleMapper.queryRoleListAll();

        return portalRoleList;
    }
    
    /**
     * 查询所有用户角色名列表
     * @return
     * @throws Exception
     */
    public List<PortalRole> getRoleListName() throws Exception {
    	List<PortalRole> portalRoleNameList = portalRoleMapper.queryPortalRoleName();
    	return portalRoleNameList;
    }

    /**
     * 获取用于页面显示的QueryUserVo列表
     */
    public List<QueryUserVo> getQueryUserVoList(
            List<PortalUser> PortalUserList) throws Exception {
        List<QueryUserVo> queryUserVoList = new ArrayList<QueryUserVo>();
        for (PortalUser portalUser : PortalUserList) {
            List<PortalRole> roleList = portalUserMapper.listPortalUserRoles(portalUser.getId());
            StringBuffer roleStr = new StringBuffer();
            StringBuffer roleIdStr = new StringBuffer();
            for (int i = 0; i < roleList.size(); i++) {
                PortalRole portalRole = roleList.get(i);
                if (i == roleList.size() - 1){
                    roleStr.append(portalRole.getRoleName());
                    roleIdStr.append(portalRole.getId());
                }
                else{
                    roleStr.append(portalRole.getRoleName() + ",");
                    roleIdStr.append(portalRole.getId() + ",");
                }
            }
            QueryUserVo quv = new QueryUserVo();
            quv.setId(portalUser.getId());
            quv.setAccount(portalUser.getAccount());
            quv.setUserName(portalUser.getUserName());
            quv.setRole(roleStr.toString());
            if(!(roleIdStr.toString().equals(""))) {
            	quv.setRoleId(roleIdStr.toString());
            }           
            if(null!=portalUser.getSex())
                quv.setSex(portalUser.getSex());
            if(null!=portalUser.getPhoneNum())
                quv.setPhoneNum(portalUser.getPhoneNum());
            if(null!=portalUser.getEmail())
                quv.setEmail(portalUser.getEmail());
            queryUserVoList.add(quv);
        }
        return queryUserVoList;
    }

    /**
     * 添加新用户
     */
    public void addPortalUser(PortalUser PortalUser, String role)
            throws Exception {
        String passwd = PortalUser.getPasswd();
        passwd = DigestUtils.md5Hex(passwd);
        PortalUser.setPasswd(passwd);
        portalUserMapper.addPortalUser(PortalUser);
        
        PortalUserRole pur = new PortalUserRole();
        pur.setRoleId(Integer.parseInt(role));
        pur.setUserId(PortalUser.getId());
        pur.setCreateTime(new Date());        

        portalUserMapper.addPortalUserRole(pur);
    }

    /**
     * 删除用户
     */
    public void deletePortalUser(int id) throws Exception {
        portalUserMapper.deleteUserById(id);
    }

    /**
     * 更改用户基本信息
     * @throws Exception 
     */
    public void modifyPortalUser(PortalUser portalUser, int roleId) throws Exception {
          portalUserMapper.modifyPortalUser(portalUser);
          if(roleId>0){
              PortalUserRole userRole = new PortalUserRole();
              userRole.setUserId(portalUser.getId());
              userRole.setRoleId(roleId);
              portalUserMapper.modifyPortalUserRole(userRole);
          }
    }
    
    /**
     * 统计所有用户数量
     */
    public int userTotalCount() {
    	return portalUserMapper.userTotalCount();
    }
    /**
     * <pre>
     * TODO
     * </pre>
     */
    public QueryUserVo queryUserVoInfo(int id) throws Exception {
        // TODO Auto-generated method stub
        PortalUser user = portalUserMapper.queryUserInfo(id);
        List<PortalRole> roleList = portalUserMapper.listPortalUserRoles(id);
        StringBuffer roleStr = new StringBuffer();
        StringBuffer roleIdStr = new StringBuffer();
        for (int i = 0; i < roleList.size(); i++) {
            PortalRole portalRole = roleList.get(i);
            if (i == roleList.size() - 1){
                roleStr.append(portalRole.getRoleName());
                roleIdStr.append(portalRole.getId());
            }
            else{
                roleStr.append(portalRole.getRoleName() + ",");
                roleIdStr.append(portalRole.getId() + ",");
            }
        }
        QueryUserVo quv = new QueryUserVo();
        quv.setId(user.getId());
        quv.setAccount(user.getAccount());
        quv.setUserName(user.getUserName());
        quv.setRole(roleStr.toString());
        quv.setRoleId(roleIdStr.toString());
        if(null!=user.getSex())
            quv.setSex(user.getSex());
        if(null!=user.getPhoneNum())
            quv.setPhoneNum(user.getPhoneNum());
        if(null!=user.getEmail())
            quv.setEmail(user.getEmail());
        
        return quv;
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public boolean checkOldPasswd(int userid, String oldPasswd) throws Exception {
        // TODO Auto-generated method stub
        String passwd = DigestUtils.md5Hex(oldPasswd);
        
        PortalUser user = new PortalUser();
        user.setId(userid);
        user.setPasswd(passwd);
        
        PortalUser result = portalUserMapper.getUserByNameAndPd(userid,passwd);
        if(null!=result){
            if(result.getId()>0)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public void modifyUserPasswd(int userid, String newPasswd) throws Exception {
        // TODO Auto-generated method stub
        String passwd = DigestUtils.md5Hex(newPasswd);
        
        PortalUser user = new PortalUser();
        user.setId(userid);
        user.setPasswd(passwd);
        portalUserMapper.modifyPortalUser(user);
    }
    
}
