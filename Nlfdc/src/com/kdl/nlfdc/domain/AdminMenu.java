package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 记录管理员具有的操作的权限
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
public class AdminMenu implements Serializable
{
    private static final long serialVersionUID = 894612483824821256L;
    
    private int adminId;
    private int firstMenuId;
    
    public int getAdminId()
    {
        return adminId;
    }
    public void setAdminId(int adminId)
    {
        this.adminId = adminId;
    }
    public int getFirstMenuId()
    {
        return firstMenuId;
    }
    public void setFirstMenuId(int firstMenuId)
    {
        this.firstMenuId = firstMenuId;
    }
    
    
}
