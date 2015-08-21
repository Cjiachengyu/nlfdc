package com.kdl.nlfdc.domain;

import java.util.List;

/**
 * 
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
public class FirstMenuWithSecondMenu extends FirstMenu
{
    private static final long serialVersionUID = 6895044000643263576L;

    private boolean isSelected;

    List<SecondMenu> secondMenuList;
    
    
    public List<SecondMenu> getSecondMenuList()
    {
        return secondMenuList;
    }

    public void setSecondMenuList(List<SecondMenu> secondMenuList)
    {
        this.secondMenuList = secondMenuList;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    
}
