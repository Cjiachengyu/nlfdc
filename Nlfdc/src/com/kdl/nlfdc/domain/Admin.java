package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
public class Admin implements Serializable
{
    private static final long serialVersionUID = -3058095127702117159L;
    
    private int     adminId;
    private String  loginName;
    private String  adminName;
    private int     adminRole;
    private String  password;
    
    private List<AdminMenu> adminMenuList;
    
    public int getAdminId()
    {
        return adminId;
    }
    public void setAdminId(int adminId)
    {
        this.adminId = adminId;
    }
    public String getLoginName()
    {
        return loginName;
    }
    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }
    public String getAdminName()
    {
        return adminName;
    }
    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }
    public int getAdminRole()
    {
        return adminRole;
    }
    public void setAdminRole(int adminRole)
    {
        this.adminRole = adminRole;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public List<AdminMenu> getAdminMenuList()
    {
        return adminMenuList;
    }
    public void setAdminMenuList(List<AdminMenu> adminMenuList)
    {
        this.adminMenuList = adminMenuList;
    }
    
}
