package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 新闻图片和滚动图片
 * 
 * @author Administrator
 * 
 * @date：2015年8月30日
 */
public class Image implements Serializable
{
    private static final long serialVersionUID = 7969284775604022068L;
    
    private int imageId;
    private int type;               // 1-新闻图片， 2-滚动图片
    private String imageUrl;
    private int notificationId;
    private String notificationTitle;
    
    public int getImageId()
    {
        return imageId;
    }
    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
    public int getNotificationId()
    {
        return notificationId;
    }
    public void setNotificationId(int notificationId)
    {
        this.notificationId = notificationId;
    }
    public String getNotificationTitle()
    {
        return notificationTitle;
    }
    public void setNotificationTitle(String notificationTitle)
    {
        this.notificationTitle = notificationTitle;
    }
    
}
