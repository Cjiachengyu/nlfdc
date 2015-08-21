package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class UserNotification implements Serializable
{
    private static final long serialVersionUID = -5449781518013213940L;
    
    private int userId;
    private int notificationId;
    private int isReaded;

    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public int getNotificationId()
    {
        return notificationId;
    }
    public void setNotificationId(int notificationId)
    {
        this.notificationId = notificationId;
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
