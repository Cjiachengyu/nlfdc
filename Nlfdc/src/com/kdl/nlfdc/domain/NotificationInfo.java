package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 记录Notification的通知范围，已读人数，总接收人数等信息
 * 
 * @author cjia
 *
 * @version 创建时间：2015年8月5日
 */
public class NotificationInfo implements Serializable
{
    private static final long serialVersionUID = -5865282756459337277L;
    
    private String notifyTargetString;
    private int    readedCount;
    private int    receiveCount;
    private String creatorName;
    
    
    public String getNotifyTargetString()
    {
        return notifyTargetString;
    }
    public void setNotifyTargetString(String notifyTargetString)
    {
        this.notifyTargetString = notifyTargetString;
    }
    public int getReadedCount()
    {
        return readedCount;
    }
    public void setReadedCount(int readedCount)
    {
        this.readedCount = readedCount;
    }
    public int getReceiveCount()
    {
        return receiveCount;
    }
    public void setReceiveCount(int receiveCount)
    {
        this.receiveCount = receiveCount;
    }
    public String getCreatorName()
    {
        return creatorName;
    }
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
}
