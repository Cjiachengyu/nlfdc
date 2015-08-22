package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.springframework.dao.DuplicateKeyException;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.component.MenuSelector;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.User;

/**
 * 超级管理员和普通管理员都用这个类
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
@SessionScope
@UrlBinding("/adminmanageaction")
public class AdminManage extends AbstractActionBean
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String MANAGE = "/WEB-INF/jsp/admin/AdminManage.jsp";

    
    private List<Notification> notificationList;
    
    
    public List<Notification> getNotificationList()
    {
        return notificationList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("commonmanage")
    public Resolution commonManage()
    {
        logRequest();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.NOTIFICATION_MANAGE);
        
        pageModule = new PageModule(15);
        
        menuSelector = initMenuSelector();
        if (menuSelector.getCurrentFirstMenuId() == MenuSelector.MENU_ITEM_ALL)
        {
            menuSelector.selectDefaultFirstMenu();
        }
        
        return new ForwardResolution(MANAGE);
    }
   
    public Resolution selectFirstMenu()
    {
        logRequest();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        
        int firstMenuId = getParamInt("firstMenuId", 0);
        
        if (firstMenuId == menuSelector.getCurrentFirstMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            menuSelector.selectFirstMenu(firstMenuId);
            
            refreshNotificationList();
            return null;
        }
    }
    
    public Resolution selectSecondMenu()
    {
        logRequest();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        
        int secondMenuId = getParamInt("secondMenuId", 0);
        
        if (secondMenuId == menuSelector.getCurrentSecondMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            menuSelector.selectSecondMenu(secondMenuId);
            
            refreshNotificationList();
            return null;
        }
    }
    
    

    /**
     * 添加系统题库编辑
     * 
     * @return
     */
    @HandlesEvent("addnewsystemuser")
    public Resolution addNewSystemUser()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String loginName = getParam("loginName", "");
        String password = getParam("password", "");
        String userName = getParam("userName", "");
        int userRole = getParamInt("userRole", 0);

        if (loginName.equals("") || password.equals("") || userName.equals(""))
        {
            return getStringResolution("error");
        }
        if (loginName.length() > Constants.MaxLength.LOGIN_NAME ||
            password.length() > Constants.MaxLength.PASSWORD ||
            userName.length() > Constants.MaxLength.USER_NAME)
        {
            return getStringResolution("lengthException");
        }

        User systemUser = new User();
        systemUser.setLoginName(loginName);
        systemUser.setPassword(password);
        systemUser.setUserName(userName);
        systemUser.setUserRole(userRole);

        try
        {
            cmService.insertUserAndGetUserId(systemUser);
            // 插入成功
            int userId = systemUser.getUserId();
            if (userRole == Constants.UserRole.YJ_EDITOR)
            {
                systemUser.setLoginId(userId + "e");
            }

            cmService.updateUser(systemUser);
            return getStringResolution("ok");
        }
        catch (DuplicateKeyException e)
        {
            return getStringResolution("dupkey");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }

    }

    /**
     * 添加编辑时ajax检查loginName是否可用
     * 返回"ok" -> 可以使用
     * 返回"exist" -> 不可以使用
     * 
     * @return
     */
    @HandlesEvent("checkloginnameunique")
    public Resolution checkLoginNameUnique()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String loginName = getParam("loginName");

        User user = cmService.getUserByLoginInfo(loginName);
        if (user == null)
        {
            return getStringResolution("ok");
        }
        else
        {
            return getStringResolution("exist");
        }
    }
   
    
    // private 
    // ------------------------------------------------------------------------
    private void refreshNotificationList()
    {
        int adminId = getCurrentAdminId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        int firstMenuId = menuSelector.getCurrentFirstMenuId();
        int secondMenuId = menuSelector.getCurrentSecondMenuId();
        
        log("admin refresh notification adminId: " + adminId + ", firstMenuId: "+ firstMenuId + ", secondMenuId: " + secondMenuId
                +" limitBegin: " + limitBegin + ", pageSize: " + pageSize);

        int notificationCount = cmService.getAdminNotificationCount(firstMenuId, secondMenuId);

        notificationList = cmService.getAdminNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);

        pageModule.changeItemsCount(notificationCount);

        log("admin refresh notification result, notificationCount: " + notificationCount + ", listSize: " +
                notificationList.size());
    }
    
    
    // override
    // ------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return makeSureSuperAdmin() || makeSureCommonAdmin();
    }
    
}
