package com.kdl.nlfdc.action.web;

import java.io.InputStream;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.tomcat.util.json.JSONArray;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.GenerateExcelUtils;
import com.kdl.nlfdc.domain.Province;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.SchoolScm;
import com.kdl.nlfdc.domain.Scm;
import com.kdl.nlfdc.domain.User;

/**
 * 幼教管理员-》编辑学校
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月27日
 */
@SessionScope
@UrlBinding("/yjadmineditschoolaction")
public class YjAdminEditShool extends AbstractActionBean
{
    private static final long serialVersionUID = -4817399275793474782L;

    private static final String ADMIN_SCHOOL_EDIT_SCHOOL = "/WEB-INF/jsp/yjadmin/YjAdminEditSchool.jsp";
    private static final String ADMIN_SCHOOL_ADD_MASTER = "/WEB-INF/jsp/yjadmin/YjEditSchoolAddMaster.jsp";
    private static final String ADMIN_SCHOOL_EDIT_SCHOOL_MASTERS_INFO = "/WEB-INF/jsp/yjadmin/EditSchoolMastersInfoList.jsp";
    private static final String ADMIN_SCHOOL_EDIT_SCHOOL_TEACHERS_INFO = "/WEB-INF/jsp/yjadmin/EditSchoolTeachersInfoList.jsp";
    private static final String ADMIN_SCHOOL_EDIT_SCHOOL_STUDENTS_INFO = "/WEB-INF/jsp/yjadmin/EditSchoolStudentsInfoList.jsp";

    private List<Province> provinceList;
    private String provinceId;
    private String cityId;
    private School editingSchool;
    private List<Scm> schemaInEditingSchoolList;
    private List<Scm> schemaOutEditingSchoolList;
    private List<User> mastersInEditingSchoolList;
    private List<User> teachersInEditingSchoolList;
    private List<User> studentsInEditingSchoolList;

    public List<User> getTeachersInEditingSchoolList()
    {
        return teachersInEditingSchoolList;
    }

    public List<User> getStudentsInEditingSchoolList()
    {
        return studentsInEditingSchoolList;
    }

    public List<User> getMastersInEditingSchoolList()
    {
        return mastersInEditingSchoolList;
    }

    public List<Scm> getSchemaInEditingSchoolList()
    {
        return schemaInEditingSchoolList;
    }

    public List<Scm> getSchemaOutEditingSchoolList()
    {
        return schemaOutEditingSchoolList;
    }

    public School getEditingSchool()
    {
        return editingSchool;
    }

    public List<Province> getProvinceList()
    {
        return provinceList;
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
    @HandlesEvent("editschool")
    public Resolution editSchool()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            // 直接连接过来的，不是通过ajax请求，所以直接返回到登录页面
            return getYjLogoutResolution();
        }

        int schoolId = getParamInt("schoolId", -1);
        School school = cmService.getSchool(schoolId);

        if (school == null)
        {
            return new RedirectResolution(YjAdminShoolList.class);
        }
        provinceList = cmService.getAllProvince();
        // 大纲
        schemaInEditingSchoolList = cmService.getSchemaBySchoolId(schoolId);
        schemaOutEditingSchoolList = cmService.getSchemaOutSchool(schoolId);
        mastersInEditingSchoolList = cmService.getSchoolUsers(schoolId, Constants.UserRole.YJ_MASTER);
        teachersInEditingSchoolList = cmService.getSchoolUsers(schoolId, Constants.UserRole.YJ_TEACHER);
        // 幼教部分没有学生，幼教家长对应普通的学生
        studentsInEditingSchoolList = cmService.getSchoolUsers(schoolId, Constants.UserRole.YJ_PARENT);

        editingSchool = school;

        return new ForwardResolution(ADMIN_SCHOOL_EDIT_SCHOOL);
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

        String cityJsonString = cmService.getCityJsonStringByProvinceId(provinceIdParam);
        return getJsonStringResolution(cityJsonString);
    }

    @HandlesEvent("getaddmasterview")
    public Resolution getAddMasterView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        return new ForwardResolution(ADMIN_SCHOOL_ADD_MASTER);
    }

    @HandlesEvent("addmaster")
    public Resolution addMaster()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        String password = getParam("password", "");
        String userName = getParam("userName", "");

        if (password.equals("") || userName.equals(""))
        {
            return getStringResolution("error");
        }

        User master = new User();
        master.setUserName(userName);
        master.setPassword(password);
        master.setSchoolId(editingSchool.getSchoolId());
        master.setUserRole(Constants.UserRole.YJ_MASTER);
        try
        {
            cmService.insertUserAndGetUserId(master);
            // 插入成功
            int userId = master.getUserId();
            String loginName = userId + "m";
            String loginId = userId + "m";
            master.setLoginName(loginName);
            master.setLoginId(loginId);

            cmService.updateUser(master);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    /**
     * 初始化需要的信息，跳转到编辑学校的页面
     * 
     * @return
     */
    @HandlesEvent("updateschool")
    public Resolution updateSchool()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        String newSchoolName = getParam("newSchoolName", "");
        String provinceId = getParam("provinceId", "");
        String cityId = getParam("cityId", "");
        int category = getParamInt("category", -1);

        if (newSchoolName.equals("") || provinceId.equals("") || cityId.equals("") || category == -1)
        {
            return getStringResolution("error");
        }

        editingSchool.setSchoolName(newSchoolName);
        editingSchool.setProvinceId(provinceId);
        editingSchool.setCityId(cityId);
        editingSchool.setCategory(category);
        try
        {
            cmService.updateSchool(editingSchool);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("addschematoeditingschool")
    public Resolution addSchemaToEditingSchool()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        int scmId = getParamInt("scmId", -1);
        if (scmId == -1)
        {
            return getStringResolution("error");
        }

        SchoolScm schoolSchema = new SchoolScm();
        schoolSchema.setScmId(scmId);
        schoolSchema.setSchoolId(editingSchool.getSchoolId());
        try
        {
            cmService.insertSchoolSchema(schoolSchema);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("deleteschemafromeditingschool")
    public Resolution deleteSchemaFromEditingSchool()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        int scmId = getParamInt("scmId", -1);
        if (scmId == -1)
        {
            return getStringResolution("error");
        }

        SchoolScm schoolSchema = new SchoolScm();
        schoolSchema.setScmId(scmId);
        schoolSchema.setSchoolId(editingSchool.getSchoolId());
        try
        {
            cmService.deleteSchoolSchema(editingSchool.getSchoolId(), scmId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("getschemaineditingschoollistjson")
    public Resolution getSchemaInEditingSchoolListJson()
    {
        // 调用该方法前都进行了session是否为空判断，这边不用再判断
        logRequest();
        JSONArray jsonArray = new JSONArray();
        schemaInEditingSchoolList = cmService.getSchemaBySchoolId(editingSchool.getSchoolId());
        for (Scm schema : schemaInEditingSchoolList)
        {
            JSONObject json = new JSONObject();
            try
            {
                json.put("scmId", schema.getScmId());
                json.put("scmName", schema.getScmName());
                jsonArray.put(json);
            }
            catch (JSONException e)
            {
                continue;
            }
        }
        String result = jsonArray.toString();
        return getJsonStringResolution(result);
    }

    @HandlesEvent("getschemaouteditingschoollistjson")
    public Resolution getSchemaOutEditingSchoolListJson()
    {
        // 调用该方法前都进行了session是否为空判断，这边不用再判断
        logRequest();
        JSONArray jsonArray = new JSONArray();

        schemaOutEditingSchoolList = cmService.getSchemaOutSchool(editingSchool.getSchoolId());
        for (Scm schema : schemaOutEditingSchoolList)
        {
            JSONObject json = new JSONObject();
            try
            {
                json.put("scmId", schema.getScmId());
                json.put("scmName", schema.getScmName());
                jsonArray.put(json);
            }
            catch (JSONException e)
            {
                continue;
            }
        }
        String result = jsonArray.toString();
        return getJsonStringResolution(result);
    }

    @HandlesEvent("getmastersinfoview")
    public Resolution getMastersInfoView()
    {
        // 调用该方法前都进行了session是否为空判断，这边不用再判断
        logRequest();
        mastersInEditingSchoolList = cmService.getSchoolUsers(editingSchool.getSchoolId(),
                Constants.UserRole.YJ_MASTER);
        return new ForwardResolution(ADMIN_SCHOOL_EDIT_SCHOOL_MASTERS_INFO);
    }

    @HandlesEvent("deleteuser")
    public Resolution deleteUser()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        int userId = getParamInt("userId", -1);

        try
        {
            cmService.deleteUser(userId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("getteachersinfoview")
    public Resolution getTeachersInfoView()
    {
        // 调用该方法前都进行了session是否为空判断，这边不用再判断
        logRequest();
        teachersInEditingSchoolList = cmService.getSchoolUsers(editingSchool.getSchoolId(),
                Constants.UserRole.YJ_TEACHER);
        return new ForwardResolution(ADMIN_SCHOOL_EDIT_SCHOOL_TEACHERS_INFO);
    }

    // 幼教这边对应的是幼教家长
    @HandlesEvent("getstudentsinfoview")
    public Resolution getStudentsInfoView()
    {
        // 调用该方法前都进行了session是否为空判断，这边不用再判断
        logRequest();
        studentsInEditingSchoolList = cmService.getSchoolUsers(editingSchool.getSchoolId(),
                Constants.UserRole.YJ_PARENT);
        return new ForwardResolution(ADMIN_SCHOOL_EDIT_SCHOOL_STUDENTS_INFO);
    }

    @HandlesEvent("downuserinfoexc")
    public Resolution downloadStudentsInfoExcel()
    {
        logRequest();
        int userRole = getParamInt("userRole", -1);

        if (!sessionIsValid() || userRole == -1)
        {
            return getStringTimeoutResolution();
        }

        try
        {
            InputStream is = null;
            String fileName = "";
            if (userRole == Constants.UserRole.YJ_PARENT)
            {
                if (studentsInEditingSchoolList == null || studentsInEditingSchoolList.size() == 0)
                {
                    throw new Exception();
                }
                fileName = editingSchool.getSchoolName() + "-家长信息表.xls";
                is = GenerateExcelUtils.generateExcelStream(studentsInEditingSchoolList);
            }
            else if (userRole == Constants.UserRole.YJ_TEACHER)
            {
                if (teachersInEditingSchoolList == null || teachersInEditingSchoolList.size() == 0)
                {
                    throw new Exception();
                }
                fileName = editingSchool.getSchoolName() + "-老师信息表.xls";
                is = GenerateExcelUtils.generateExcelStream(teachersInEditingSchoolList);
            }

            if (is != null)
            {
                return getExcelResolution(is, fileName);
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjAdmin();
    }

}
