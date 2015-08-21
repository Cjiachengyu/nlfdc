package com.kdl.nlfdc.action.web;

import java.io.File;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.School;

@SessionScope
@UrlBinding("/yjmastersettingaction")
public class YjMasterSetting extends AbstractUserSetting
{
    private static final long serialVersionUID = 3278086211475795844L;

    private static final String EDIT_SCHOOL = "/WEB-INF/jsp/yjmaster/MasterEditSchool.jsp";
    private static final String EDIT_PWD = "/WEB-INF/jsp/yjmaster/MasterEditPwd.jsp";
    private static final String MESSAGE = "/WEB-INF/jsp/yjmaster/Message.jsp";

    private static final int SUB_MENU_SCHOOL = 1;
    private static final int SUB_MENU_PWD = 2;

    private FileBean schoolIcon;
    private String imageName;

    public FileBean getSchoolIcon()
    {
        return schoolIcon;
    }

    public void setSchoolIcon(FileBean schoolIcon)
    {
        this.schoolIcon = schoolIcon;
    }

    public School getSchool()
    {
        return (School) getCurrentSession().getAttribute("school");
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("mastersetting")
    public Resolution masterSetting()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", "");

        int subMenuType = getParamInt("subMenuType", 1);
        switch (subMenuType)
        {
        case SUB_MENU_SCHOOL:
            return new ForwardResolution(EDIT_SCHOOL);

        case SUB_MENU_PWD:
            return new ForwardResolution(EDIT_PWD);
        }

        return new ForwardResolution(EDIT_SCHOOL);
    }

    @HandlesEvent("changeschoolname")
    public Resolution changeSchoolName()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String newSchoolName = getParam("newSchoolName", "");
        if (!newSchoolName.equals("") && newSchoolName.length() <= Constants.MaxLength.SCHOOL_NAME)
        {
            School school = getSchool();
            school.setSchoolName(newSchoolName);
            try
            {
                cmService.updateSchool(school);
                return getStringResolution("ok");
            }
            catch (Exception e)
            {
                return getStringResolution("error");
            }
        }
        return getStringResolution("error");
    }

    @HandlesEvent("changeschoolmotto")
    public Resolution changeSchoolMotto()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String newSchoolMotto = getParam("newSchoolMotto", "");

        if (newSchoolMotto.equals("") || newSchoolMotto.length() > Constants.MaxLength.SCHOOL_MOTTO)
        {
            return getStringResolution("error");
        }
        School school = getSchool();
        if (newSchoolMotto.equals("null"))
        {
            newSchoolMotto = "";
        }
        school.setSchoolMotto(newSchoolMotto);

        try
        {
            cmService.updateSchool(school);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }


    @HandlesEvent("uploadschoolicon")
    public Resolution uploadSchoolIcon()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        if (schoolIcon == null)
        {
            return getStringResolution("error");
        }

        imageName = Utils.getNextLongId() + "." + Utils.getFileSuffix(schoolIcon.getFileName()).toLowerCase();
        String saveDir = Constants.PATH_FILE + "schoolIcon/";
        Utils.makeSureDirExists(saveDir);

        try
        {
            schoolIcon.save(new File(saveDir + imageName));
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }


    @HandlesEvent("changeschoolicon")
    public Resolution changeSchoolIcon()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        if (imageName == null)
        {
            return getStringResolution("error");
        }

        try
        {
            School school = getSchool();
            school.setSchoolIcon(imageName);
            cmService.updateSchool(school);
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
        return commonSessionIsValid() && makeSureYjMaster();
    }
}
