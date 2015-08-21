package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Utils;

/**
 * 幼教管理员-》编辑班级
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月27日
 */
@SessionScope
@UrlBinding("/yjadmineditcls")
public class YjAdminEditCls extends YjMasterEditCls
{
    private static final long serialVersionUID = 6576367988494922L;

    private static final String ADMIN_SCHOOL_CLASS_ADD_USER = "/WEB-INF/jsp/yjadmin/YjSchoolManageClassAddUser.jsp";
    

    // Resolution
    // --------------------------------------------------------------------------------
    @HandlesEvent("getclassadduserview")
    public Resolution getClassAddUserView()
    {
        logRequest();

        clsId = getParamInt("clsId", -1);
        classInfo = cmService.getClassInfo(clsId);

        if (!sessionIsValid() || classInfo == null)
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(ADMIN_SCHOOL_CLASS_ADD_USER);
    }

    @HandlesEvent("classadduser")
    public Resolution classAddUser()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String userNameList = getParam("userName", "");
        String password = getParam("password", "");
        int userRole = getParamInt("userRole", 0);

        if (userNameList.equals("") || password.equals("") || userRole == 0)
        {
            return getStringResolution("error");
        }

        List<String> userNames = Utils.splitStrList(userNameList, "\n");

        try
        {
            cmService.insertUsers(userNames, userRole, password, clsId, schoolId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
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
