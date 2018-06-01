package com.infrastructure.portal.service.portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.infrastructure.portal.entity.po.portal.PortalFunction;
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.po.portal.PortalRoleFunction;
import com.infrastructure.portal.entity.vo.QueryPortalRoleVo;
import com.infrastructure.portal.mapper.portal.PortalFunctionMapper;
import com.infrastructure.portal.mapper.portal.PortalRoleFunctionMapper;
import com.infrastructure.portal.mapper.portal.PortalRoleMapper;

@Service
public class PortalRoleService {

    @Autowired 
    private PortalRoleMapper portalRoleMapper;
    @Autowired
    private PortalFunctionMapper portalFunctionMapper;
    @Autowired
    private PortalRoleFunctionMapper portalRoleFunctionMapper;

    /**
     * 根据role_name,role_code来分页查询用户
     */
    public PageInfo<PortalRole> queryPortalRoleListByPage(Integer currentPage, Integer pageSize,
    		PortalRole portalRole) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<PortalRole> portalRoleList = portalRoleMapper.selectByPage(portalRole);
        PageInfo<PortalRole>pageInfo = new PageInfo<PortalRole>(portalRoleList);
        return pageInfo;
    }

    /**
     * 添加角色
     */
    public void addPortalRole(PortalRole portalRole, int[] functions)
            throws Exception {
        int roleId = portalRoleMapper.insertSelective(portalRole);
        for(int id:functions){
        	PortalRoleFunction roleFunction = new PortalRoleFunction();
        	roleFunction.setFunctionId(id);
        	roleFunction.setRoleId(roleId);
        	roleFunction.setCreateTime(new Date());
            portalRoleFunctionMapper.insertSelective(roleFunction);
        }
    }

    /**
     * 删除角色信息
     */
    public void deletePortalRole(int id) throws Exception {
        portalRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更改角色信息
     * @throws Exception 
     */
    public void modifyPortalRole(PortalRole portalRole, int[] functions) throws Exception {
        
        portalRoleMapper.updateByPrimaryKey(portalRole);
        
        portalRoleFunctionMapper.deleteByPrimaryKey(portalRole.getId());
        
        for(int id:functions){
            PortalRoleFunction roleFunction = new PortalRoleFunction();
            roleFunction.setFunctionId(id);
            roleFunction.setRoleId(portalRole.getId());
            roleFunction.setCreateTime(new Date());
            portalRoleFunctionMapper.insertSelective(roleFunction);
        }
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public List<QueryPortalRoleVo> getPortalRoleVoList(List<PortalRole> portalRoleList) throws Exception {
        // TODO Auto-generated method stub
        List<QueryPortalRoleVo> queryRoleVoList = new ArrayList<QueryPortalRoleVo>();
        for (PortalRole portalRole : portalRoleList) {
            List<PortalFunction> functionList = portalFunctionMapper.listPortalRoleFunctions(portalRole.getId());
            StringBuffer functionNameStr = new StringBuffer();
            StringBuffer functionIdStr = new StringBuffer();
            for (int i = 0; i < functionList.size(); i++) {
                PortalFunction portalFunction = functionList.get(i);
                if (i == functionList.size() - 1){
                    functionNameStr.append(portalFunction.getFunctionName());
                    functionIdStr.append(portalFunction.getId());
                }
                else{
                    functionNameStr.append(portalFunction.getFunctionName() + ",");
                    functionIdStr.append(portalFunction.getId() + ",");
                }
            }
            QueryPortalRoleVo qrv = new QueryPortalRoleVo();
            qrv.setId(portalRole.getId());
            qrv.setRoleName(portalRole.getRoleName());
            qrv.setRoleCode(portalRole.getRoleCode());
            qrv.setUpdateTime(portalRole.getUpdateTime());
            qrv.setFunctionId(functionIdStr.toString());
            qrv.setFunctionName(functionNameStr.toString());
            queryRoleVoList.add(qrv);
       } 
       return queryRoleVoList;
    }
   
    /**
     * 查询所有用户角色名列表
     * @return
     * @throws Exception
     */
    public List<PortalRole> getRoleListName() throws Exception {
    	List<PortalRole> portalRoleNameList = portalRoleMapper.getRoleListName();
    	return portalRoleNameList;
    }
}
