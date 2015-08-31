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
@UrlBinding("/commonusersearch")
public class CommonUserSearch extends AbstractActionBean
{
    private static final long serialVersionUID = -4211569791959625408L;

    private static final String COMMON_USER_NOTIFICATION_MAIN = "/WEB-INF/jsp/main/commonUserNotificationSearch.jsp";

    private List<Notification> notificationList;
    private String searchText;
    
    public List<Notification> getNotificationList()
    {
        return notificationList;
    }
    public String getSearchText()
    {
        return searchText;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("searchnotification")
    public Resolution searchNotification()
    {
        logRequest();
        
        pageModule = new PageModule(15);
        searchText = getParam("searchText");
        refreshNotificationList();
        
        return new ForwardResolution(COMMON_USER_NOTIFICATION_MAIN);
    }
  
    
    // private 
    // ------------------------------------------------------------------------
    private void refreshNotificationList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        
        log("commonUser search notification, searchText: "+ searchText);
        
        System.out.println("searchText: " + searchText);
        int notificationCount = cmService.getSearchNotificationListCount("%" + searchText + "%");

        notificationList = cmService.getSearchNotificationList("%" + searchText + "%", limitBegin, pageSize);
        
        pageModule.changeItemsCount(notificationCount);
        
        System.out.println("commonUser search notification, size: " + notificationList.size() );
    }
    
}
