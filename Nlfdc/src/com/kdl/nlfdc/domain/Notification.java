package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Utils;

public class Notification implements Serializable
{
    private static final long serialVersionUID = -1367884943706910323L;
    
    private int notificationId;
    private int firstMenuId;
    private int secondMenuId;
    private int creatorId;
    private int createTime;
    private String title;
    private String content;
    private int isDeleted;
    
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
    public int getIsDeleted()
    {
        return isDeleted;
    }
    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }
    public int getFirstMenuId()
    {
        return firstMenuId;
    }
    public void setFirstMenuId(int firstMenuId)
    {
        this.firstMenuId = firstMenuId;
    }
    public int getSecondMenuId()
    {
        return secondMenuId;
    }
    public void setSecondMenuId(int secondMenuId)
    {
        this.secondMenuId = secondMenuId;
    }
    
}
