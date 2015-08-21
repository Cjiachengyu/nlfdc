package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Utils;

public class Notification implements Serializable
{
    private static final long serialVersionUID = -1367884943706910323L;
    
    private int notificationId;
    private int creatorId;
    private int createTime;
    private String title;
    private String content;
    private String notifyTarget;            // 表示发送范围，可能是“全园”“全体老师”“全体家长”或者班级id拼接的字符串
    private int isDeleted;
    
    private int isReaded;
   
    public String getCreateTimeString()
    {
        return Utils.getSimpleTimeString(createTime);
    }
   
    public int getNotificationId()
    {
        return notificationId;
    }
    public void setNotificationId(int notificationId)
    {
        this.notificationId = notificationId;
    }
    public int getCreatorId()
    {
        return creatorId;
    }
    public void setCreatorId(int creatorId)
    {
        this.creatorId = creatorId;
    }
    public int getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(int createTime)
    {
        this.createTime = createTime;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public String getNotifyTarget()
    {
        return notifyTarget;
    }
    public void setNotifyTarget(String notifyTarget)
    {
        this.notifyTarget = notifyTarget;
    }
    public int getIsDeleted()
    {
        return isDeleted;
    }
    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public int getIsReaded()
    {
        return isReaded;
    }

    public void setIsReaded(int isReaded)
    {
        this.isReaded = isReaded;
    }
    
}
