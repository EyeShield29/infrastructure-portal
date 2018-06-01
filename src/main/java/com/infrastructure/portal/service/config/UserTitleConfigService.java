package com.infrastructure.portal.service.config;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.config.UserTitleConfig;
import com.infrastructure.portal.mapper.config.UserTitleConfigMapper;

@Service
public class UserTitleConfigService {
    @Autowired
    private UserTitleConfigMapper userTitleConfigMapper;
    
    /**
     * 根据title_name来分页查询用户
     */
    public PageInfo<UserTitleConfig> queryUserTitleConfigListByPage(Integer currentPage, Integer pageSize,
    		UserTitleConfig userTitleConfig) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<UserTitleConfig> userTitleConfigList = userTitleConfigMapper.selectByPage(userTitleConfig);
        PageInfo<UserTitleConfig> pageInfo = new PageInfo<UserTitleConfig>(userTitleConfigList);
        return pageInfo;
    }

    /**
     * 添加流程
     */
    public void addUserTitleConfig(UserTitleConfig userTitleConfig)
            throws Exception {
        
        userTitleConfig.setCreateTime(new Date());
        userTitleConfigMapper.addUserTitleConfig(userTitleConfig);
    }

    /**
     * 删除用户职称配置
     */
    public void deleteUserTitleConfig(int id) throws Exception {
        userTitleConfigMapper.deleteUserTitleConfigById(id);
    }

    /**
     * 更改用户职称配置信息
     * @throws Exception 
     */
    public void modifyUserTitleConfig(UserTitleConfig userTitleConfig) throws Exception {
        userTitleConfigMapper.modifyUserTitleConfig(userTitleConfig);
    }
}
