package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 用于统计访问数
 * 
 * @author cjia
 *
 * @version 创建时间：2015年8月19日
 */
public class VisitCount implements Serializable
{
    private static final long serialVersionUID = 794456891808593441L;
    
    private int day;    // 形式：20150212
    private int count;
    
    public int getDay()
    {
        return day;
    }
    public void setDay(int day)
    {
        this.day = day;
    }
    public int getCount()
    {
        return count;
    }
    public void setCount(int count)
    {
        this.count = count;
    }
   
    public void addOneCount()
    {
        this.count = this.count + 1;
    }
}
