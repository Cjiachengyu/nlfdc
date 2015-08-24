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
    private static final String NOTIFICATION_LISTVIEW = "/WEB-INF/jsp/component/AdminNotificationListView.jsp";
    protected static final String PUBLISH_NOTIFICATION_PAGE = "/WEB-INF/jsp/component/AdminNotificationPublish.jsp";

    
    private List<Notification> notificationList;
    
    public List<Notification> getNotificationList()
    {
        return notificationList;
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
            menuSelector = initMenuSelector();
            menuSelector.selectFirstMenu(firstMenuId);
        }
       
        refreshNotificationList();
        return new ForwardResolution(COMMON_USER_NOTIFICATION_MAIN);
    }
   
    @HandlesEvent("selectfirstmenu")
    public Resolution selectFirstMenu()
    {
        logRequest();
        
        int firstMenuId = getParamInt("firstMenuId", 0);
        
        if (firstMenuId == menuSelector.getCurrentFirstMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            menuSelector.selectFirstMenu(firstMenuId);
            
            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
    }
   
    @HandlesEvent("selectsecondmenu")
    public Resolution selectSecondMenu()
    {
        logRequest();
        
        int secondMenuId = getParamInt("secondMenuId", 0);
        
        if (secondMenuId == menuSelector.getCurrentSecondMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            menuSelector.selectSecondMenu(secondMenuId);
            
            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
    }
    
    @HandlesEvent("turntopublishpage")
    public Resolution turnToPublishPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        return new ForwardResolution(PUBLISH_NOTIFICATION_PAGE);
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
