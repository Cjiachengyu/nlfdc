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
@UrlBinding("/yjmasternotification")
public class YjMasterNotification extends AbstractNotification
{
    private static final long serialVersionUID = -7204617908409470765L;

    private static final String MAIN_PAGE = "/WEB-INF/jsp/yjmaster/MasterNotification.jsp";
    
    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("masternotification")
    public Resolution masterNotification()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        pageModule = new PageModule(15);

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_MASTER_NOTIFICATION);
        createTime = Utils.currentSeconds();
        
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

        classesToChoose = cmService.getOtherClassListOfASchool(getCurrentUser().getSchoolId(), 0);

        return new ForwardResolution(PUBLISH_NOTIFICATION_PAGE);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjMaster();
    }

    @Override
    protected void refreshNotificationList()
    {
        int userId = getCurrentUserId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        
        log("master refresh notification userId: " + userId + ", createTime: " + Utils.getSimpleTimeString(createTime) +
                ", limitBegin: " + limitBegin + ", pageSize: " + pageSize);

        int notificationCount = cmService.getSelfCreatedNotificationCount(userId, createTime);

        notificationList = cmService.getSelfCreatedNotification(userId, createTime, limitBegin, pageSize);

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
