package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import org.apache.tomcat.util.json.JSONObject;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.User;

/**
 * 校长和管理员公共的管理用户模块
 * 
 * 1. 根据用户的登录信息查找用户的密码
 * 2. 重置用户的密码
 */
@SessionScope
public abstract class AbstractCommonProblem extends AbstractActionBean
{
    private static final long serialVersionUID = -1089283068710293874L;

    private User pwdUser;

    // resolution
    // --------------------------------------------------------------------------------
    @HandlesEvent("findpassword")
    public Resolution findPassword()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return null;
        }

        String email = getParam("email");
        if (getCurrentUser().getUserRole() == Constants.UserRole.YJ_ADMIN)
        {
            pwdUser = cmService.getUserByLoginInfo(email);
            if (isYjAdmin(pwdUser))
            {
                pwdUser = null;
            }
        }
        else if (getCurrentUser().getUserRole() == Constants.UserRole.YJ_MASTER)
        {
            pwdUser = cmService.getUserByLoginInfo(email);
            if (isYjMaster(pwdUser))
            {
                pwdUser = null;
            }
        }

        JSONObject json = cmService.generateJsonObj(pwdUser);

        return getJsonStringResolution(json.toString());
    }

    @HandlesEvent("resetpassword")
    public Resolution resetPassword()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String newPassword = getParam("newPassword", "");
        if (newPassword.equals("") || newPassword.length() > Constants.MaxLength.PASSWORD)
        {
            return getStringResolution("error");
        }

        try
        {
            cmService.updateUserPwd(pwdUser, newPassword);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

}
