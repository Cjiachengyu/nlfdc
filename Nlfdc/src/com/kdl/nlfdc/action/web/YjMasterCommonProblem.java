package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;

@SessionScope
@UrlBinding("/yjmasterproblemmanageaction")
public class YjMasterCommonProblem extends AbstractCommonProblem
{
    private static final long serialVersionUID = -2244155666658840240L;

    protected static final String MANAGE = "/WEB-INF/jsp/yjmaster/MasterProblemManage.jsp";

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

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_MASTER_COMMON_MANAGE);
        return new ForwardResolution(MANAGE);
    }
    

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjMaster();
    }
}
