package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Province;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.SchoolInfo;

/**
 * 显示幼教学校列表
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月24日
 */

@SessionScope
@UrlBinding("/yjadminschoollistaction")
public class YjAdminShoolList extends AbstractActionBean
{
    private static final long serialVersionUID = -7380855812957231838L;
    
    private static final String ADMIN_SCHOOL_LIST_MAIN = "/WEB-INF/jsp/yjadmin/YjAdminSchoolList.jsp"; 
    private static final String YJ_ADMIN_SCHOOL_LISTVIEW = "/WEB-INF/jsp/yjadmin/YjSchoolListView.jsp";
    private static final String ADMIN_SCHOOL_ADD_SCHOOL = "/WEB-INF/jsp/yjadmin/AddSchool.jsp";

    private List<Province> provinceList;
    private List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
    private String provinceId;
    private String cityId;

    public List<Province> getProvinceList()
    {
        return provinceList;
    }

    public List<SchoolInfo> getSchoolInfoList()
    {
        return schoolInfoList;
    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public String getCityId()
    {
        return cityId;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("admin")
    public Resolution admin()
    {
        logRequest();
        validateSession();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_ADMIN_SCHOOL);

        provinceList = cmService.getAllProvince();
        resfreshSchoolList();

        return new ForwardResolution(ADMIN_SCHOOL_LIST_MAIN);
    }

    // 响应选择省份操作
    @HandlesEvent("getcity")
    public Resolution getCity()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getJsonTimeoutResolution();
        }

        String provinceIdParam = getParam("provinceId");
        int ignore = getParamInt("ignore", 0);
        if (ignore != 1)
        {
            provinceId = provinceIdParam;
        }

        String cityJsonString = cmService.getCityJsonStringByProvinceId(provinceIdParam);

        cityId = null;
        resfreshSchoolList();

        return getJsonStringResolution(cityJsonString);
    }

    // 响应选择城市操作；刷新学校列表
    @HandlesEvent("changecity")
    public Resolution changeCity()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        // 设置cityId ，刷新学校列表
        cityId = getParam("cityId");

        resfreshSchoolList();
        return getStringResolution("ok");
    }

    @HandlesEvent("getaddschoolview")
    public Resolution getAddSchoolView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        return new ForwardResolution(ADMIN_SCHOOL_ADD_SCHOOL);
    }

    @HandlesEvent("addnewschool")
    public Resolution addNewSchool()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        String schoolName = getParam("schoolName", "");
        String provinceIdIn = getParam("provinceId", "-1");
        String cityIdIn = getParam("cityId", "-1");
        int category = getParamInt("category", -1);

        if (schoolName.equals("") || provinceIdIn.equals("-1") || cityIdIn.equals("-1") || category == -1)
        {
            return getStringResolution("error");
        }

        if (schoolName.length() > Constants.MaxLength.SCHOOL_NAME)
        {
            return getStringResolution("lengthException");
        }

        School school = new School();
        school.setSchoolName(schoolName);
        school.setProvinceId(provinceIdIn);
        school.setCityId(cityIdIn);
        school.setCategory(category);
        try
        {
            cmService.insertSchool(school);

            resfreshSchoolList();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("getschoollistview")
    public Resolution getSchoolListView()
    {
        logRequest();
        // 调用这个方法之前都判断过session是否为空，这边不用再判断，如果以后需要再添加
        return new ForwardResolution(YJ_ADMIN_SCHOOL_LISTVIEW);
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
    private void resfreshSchoolList()
    {
        schoolInfoList.clear();
        List<School> schoolList = cmService.getAllSchoolInCities(provinceId, cityId, Constants.Category.YOU_JIAO);
        if (schoolList != null && schoolList.size() > 0)
        {
            for (School school : schoolList)
            {
                int schoolId = school.getSchoolId();
                SchoolInfo schoolInfo = new SchoolInfo();

                schoolInfo.setSchoolId(schoolId);
                schoolInfo.setSchoolName(school.getSchoolName());
                
                schoolInfo.setNumOfTeachers(cmService.getCountOfSchoolMember(schoolId, Constants.UserRole.YJ_TEACHER, 0));
                schoolInfo.setNumOfStudents(cmService.getCountOfSchoolMember(schoolId, Constants.UserRole.YJ_PARENT, 0));
                schoolInfo.setNumOfClasses(cmService.getSchoolClassCount(schoolId));
              
                schoolInfoList.add(schoolInfo);
            }
        }
    }

}
