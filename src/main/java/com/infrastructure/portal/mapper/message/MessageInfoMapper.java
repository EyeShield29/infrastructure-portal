package com.infrastructure.portal.mapper.message;


import java.util.List;
import com.infrastructure.portal.entity.po.message.MessageInfo;


public interface MessageInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    MessageInfo selectByPrimaryKey(Integer id);
    
    List<MessageInfo> selectByPage(MessageInfo messageInfo);
    
    List<MessageInfo> queryAllUnreadMessageInfo(MessageInfo messageInfo);
    
    List<MessageInfo> queryRelatedMessageList(Integer transId);

    int updateByPrimaryKeySelective(MessageInfo record);

    int updateByPrimaryKeyWithBLOBs(MessageInfo record);

    int updateByPrimaryKey(MessageInfo record);
    
    int deleteMessageInfoById(Integer id, Integer type);
}