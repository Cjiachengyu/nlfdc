package com.kdl.nlfdc.action.web;

import org.springframework.dao.DuplicateKeyException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.User;


/**
 * 用户的公共设置操作
 * 
 * 1. 修改password
 * 2. 修改loginName
 * 3. 修改email
 * 
 */
@SessionScope
public abstract class AbstractUserSetting extends AbstractActionBean
{
    private static final long serialVersionUID = 4327400800449313038L;

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("dochangepwd")
    public Resolution doChangePwd()
    {
        logRequest();
        // 这边不需要validateSession, 在子类中完成

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        User realUser = getCurrentRealUser();
        String oldPwd = realUser.getPassword();
        String newPwd = getParam("newPwd");
        if (!oldPwd.equals(getParam("oldPwd")))
        {
            return getStringResolution("wrongPwd");
        }

        if (newPwd.length() > Constants.MaxLength.PASSWORD)
        {
            return getStringResolution("length_exception");
        }

        realUser.setPassword(newPwd);
        try
        {
            log("update " + realUser.getUserName() + "'s pwd from " + oldPwd + " to " + newPwd
                    + ", loginUser: " + getCurrentLoginUser().getUserName());

            cmService.updateUser(realUser);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            realUser.setPassword(oldPwd);
            return getStringResolution("error");
        }
    }

    @HandlesEvent("dochangeloginname")
    public Resolution doChangeLoginName()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        User realUser = getCurrentRealUser();

        String newLoginName = getParam("newLoginName");
        // 如果修改失败，要把loginName还原
        String oldLoginName = realUser.getLoginName();

        if (newLoginName.equals(""))
        {
            return getStringResolution("error");
        }

        if (newLoginName.length() > Constants.MaxLength.LOGIN_NAME)
        {
            return getStringResolution("length_exception");
        }

        realUser.setLoginName(newLoginName);
        try
        {
            cmService.updateUser(realUser);
            return getStringResolution("ok");
        }
        catch (DuplicateKeyException e)
        {
            log("dup login name: " + newLoginName);
            realUser.setLoginName(oldLoginName);
            return getStringResolution("dupkey");
        }
        catch (Exception e)
        {
            realUser.setLoginName(oldLoginName);
            return getStringResolution("error");
        }
    }

    @HandlesEvent("dochangeemail")
    public Resolution doChangeEmail()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        User realUser = getCurrentRealUser();

        String newEmail = getParam("newEmail");

        String oldEmail = realUser.getEmail();
        if (newEmail.equals(""))
        {
            return getStringResolution("error");
        }
        if (newEmail.length() > Constants.MaxLength.EMAIL)
        {
            return getStringResolution("length_exception");
        }
        realUser.setEmail(newEmail);
        try
        {
            cmService.updateUser(realUser);
            return getStringResolution("ok");
        }
        catch (DuplicateKeyException e)
        {
            log("dup email: " + newEmail);
            realUser.setEmail(oldEmail);
            return getStringResolution("dupkey");
        }
        catch (Exception e)
        {
            realUser.setEmail(oldEmail);
            return getStringResolution("error");
        }
    }
}
