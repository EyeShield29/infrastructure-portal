package com.infrastructure.portal.mapper.config;

import java.util.List;
import com.infrastructure.portal.entity.po.config.UserTitleConfig;


public interface UserTitleConfigMapper {

    int deleteUserTitleConfigById(Integer id);

    int insert(UserTitleConfig record);

    int addUserTitleConfig(UserTitleConfig record);
    
    List<UserTitleConfig> selectByPage(UserTitleConfig userTitleConfig);

    UserTitleConfig selectByPrimaryKey(Integer id);

    int modifyUserTitleConfig(UserTitleConfig record);

    int updateByPrimaryKey(UserTitleConfig record);
}