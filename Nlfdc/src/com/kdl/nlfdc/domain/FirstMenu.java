package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 主模块
 * 
 * @author cjia
 *
 * @version 创建时间：2015年8月19日
 */
public class FirstMenu implements Serializable
{
    private static final long serialVersionUID = 1031222107943976056L;
    
    private int firstMenuId;
    private String firstMenuName;
   
    
    public int getFirstMenuId()
    {
        return firstMenuId;
    }
    public void setFirstMenuId(int firstMenuId)
    {
        this.firstMenuId = firstMenuId;
    }
    public String getFirstMenuName()
    {
        return firstMenuName;
    }
    public void setFirstMenuName(String firstMenuName)
    {
        this.firstMenuName = firstMenuName;
    }
    
}
