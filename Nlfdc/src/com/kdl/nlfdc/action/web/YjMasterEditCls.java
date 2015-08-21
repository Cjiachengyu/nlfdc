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
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.YearRange;

@SessionScope
@UrlBinding("/yjmastereditclassaction")
public class YjMasterEditCls extends AbstractActionBean
{
    private static final long serialVersionUID = 3339859300873476870L;

    protected static final String MASTER_EDITCLASS_MAIN = "/WEB-INF/jsp/yjmaster/MasterEditClass.jsp";
    protected static final String MASTER_STUDENTS_LISTVIEW = "/WEB-INF/jsp/yjmaster/StudentListViewOfClass.jsp";
    protected static final String MASTER_TEACHERS_LISTVIEW = "/WEB-INF/jsp/yjmaster/TeacherListViewOfClass.jsp";

    // 年份信息，用于设置修改班级入学年份的范围

    protected int schoolId;
    // 当前编辑班级的classId
    protected int clsId;
    // 不在班级的用户按classId分类
    protected int outChoosedClassId;
    protected int outChoosedClassId2;
    // 该学校除了当前班级之外的所有班级id
    protected List<Cls> outClassInfos = new ArrayList<Cls>();

    protected Cls classInfo;
    protected YearRange year = new YearRange();

    protected List<User> teachersIn = new ArrayList<User>();
    protected List<User> teachersOut = new ArrayList<User>();
    protected List<User> studentsIn = new ArrayList<User>();
    protected List<User> studentsOut = new ArrayList<User>();

    public int getOutChoosedClassId2()
    {
        return outChoosedClassId2;
    }

    public int getOutChoosedClassId()
    {
        return outChoosedClassId;
    }

    public List<Cls> getOutClassInfos()
    {
        return outClassInfos;
    }

    public List<User> getTeachersIn()
    {
        return teachersIn;
    }

    public List<User> getTeachersOut()
    {
        return teachersOut;
    }

    public List<User> getStudentsIn()
    {
        return studentsIn;
    }

    public List<User> getStudentsOut()
    {
        return studentsOut;
    }

    public Cls getClassInfo()
    {
        return classInfo;
    }

    public int getThisYear()
    {
        return year.getThisYear();
    }

    public int getMinYear()
    {
        return year.getMinYear();
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("editclass")
    public Resolution editClass()
    {
        logRequest();
        validateSession();

        clsId = getParamInt("clsId");
        int schoolIdParam = getParamInt("schoolId", -1);

        classInfo = cmService.getClassInfo(clsId);

        School school = (School) getCurrentSession().getAttribute("school");

        // 管理员端复用该页面时传一个schoolId过来，设置Session中的school，因为管理员是没有schoolId的
        if (schoolIdParam != -1)
        {
            school = cmService.getSchool(schoolIdParam);
            setSessionAttr("school", school);
        }

        if (!sessionIsValid() || classInfo == null || school == null)
        {
            return getYjLogoutResolution();
        }

        year.initData(school);

        teachersIn.clear();
        teachersOut.clear();
        studentsIn.clear();
        studentsOut.clear();

        teachersIn = cmService.getUsersInClassByRole(clsId, Constants.UserRole.YJ_TEACHER);
        studentsIn = cmService.getUsersInClassByRole(clsId, Constants.UserRole.YJ_PARENT);

        // 查出该学校所有学生和老师
        schoolId = school.getSchoolId();
        outChoosedClassId = 0;
        outChoosedClassId2 = 0;
        outClassInfos = cmService.getOtherClassListOfASchool(schoolId, clsId);

        teachersOut = cmService.getUsersOutOfClassByRole(schoolId, clsId, outChoosedClassId,
                Constants.UserRole.YJ_TEACHER);
        studentsOut = cmService.getUsersOutOfClassByRole(schoolId, clsId, outChoosedClassId2,
                Constants.UserRole.YJ_PARENT);

        return new ForwardResolution(MASTER_EDITCLASS_MAIN);
    }

    @HandlesEvent("searchusersbyname")
    public Resolution searchUsersByName()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        String searchName = getParam("userName", "");
        // 搜索类型 1：老师 2：学生
        int type = getParamInt("type", 0);

        if (type == 0)
        {
            return getStringResolution("failed");
        }
        else
        {
            if (type == 1)
            {
                teachersOut = cmService.getUsersOfSchoolBySearchName(schoolId, clsId, outChoosedClassId,
                        searchName,
                        Constants.UserRole.YJ_TEACHER);
            }
            if (type == 2)
            {
                studentsOut = cmService.getUsersOfSchoolBySearchName(schoolId, clsId, outChoosedClassId2,
                        searchName,
                        Constants.UserRole.YJ_PARENT);
            }
            return getStringResolution("ok");
        }
    }

    // 把不在班级中的用户添加到班级中
    @HandlesEvent("addusertouserclass")
    public Resolution addUserToUserClass()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        int userId = getParamInt("userId", -1);
        int isTeacher = getParamInt("isTeacher", -1);

        if (userId == -1 || isTeacher == -1)
        {
            return getStringResolution("failed");
        }

        if (isTeacher == 1)
        {
            return doAddUserToClass(userId, teachersOut, teachersIn);
        }
        else
        {
            return doAddUserToClass(userId, studentsOut, studentsIn);
        }
    }

    // 把用户从班级中移出
    @HandlesEvent("removeuserfromuserclass")
    public Resolution removeUserFromUserClass()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int userId = getParamInt("userId", -1);
        if (userId == -1)
        {
            getStringResolution("failed");
        }

        User deleteUser = cmService.getUser(userId);
        try
        {
            cmService.deleteUserClass(userId, clsId);
        }
        catch (Exception e)
        {
            return getStringResolution("failed");
        }

        if (deleteUser.getUserRole() == Constants.UserRole.YJ_TEACHER || deleteUser.getUserRole() == Constants.UserRole.YJ_TEACHER)
        {
            filterUser(teachersIn, teachersOut, userId, deleteUser);
        }
        else
        {
            filterUser(studentsIn, studentsOut, userId, deleteUser);
        }
        return getStringResolution("ok");
    }

    @HandlesEvent("getstudentslistview")
    public Resolution getStudentsListView()
    {
        logRequest();
        return new ForwardResolution(MASTER_STUDENTS_LISTVIEW);
    }

    @HandlesEvent("getteacherslistview")
    public Resolution getTeachersListView()
    {
        logRequest();
        return new ForwardResolution(MASTER_TEACHERS_LISTVIEW);
    }

    @HandlesEvent("changeclassinfo")
    public Resolution changeClassInfo()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String clsName = getParam("clsName", null);
        int enterYear = getParamInt("enterYear", 0);
        if (Utils.stringEmpty(clsName) || clsName.length() > Constants.MaxLength.CLASS_NAME)
        {
            return getStringResolution("clsNameLength");
        }

        if (enterYear == 0)
        {
            return getStringResolution("clsEnterYear");
        }

        try
        {
            classInfo.setClsName(clsName);
            classInfo.setEntranceYear(enterYear);
            cmService.updateClass(classInfo);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("resetuserpassword")
    public Resolution resetUserPassword()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int userId = getParamInt("userId", 0);
        User user = cmService.getUser(userId);
        if (user != null)
        {
            user.setPassword(Constants.User.DEFAULT_PASSWORD);
            try
            {
                cmService.updateUser(user);
                return getStringResolution("ok");
            }
            catch (Exception e)
            {
                return getStringResolution("failed");
            }    
        }
        return getStringResolution("failed");
    }

    @HandlesEvent("reselectclass")
    public Resolution reselectClass()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int selectedClassId = getParamInt("selectedClassId", -1);
        // 1--老师； 2--学生
        int type = getParamInt("type", 0);
        if (type == 0 || selectedClassId == -1)
        {
            return getStringResolution("failed");
        }
        else
        {
            if (type == 1)
            {
                outChoosedClassId = selectedClassId;
                teachersOut = cmService.getUsersOutOfClassByRole(schoolId, clsId, selectedClassId,
                        Constants.UserRole.YJ_TEACHER);
                return getStringResolution("ok");
            }
            else
            {
                outChoosedClassId2 = selectedClassId;
                studentsOut = cmService.getUsersOutOfClassByRole(schoolId, clsId, selectedClassId,
                        Constants.UserRole.YJ_PARENT);
                return getStringResolution("ok");
            }
        }
    }

    @HandlesEvent("deleteclass")
    public Resolution deleteClass()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        try
        {
            cmService.deleteClass(clsId);
            // 这边不能对执行sql的影响行数进行判断，因为该班级中可能没有用户，返回结果为0，但是的确是正确执行删除了
            cmService.deleteUserClassByClassId(clsId);
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

    // private
    // --------------------------------------------------------------------------------
    private void filterUser(List<User> list1, List<User> list2, int userId, User deleteUser)
    {
        for (int j = 0; j < list1.size(); j++)
        {
            if (list1.get(j).getUserId() == userId)
            {
                list1.remove(j);
                break;
            }
        }
        list2.add(deleteUser);
    }

    private Resolution doAddUserToClass(int userId, List<User> outList, List<User> inList)
    {
        for (int i = 0; i < outList.size(); i++)
        {
            if (outList.get(i).getUserId() == userId)
            {
                User addUser = outList.get(i);

                try
                {
                    cmService.insertUserClass(userId, clsId);
                }
                catch (Exception e)
                {
                    return getStringResolution("failed");
                }
                outList.remove(i);
                inList.add(addUser);
                return getStringResolution("ok");
            }
        }
        return getStringResolution("failed");
    }

}
