package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Admin;


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

        if (getCurrentAdmin() == null)
        {
            return getStringTimeoutResolution();
        }

        Admin admin = getCurrentAdmin();
        String oldPwd = admin.getPassword();
        String newPwd = getParam("newPwd");
        if (!oldPwd.equals(getParam("oldPwd")))
        {
            return getStringResolution("wrongPwd");
        }

        if (newPwd.length() > Constants.MaxLength.PASSWORD)
        {
            return getStringResolution("length_exception");
        }

        admin.setPassword(newPwd);
        try
        {
            log("update " + admin.getAdminName() + "'s pwd from " + oldPwd + " to " + newPwd);

            cmService.updateAdmin(admin);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            admin.setPassword(oldPwd);
            return getStringResolution("error");
        }
    }
    
}
