package com.kdl.nlfdc.action.component;

import java.util.ArrayList;
import java.util.List;

import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.domain.AdminMenu;
import com.kdl.nlfdc.domain.FirstMenu;
import com.kdl.nlfdc.domain.FirstMenuWithSecondMenu;
import com.kdl.nlfdc.domain.SecondMenu;
import com.kdl.nlfdc.service.CmService;

public class MenuSelector
{
    protected static final int MENU_ITEM_ALL = 0;
    
    private List<FirstMenuWithSecondMenu>     firstMenuList;
    private List<SecondMenu>                  secondMenuList; 
    
    private FirstMenuWithSecondMenu           currentFirstMenu;
    private SecondMenu                        currentSecondMenu;
    
    private FirstMenuWithSecondMenu           firstMenuAll;
    private SecondMenu                        secondMenuAll;
    
    public List<FirstMenuWithSecondMenu> getFirstMenuList()
    {
        return firstMenuList;
    }
    public List<SecondMenu> getSecondMenuList()
    {
        return secondMenuList;
    }
    public FirstMenuWithSecondMenu getCurrentFirstMenu()
    {
        return currentFirstMenu;
    }
    public SecondMenu getCurrentSecondMenu()
    {
        return currentSecondMenu;
    }
    
    
    public MenuSelector(CmService cmService, Admin admin) 
    {
        if (admin == null)
        {
            // 普通用户访问网站不需要登录
            firstMenuList = cmService.getAllFirstMenuWithSecondMenu();
        }
        else
        {
            firstMenuList = new ArrayList<FirstMenuWithSecondMenu>();
            List<AdminMenu> adminMenuList = cmService.getAdminMenus(admin.getAdminId());
            
            for(AdminMenu adminMenu: adminMenuList)
            {
                FirstMenu firstMenu = cmService.getFirstMenu(adminMenu.getFirstMenuId());
                
                FirstMenuWithSecondMenu fmws = new FirstMenuWithSecondMenu();
                fmws.setFirstMenuId(firstMenu.getFirstMenuId());
                fmws.setFirstMenuName(firstMenu.getFirstMenuName());
                
                firstMenuList.add(fmws);
            }
        }
        
        setSecondMenuList(firstMenuList, cmService);
        
        currentFirstMenu = firstMenuAll;
        currentSecondMenu = secondMenuAll;
    }
    
    
    public void selectFirstMenu(int firstMenuId)
    {
        if (currentFirstMenu.getFirstMenuId() == firstMenuId)
        {
            return;
        }
        else
        {
            for (FirstMenuWithSecondMenu fmws : firstMenuList)
            {
                if (fmws.getFirstMenuId() == firstMenuId)
                {
                    currentFirstMenu = fmws;
                    setSelect();
                    return;
                }
            }
        }
    }
    
    public void selectSecondMenu(int secondMenuId)
    {
        List<SecondMenu> secondMenuList = currentFirstMenu.getSecondMenuList(); 
        if (secondMenuList == null)
        {
            return;
        }
        else
        {
            for(SecondMenu sm : secondMenuList)
            {
                if (sm.getSecondMenuId() == secondMenuId)
                {
                    currentSecondMenu = sm;
                    setSelect();
                    return;
                }
            }
        }
    }
    
    // private 
    // ---------------------------------------------
    private void setSecondMenuList(List<FirstMenuWithSecondMenu> firstMenuList, CmService cmService)
    {
        for (FirstMenuWithSecondMenu fmws : firstMenuList)
        {
            List<SecondMenu> secondMenuList = cmService.getSecondMenuByFirstMenuId(fmws.getFirstMenuId());
            fmws.setSecondMenuList(secondMenuList);
        }
    }
    
    private void setSelect()
    {
        for (FirstMenuWithSecondMenu fmws : firstMenuList)
        {
            fmws.setIsSelected(false);
            for (SecondMenu sm : fmws.getSecondMenuList())
            {
                sm.setIsSelected(false);
            }
        }
        
        currentFirstMenu.setIsSelected(true);
        currentSecondMenu.setIsSelected(true);
    }
    
    
    {
        firstMenuAll = new FirstMenuWithSecondMenu();
        firstMenuAll.setFirstMenuId(MENU_ITEM_ALL);
        
        secondMenuAll = new SecondMenu();
        secondMenuAll.setSecondMenuId(MENU_ITEM_ALL);
    }
}
