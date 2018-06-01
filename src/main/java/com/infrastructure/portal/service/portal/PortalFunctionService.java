package com.infrastructure.portal.service.portal;


import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.portal.PortalFunction;
import com.infrastructure.portal.mapper.portal.PortalFunctionMapper;

@Service
public class PortalFunctionService {

    @Autowired
    private PortalFunctionMapper portalFunctionMapper;
    /**
     * 根据function_name,permission来分页查询用户
     */
    public PageInfo<PortalFunction> queryPortalFunctionListByPage(int currentPage, int pageSize,
    		PortalFunction portalFunction) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<PortalFunction> portalFunctionList = portalFunctionMapper.selectByPage(portalFunction);
        PageInfo<PortalFunction> pageInfo = new PageInfo<PortalFunction>(portalFunctionList);
        return pageInfo;
    }

    /**
     * 添加功能
     */
    public void addPortalFunction(PortalFunction portalFunction)
            throws Exception {
        
        portalFunction.setCreateTime(new Date());
        portalFunctionMapper.insertSelective(portalFunction);
    }

    /**
     * 删除功能信息
     */
    public void deletePortalFunction(int id) throws Exception {
        portalFunctionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更改功能信息
     * @throws Exception 
     */
    public void modifyPortalFunction(PortalFunction portalFunction) throws Exception {
        portalFunctionMapper.updateByPrimaryKey(portalFunction);
    }

    /**
     * <pre>
     * 返回全部功能列表
     * </pre>
     */
    public List<PortalFunction> getFunctionList() throws Exception {
        // TODO Auto-generated method stub
        return portalFunctionMapper.getFunctionList();
    }
    
}
