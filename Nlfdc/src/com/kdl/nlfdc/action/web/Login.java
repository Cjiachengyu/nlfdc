package com.kdl.nlfdc.action.web;

import java.util.ArrayDeque;

import javax.servlet.http.Cookie;
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
import com.kdl.nlfdc.domain.User;
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
        User user = cmService.getUserByLoginInfo(email);

        if (user == null)
        {
            return getStringResolution("wrong_email");
        }

        String password = getParam("password");
        if (!password.equals(user.getPassword()))
        {
            return getStringResolution("wrong_password");
        }

        try
        {
            loginSuccessInitSession(user);
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


    /**
     * 向userStack中push一个用户
     */
    @HandlesEvent("switchtouser")
    public Resolution switchToUser()
    {
        logRequest();

        User currentUser = getCurrentUser();
        if (currentUser == null)
        {
            return getYjLogoutResolution();
        }

        User user = cmService.getUser(getParamInt("userId"));

        // switch一定是从高身份到低身份，如果不是，则把userStack pop()到正确的位置
        ArrayDeque<User> userStack = getUserStack();
        while (userStack != null && userStack.size() > 0
                && user.getUserRole() >= ((User) userStack.getFirst()).getUserRole())
        {
            userStack.pop();
        }

        if (user == null)
        {
            return getYjLogoutResolution();
        }

        if (currentUser.getUserRole() > user.getUserRole())
        {
            userStack.push(getCurrentRealUser());
        }

        try
        {
            setGoBackUser(userStack);
            setRealUserSession(user);
        }
        catch (AccountInvalidException e)
        {
            return getYjLogoutResolution();
        }

        return getUserMainPage(user, false);
    }

    // private
    // --------------------------------------------------------------------------------
    private void invlidSession()
    {
        HttpSession session = getCurrentSession();
        session.removeAttribute("user");
        session.removeAttribute("loginUser");
        session.removeAttribute("realUser");
        getCurrentSession().invalidate();
    }

    private void invlidCookieAndSession()
    {
        Cookie cookie = new Cookie("nlfdc_tall", "");
        cookie.setMaxAge(0);
        context.getResponse().addCookie(cookie);

        HttpSession session = getCurrentSession();
        session.removeAttribute("user");
        session.removeAttribute("loginUser");
        session.removeAttribute("realUser");
        getCurrentSession().invalidate();
    }

}
