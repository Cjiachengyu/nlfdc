package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.domain.FirstMenu;
import com.kdl.nlfdc.domain.Image;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.VisitCount;

@SessionScope
@UrlBinding("/index")
public class Index extends AbstractActionBean
{
    private static final long serialVersionUID = -6965961964257758466L;
    private static final String MAIN_PAGE = "/WEB-INF/jsp/main/main.jsp";
    private static final String NAVIGATION_PAGE = "/WEB-INF/jsp/main/navigation.jsp";

    private static final int[] max_notification_num = {0, 11, 11, 9, 9, 9, 9, 9, 8, 8};
    
    private String remoteAddr = "";
    
    private int totalCount;
    private int thisDayCount;
    private List<Notification>[] firstMenuNotificationList; 
    private List<Image> newsImageList;
    private List<Image> rollImageList;
    
    public int getTotalCount()
    {
        return totalCount;
    }
    public int getThisDayCount()
    {
        return thisDayCount;
    }
    public List<Notification>[] getFirstMenuNotificationList()
    {
        return firstMenuNotificationList;
    }
    public List<Image> getNewsImageList()
    {
        return newsImageList;
    }
    public List<Image> getRollImageList()
    {
        return rollImageList;
    }
    
    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    public Resolution defaultHandler()
    {
        String remoteAddrParam = getCurrentRequest().getRemoteAddr();
        
        if (!remoteAddrParam.equals(remoteAddr))
        {
            // 访问统计，同一次访问中刷新不累加
            remoteAddr = remoteAddrParam;
            
            int time = generateTime();
            
            VisitCount visitCount = cmService.getVisitCount(time);
            
            if (visitCount == null)
            {
                visitCount = new VisitCount();
                visitCount.setDay(time);
                visitCount.setCount(1);
                cmService.insertVisitCount(visitCount);
            }
            else
            {
                visitCount.addOneCount();
                cmService.updateVisitCount(visitCount);
            }
            
            thisDayCount = visitCount.getCount();
            totalCount = cmService.getSumVisitCount();
        }
        
        // 查询主模块
        menuSelector = initCommomMenuSelector();
        firstMenuNotificationList = new ArrayList[menuSelector.getFirstMenuList().size()];
        for(int i = 1; i < menuSelector.getFirstMenuList().size(); i++)
        {
            FirstMenu firstMenu = menuSelector.getFirstMenuList().get(i);
            List<Notification> notificationList = cmService.getCommonNotificationList(firstMenu.getFirstMenuId(), 0, 0,
                    max_notification_num[i]);
            firstMenuNotificationList[i] = notificationList; 
        }
        
        newsImageList = cmService.getImageList(1);
        rollImageList = cmService.getImageList(2);
        
        return new ForwardResolution(MAIN_PAGE);
    }

    @HandlesEvent("websitenavigation")
    public Resolution webSiteNavigation()
    {
        return new ForwardResolution(NAVIGATION_PAGE);
    }
   
    //  private
    // -----------------------------------------------------------------
    private int generateTime()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MARCH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        
        if (month < 10)
        {
            sb.append(0);
        }
        sb.append(month);
        
        if (day < 10)
        {
            sb.append(0);
        }
        sb.append(day);
        
        return Integer.parseInt(sb.toString());
    }
    

}
