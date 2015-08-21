package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.Grade;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.YearRange;

@SessionScope
@UrlBinding("/yjmasterclassaction")
public class YjMasterClsList extends AbstractMasterSelectSubject
{
    private static final long serialVersionUID = 3339859300873476879L;

    private static final String YJ_MASTER_CLASSINFO_MAIN = "/WEB-INF/jsp/yjmaster/MasterClassInfo.jsp";
    private static final String YJ_MASTER_CLASS_LISTVIEW = "/WEB-INF/jsp/yjmaster/ClassListView.jsp";
    private static final String YJ_MASTER_CLASS_ADDCLASS = "/WEB-INF/jsp/yjmaster/addClass.jsp";

    // 学校班级的入学年份集合，按年级去搜集班级信息
    private List<Grade> schoolGradesList = new ArrayList<Grade>();

    // 每个classInfoList[i]对应一个年级中所有班级的情况
    private List<Cls>[] classInfoList;
    private YearRange year = new YearRange();


    public int getThisYear()
    {
        return year.getThisYear();
    }

    public int getMinYear()
    {
        return year.getMinYear();
    }

    public List<Grade> getSchoolGradesList()
    {
        return schoolGradesList;
    }

    public List<Cls>[] getClassInfoList()
    {
        return classInfoList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("masterclassinfo")
    public Resolution masterClassInfo()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_MASTER_CLASS_INFO);

        School school = (School) getCurrentSession().getAttribute("school");
        year.initData(school);

        initMasterSubject();

        refreshClassList();

        return new ForwardResolution(YJ_MASTER_CLASSINFO_MAIN);
    }

    @HandlesEvent("getclasslistview")
    public Resolution getClassListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(YJ_MASTER_CLASS_LISTVIEW);
    }

    @HandlesEvent("getaddclassview")
    public Resolution getAddClassView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(YJ_MASTER_CLASS_ADDCLASS);
    }

    // 执行添加班级操作
    @HandlesEvent("addclass")
    public Resolution addClass()
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
            return getStringResolution("dataError");
        }

        int schoolId = ((School) getCurrentSession().getAttribute("school")).getSchoolId();
        try
        {
            cmService.insertClass(clsName, classEnterYear, schoolId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjMaster();
    }

    // private
    // --------------------------------------------------------------------------------
    private void refreshClassList()
    {
        School school = ((School) getCurrentSession().getAttribute("school"));
        int schoolId = school.getSchoolId();

        List<Integer> enterSchoolYearList = cmService.getClassEnterYearList(schoolId);
        schoolGradesList = cmService.generateSchoolGrade(enterSchoolYearList);
        classInfoList = cmService.getClassInfoListOfASchool(schoolId, enterSchoolYearList, subjectId);
    }

    @Override
    protected Resolution selectSubjectReturnList()
    {
        refreshClassList();
        return new ForwardResolution(YJ_MASTER_CLASS_LISTVIEW);
    }

}
