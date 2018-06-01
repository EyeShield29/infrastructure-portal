package com.infrastructure.portal.entity.po.message;

import java.util.Date;

public class MessageInfo {
    private Integer id;

    private String transId;

    private String senderAccount;

    private Integer senderVisible;

    private String messageTitle;

    private Date sendTime;

    private Integer replyMessageId;

    private String receiverAccount;

    private Integer receiverVisible;

    private Integer isReaded;

    private Date createTime;

    private Date updateTime;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount == null ? null : senderAccount.trim();
    }

    public Integer getSenderVisible() {
        return senderVisible;
    }

    public void setSenderVisible(Integer senderVisible) {
        this.senderVisible = senderVisible;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle == null ? null : messageTitle.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Integer replyMessageId) {
        this.replyMessageId = replyMessageId;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount == null ? null : receiverAccount.trim();
    }

    public Integer getReceiverVisible() {
        return receiverVisible;
    }

    public void setReceiverVisible(Integer receiverVisible) {
        this.receiverVisible = receiverVisible;
    }

    public Integer getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Integer isReaded) {
        this.isReaded = isReaded;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}