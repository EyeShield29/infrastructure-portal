package com.infrastructure.portal.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.infrastructure.portal.entity.po.portal.PortalFunction;
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.po.portal.PortalUserRole;
import com.infrastructure.portal.entity.vo.ListUserVo;
import com.infrastructure.portal.entity.vo.QueryUserVo;


public interface PortalUserMapper {

    int deleteUserById(Integer id);

    int insert(PortalUser record);

    int addPortalUser(PortalUser record);
    
    int modifyPortalUserRole(PortalUserRole portalUserRole);
    
    int addPortalUserRole(PortalUserRole portalUserRole);
    
    PortalUser queryUserInfo(int id);
    
    PortalUser getUserByNameAndPd(@Param("id")int id,@Param("passwd") String passwd);
    
    PortalUser getUserByAccount(String userName);
    
    List<PortalUser> selectByPage(QueryUserVo queryUserVo);

    List<ListUserVo> queryUserVoListAll();
    
    List<PortalRole> listPortalUserRoles(int id);
    
    List<PortalFunction> listPortalUserFunctions(int id);
    
    PortalUser selectByPrimaryKey(Integer id);

    int modifyPortalUser(PortalUser record);

    int updateByPrimaryKey(PortalUser record);
    
    int userTotalCount();
    
}