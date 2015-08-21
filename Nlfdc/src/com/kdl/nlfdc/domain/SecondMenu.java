package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 子模块
 * 
 * @author cjia
 *
 * @version 创建时间：2015年8月19日
 */
public class SecondMenu implements Serializable
{
    private static final long serialVersionUID = 5031024234730184776L;
    
    private int secondMenuId;
    private String secondMenuName;
    private int firstMenuId;
    
    
    public int getSecondMenuId()
    {
        return secondMenuId;
    }
    public void setSecondMenuId(int secondMenuId)
    {
        this.secondMenuId = secondMenuId;
    }
    public String getSecondMenuName()
    {
        return secondMenuName;
    }
    public void setSecondMenuName(String secondMenuName)
    {
        this.secondMenuName = secondMenuName;
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
