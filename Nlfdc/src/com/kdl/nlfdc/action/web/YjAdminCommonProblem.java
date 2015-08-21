package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.springframework.dao.DuplicateKeyException;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.User;

/**
 * 幼教管理员-》常见问题管理
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月23日
 */

@SessionScope
@UrlBinding("/yjadminproblemmanageaction")
public class YjAdminCommonProblem extends AbstractCommonProblem
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String MANAGE = "/WEB-INF/jsp/yjadmin/YjAdminProblemManage.jsp";

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("commonmanage")
    public Resolution commonManage()
    {
        logRequest();
        validateSession();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_ADMIN_COMMON_MANAGE);
        return new ForwardResolution(MANAGE);
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
    
    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjAdmin();
    }
    
}
