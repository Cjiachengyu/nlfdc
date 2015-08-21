package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.PageModule;

@SessionScope
@UrlBinding("/yjteachernotification")
public class YjTeacherNotification extends AbstractNotification
{
    private static final long serialVersionUID = -7204617908409470765L;

    private static final String MAIN_PAGE = "/WEB-INF/jsp/yjteacher/TeacherNotification.jsp";

    private String showType;
    
    
    public String getShowType()
    {
        return showType;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("teachernotification")
    public Resolution masterNotification()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        pageModule = new PageModule(15);

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_TEA_NOTIFICATION);
        createTime = Utils.currentSeconds();
        
        showType = getParam("showType", "get");
        
        refreshNotificationList();

        return new ForwardResolution(MAIN_PAGE);
    }

    @HandlesEvent("turntopublishpage")
    public Resolution turnToPublishPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        classesToChoose = cmService.getClassListOfAUser(getCurrentUserId());

        return new ForwardResolution(PUBLISH_NOTIFICATION_PAGE);
    }

    @HandlesEvent("setnotificationreaded")
    public Resolution setNotificationReaded()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution(); 
        }
        
        int notificationId = getParamInt("notificationId");
        
        cmService.setUserNotificationReaded(getCurrentUserId(), notificationId);
        
        return getStringResolution("ok");
    }
    
    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    @Override
    protected void refreshNotificationList()
    {
        int userId = getCurrentUserId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        
        log("teacher refresh notification userId: " + userId + ", createTime: " + Utils.getSimpleTimeString(createTime) +
                ", limitBegin: " + limitBegin + ", pageSize: " + pageSize);

        int notificationCount = 0;
        if (showType.equals("send"))
        {
            notificationCount = cmService.getSelfCreatedNotificationCount(userId, createTime);
            notificationList = cmService.getSelfCreatedNotification(userId, createTime, limitBegin, pageSize);
        }
        else
        {
            notificationCount = cmService.getReceivedNotificationCount(userId, createTime);
            notificationList = cmService.getReceivedNotification(userId, createTime, limitBegin, pageSize);
        }

        pageModule.changeItemsCount(notificationCount);

        log("master refresh notification result, notificationCount: " + notificationCount + ", listSize: " +
                notificationList.size());
    }

    @Override
    protected Resolution getNotificationMainPage()
    {
        return new ForwardResolution(MAIN_PAGE);
    }


}
