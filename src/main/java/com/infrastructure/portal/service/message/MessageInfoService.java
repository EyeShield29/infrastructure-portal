package com.infrastructure.portal.service.message;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.message.MessageInfo;
import com.infrastructure.portal.mapper.message.MessageInfoMapper;

@Service
public class MessageInfoService {
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    /**
     * 根据account,user_name,role来分页查询用户
     */
    public PageInfo<MessageInfo> queryMessageInfoListByPage(int currentPage, int pageSize, MessageInfo messageInfo) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<MessageInfo> messageInfoList = messageInfoMapper.selectByPage(messageInfo);
        PageInfo<MessageInfo> pageInfo = new PageInfo<MessageInfo>(messageInfoList);
        //List<Message_Info> messageInfoList = messageInfoDao.queryMessageInfoByPage(pageBean, querySqlBuffer.toString(), queryParams);

        return pageInfo;
    }
    
    public List<MessageInfo> queryAllUnreadMessageInfo(MessageInfo messageInfo) throws Exception {
        List<MessageInfo> messageInfoList = messageInfoMapper.queryAllUnreadMessageInfo(messageInfo);
        return messageInfoList;
    }

    /**
     * 添加信件信息
     */
    public int addMessageInfo(MessageInfo messageInfo)
            throws Exception {
        return messageInfoMapper.insertSelective(messageInfo);
    }

    /**
     * 删除信件
     */
    public void deleteMessageInfo(int id, int type) throws Exception {
        messageInfoMapper.deleteMessageInfoById(id, type);
    }
    
    /**
     * 获取信件内容
     */
    public MessageInfo getMessageInfo(int id) throws Exception {
        return messageInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更改信件信息
     * @throws Exception 
     */
    public void updateMessageInfo(int oldId, int newId) throws Exception {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setId(oldId);
        messageInfo.setReplyMessageId(newId);
        messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
    }
    
    /**
     * 更改信件信息状态为已读
     * @throws Exception 
     */
    public void updateMessageInfo(int messageId) throws Exception {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setId(messageId);
        messageInfo.setIsReaded(1);
        messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
    }

    /**
     * <pre>
     * 获取会话相关信件列表
     * </pre>
     */
    public List<MessageInfo> queryRelatedMessageList(int id, int transId) throws Exception{
        
        List<MessageInfo> messageInfoList = messageInfoMapper.queryRelatedMessageList(transId);
        return messageInfoList;
    }
}
