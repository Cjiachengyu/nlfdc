package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Notification;

/**
 * 普通用户查看通知
 * 
 * @author Administrator
 * 
 * @date：2015年8月24日
 */
@SessionScope
@UrlBinding("/commonusernotificationmain")
public class CommonUser extends AbstractActionBean
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String COMMON_USER_NOTIFICATION_MAIN = "/WEB-INF/jsp/main/commonUserNotificationMain.jsp";
    private static final String COMMON_USER_VIEW_NOTIFICATION  = "/WEB-INF/jsp/main/commonUserNotificationView.jsp";
    private static final String NOTIFICATION_LIST_VIEW = "/WEB-INF/jsp/component/CommonNotificationListView.jsp";

    private List<Notification> notificationList;
    private Notification viewIngNotification;
    
    public List<Notification> getNotificationList()
    {
        return notificationList;
    }
    public Notification getViewIngNotification()
    {
        return viewIngNotification;
    }
  
    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("handlefirstmenu")
    public Resolution commonManage()
    {
        logRequest();
        
        pageModule = new PageModule(15);
        int firstMenuId = getParamInt("firstMenuId", 1);
        if (firstMenuId == 1)
        {
            return getIndexResolution();
        }
        
        if (menuSelector == null || menuSelector.getCurrentFirstMenuId() != firstMenuId)
        {
            menuSelector = initCommomMenuSelector();
            menuSelector.selectFirstMenu(firstMenuId);
        }
        
        refreshNotificationList();
        return new ForwardResolution(COMMON_USER_NOTIFICATION_MAIN);
    }
  
    @HandlesEvent("selectfirstandsecondmenu")
    public Resolution selectFirstAndSecondMenu()
    {
        logRequest();
        
        pageModule = new PageModule(15);
        int firstMenuId = getParamInt("firstMenuId", 1);
        int secondMenuId = getParamInt("secondMenuId", 0);
        if (firstMenuId == 1)
        {
            return getIndexResolution();
        }
        
        if (menuSelector == null || menuSelector.getCurrentFirstMenuId() != firstMenuId)
        {
            menuSelector = initCommomMenuSelector();
            menuSelector.selectFirstMenu(firstMenuId);
            menuSelector.selectSecondMenu(secondMenuId);
        }
        else
        {
            menuSelector.selectSecondMenu(secondMenuId);
        }
       
        refreshNotificationList();
        return new ForwardResolution(COMMON_USER_NOTIFICATION_MAIN);
    }
    
    @HandlesEvent("viewnotification")
    public Resolution viewNotification()
    {
        logRequest();
        
        int notificationId = getParamInt("notificationId", 0);
        viewIngNotification = cmService.getNotification(notificationId);
        
        if (viewIngNotification == null)
        {
            return getIndexResolution();
        }
        else
        {
            if (menuSelector == null)
            {
                menuSelector = initCommomMenuSelector();
            }
           
            menuSelector.selectFirstMenu(viewIngNotification.getFirstMenuId());
            menuSelector.selectSecondMenu(viewIngNotification.getSecondMenuId());
            
            return new ForwardResolution(COMMON_USER_VIEW_NOTIFICATION);
        }
    }
    
    @HandlesEvent("gotopage")
    public Resolution gotoPage()
    {
        logRequest();

        pageModule.gotoPage(getParamInt("pageNum", 1));

        refreshNotificationList();

        return new ForwardResolution(NOTIFICATION_LIST_VIEW);
    }
    
    // private 
    // ------------------------------------------------------------------------
    private void refreshNotificationList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        int firstMenuId = menuSelector.getCurrentFirstMenuId();
        int secondMenuId = menuSelector.getCurrentSecondMenuId();
        
        log("commonUser refresh notification, firstMenuId: "+ firstMenuId + ", secondMenuId: " + secondMenuId
                +" limitBegin: " + limitBegin + ", pageSize: " + pageSize);

        int notificationCount = cmService.getCommonNotificationCount(firstMenuId, secondMenuId);

        notificationList = cmService.getCommonNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);

        pageModule.changeItemsCount(notificationCount);

        log("commonUser refresh notification result, notificationCount: " + notificationCount + ", listSize: " +
                notificationList.size());
    }
    
}
