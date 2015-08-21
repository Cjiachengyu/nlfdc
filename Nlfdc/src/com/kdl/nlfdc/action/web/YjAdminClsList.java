package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.Grade;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.YearRange;

@SessionScope
@UrlBinding("/yjadminclassaction")
public class YjAdminClsList extends AbstractActionBean
{
    private static final long serialVersionUID = -7088955100395955000L;

    private static final String YJ_ADMIN_SCHOOL_CLASS = "/WEB-INF/jsp/yjadmin/YjAdminSchoolManageClass.jsp";

    private static final String ADMIN_SCHOOL_ADD_CLASS = "/WEB-INF/jsp/yjadmin/YjSchoolAddClass.jsp";
    private static final String ADMIN_SCHOOL_CLASS_USER_LIST = "/WEB-INF/jsp/yjadmin/SchoolManageClassUserList.jsp";

    private School editingSchool;

    private String viewingClassName;
    private YearRange yearRange = new YearRange();

    private List<Integer> enterSchoolYearList;
    private List<Grade> schoolGradesList = new ArrayList<Grade>();
    private List<Cls> classInfoList[];
    private List<User> userOfClassList;

    public List<User> getUserOfClassList()
    {
        return userOfClassList;
    }

    // getMinYear()、 getThisYear() 不能自动生成，用到Year的地方固定这样写
    public int getMinYear()
    {
        return yearRange.getMinYear();
    }

    public int getThisYear()
    {
        return yearRange.getThisYear();
    }

    public List<Cls>[] getClassInfoList()
    {
        return classInfoList;
    }

    public School getEditingSchool()
    {
        return editingSchool;
    }

    public String getViewingClassName()
    {
        return viewingClassName;
    }

    public List<Grade> getSchoolGradesList()
    {
        return schoolGradesList;
    }

    @DefaultHandler
    @HandlesEvent("manageclass")
    public Resolution manageClass()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        int schoolId = getParamInt("schoolId", -1);

        School school = cmService.getSchool(schoolId);
        if (school == null)
        {
            return new RedirectResolution(YjAdminShoolList.class);
        }

        editingSchool = school;
        yearRange.initData(school);

        refreshClassList();
        return new ForwardResolution(YJ_ADMIN_SCHOOL_CLASS);
    }

    @HandlesEvent("getaddclassview")
    public Resolution getAddClassView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(ADMIN_SCHOOL_ADD_CLASS);
    }

    @HandlesEvent("getclassuserinfoview")
    public Resolution getClassUserInfoView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int clsId = getParamInt("clsId");
        Cls classInfo = cmService.getClassInfo(clsId);
        viewingClassName = classInfo.getClsName();
        userOfClassList = cmService.getUserByClassId(clsId);

        return new ForwardResolution(ADMIN_SCHOOL_CLASS_USER_LIST);
    }

    @HandlesEvent("addoneclass")
    public Resolution addOneClass()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        String clsName = getParam("clsName", "");
        int classEnterYear = getParamInt("classEnterYear", 0);

        if (clsName.equals("") || classEnterYear == 0 || clsName.length() > Constants.MaxLength.CLASS_NAME)
        {
            return getStringResolution("error");
        }

        try
        {
            cmService.insertClass(clsName, classEnterYear, editingSchool.getSchoolId());
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    // TODO 事务处理
    @HandlesEvent("addbatchclass")
    public Resolution addBatchClass()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String baseName = getParam("baseClassName", "");
        int classEnterYear = getParamInt("classEnterYear", 0);
        int beginIndex = getParamInt("beginIndex", 0);
        int endIndex = getParamInt("endIndex", 0);
        int schoolId = editingSchool.getSchoolId();

        if (baseName.equals("") || classEnterYear == 0 || beginIndex == 0 || endIndex == 0 ||
                baseName.length() > Constants.MaxLength.BASE_CLASSNAME)
        {
            return getStringResolution("error");
        }

        try
        {
            for (int i = beginIndex; i <= endIndex; i++)
            {
                String clsName = baseName + "(" + i + ")班";
                cmService.insertClass(clsName, classEnterYear, schoolId);
            }
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

    // private
    // --------------------------------------------------------------------------------
    private void refreshClassList()
    {
        int schoolId = editingSchool.getSchoolId();

        enterSchoolYearList = cmService.getClassEnterYearList(schoolId);

        schoolGradesList = cmService.generateSchoolGrade(enterSchoolYearList);

        classInfoList = new ArrayList[enterSchoolYearList.size()];

        for (int i = 0; i < enterSchoolYearList.size(); i++)
        {
            classInfoList[i] = cmService.getClassByGrade(schoolId, (int) enterSchoolYearList.get(i));
            for (Cls classInfo : classInfoList[i])
            {
                int clsId = classInfo.getClsId();
                classInfo.setNumberOfStudents(cmService.getCountOfClassMember(clsId, Constants.UserRole.YJ_PARENT));
                classInfo.setNumberOfTeachers(cmService.getCountOfClassMember(clsId, Constants.UserRole.YJ_TEACHER));
            }
        }
    }


}
