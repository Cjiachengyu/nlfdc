package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

@SessionScope
@UrlBinding("/userchangepwdaction")
public class UserChangePwd extends AbstractUserSetting
{
    private static final long serialVersionUID = 1535624149837750713L;

    private static final String CHANGE_PWD = "/WEB-INF/jsp/user/UserChangePwd.jsp";

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("defaulthandler")
    public Resolution defaultHandler()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        return new ForwardResolution(CHANGE_PWD);
    }
    

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && (getCurrentUser() != null);
    }
}
