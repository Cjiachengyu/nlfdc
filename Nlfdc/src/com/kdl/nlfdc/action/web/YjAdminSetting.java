package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * 幼教编辑设置模块
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月20日
 */

@SessionScope
@UrlBinding("/yjadminsettingaction")
public class YjAdminSetting extends AbstractUserSetting
{
    private static final long serialVersionUID = 3197153244985380409L;
    
    private static final String EDIT_PWD = "/WEB-INF/jsp/yjadmin/YjAdminSettingPwd.jsp";

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

        getCurrentSession().setAttribute("currentMemuOperation", "");

        return new ForwardResolution(EDIT_PWD);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjAdmin();
    }
    
}
