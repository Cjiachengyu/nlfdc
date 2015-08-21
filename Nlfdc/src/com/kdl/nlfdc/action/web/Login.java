package com.kdl.nlfdc.action.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.exception.AccountInvalidException;

@SessionScope
@UrlBinding("/loginaction")
public class Login extends AbstractActionBean
{
    private static final long serialVersionUID = -8805358472725790310L;

    
    private static final String LOGIN_PAGE = "/WEB-INF/index/admin_login.jsp";

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    public Resolution defaultHandler() throws Exception
    {
        return new ForwardResolution(LOGIN_PAGE);
    }
    
    @HandlesEvent("index")
    public Resolution index()
    {
        return getIndexResolution();
    }

    @HandlesEvent("yjlogincheck")
    public Resolution yjLoginCheck()
    {
        logRequest();

        String email = getParam("email");
        String password = getParam("password");
        
        Admin admin = cmService.getAdminByLoginName(email);

        if (admin == null)
        {
            return getStringResolution("wrong_email");
        }

        if (!password.equals(admin.getPassword()))
        {
            return getStringResolution("wrong_password");
        }

        try
        {
            loginSuccessInitSession(admin);
        }
        catch (AccountInvalidException e)
        {
            return getStringResolution("wrong_email");
        }

        return getStringResolution("ok");
    }


    @HandlesEvent("yjsignout")
    public Resolution yjSignOut()
    {
        logRequest();

        invlidSession();

        return new RedirectResolution("/loginaction");
    }

    @HandlesEvent("switchlang")
    public Resolution switchLang()
    {
        logRequest();

        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession();
        String lang = getParam("lang");
        session.setAttribute("lang", lang);
        String result = "ok_switch";
        return getStringResolution(result);
    }


    // private
    // --------------------------------------------------------------------------------
    private void invlidSession()
    {
        HttpSession session = getCurrentSession();
        session.removeAttribute("admin");
        getCurrentSession().invalidate();
    }

}
