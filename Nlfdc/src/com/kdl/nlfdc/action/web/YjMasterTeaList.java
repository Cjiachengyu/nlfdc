package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.ClsAsm;
import com.kdl.nlfdc.domain.Grade;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.TeaBook;
import com.kdl.nlfdc.domain.TeaSubject;
import com.kdl.nlfdc.domain.TeacherInfo;
import com.kdl.nlfdc.domain.TeacherInfoInClass;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

@SessionScope
@UrlBinding("/yjmasterteacheraction")
public class YjMasterTeaList extends AbstractMasterSelectSubject
{
    private static final long serialVersionUID = 3339859300873476879L;

    private static final String MASTER_TEACHERINFO_MAIN           = "/WEB-INF/jsp/yjmaster/MasterTeacherInfo.jsp";
    private static final String MASTER_TEACHER_LISTVIEW           = "/WEB-INF/jsp/yjmaster/TeacherListView.jsp";
    private static final String MASTER_TEACHERINFO_MAIN_BY_CLASS  = "/WEB-INF/jsp/yjmaster/MasterTeacherInfoByClass.jsp";
    private static final String MASTER_TEACHER_LISTVIEW_BY_CLASS  = "/WEB-INF/jsp/yjmaster/TeacherListViewByClass.jsp";

    // 快速设置老师的弹出层
    private static final String MASTER_TEACHER_SUBJECTS_LISTVIEW  = "/WEB-INF/jsp/yjmaster/TeacherSubjectListView.jsp";
    private static final String MASTER_TEACHER_CLASSES_LISTVIEW   = "/WEB-INF/jsp/yjmaster/TeacherClassListView.jsp";
    private static final String MASTER_TEACHER_TEXTBOOKS_LISTVIEW = "/WEB-INF/jsp/yjmaster/TeacherTextbookListView.jsp";

    // 按班级查看还是按老师查看
    private static final int CHECK_BY_CLASS = 1;
    private static final int CHECK_BY_TEACHER = 2;
    
    // private state
    // --------------------------------------------------------------------------------
    private int schoolId;

    // page state
    // --------------------------------------------------------------------------------
    private int checkTeacherType;
    private List<TeacherInfo> teacherList;                          // 老师列表
    private List<Integer> enterSchoolYearList;                      // 按班级查看老师年份列表
    private List<Grade> schoolGradesList = new ArrayList<Grade>();
    private List<TeacherInfoInClass>[] teacherInfoInClassListArray; // 按班级查看老师班级列表
    // 快速设置老师
    private List<Subject> userSubjectList;                  // 快速设置老师课本，列出的课本按科目分类
    private List<Book>[] textbooks;                     // 老师所在学校的所有分类后的课本
    private List<Cls>[] classes;                      // 老师所在学校的分类后的班级

    public int getCheckTeacherType()
    {
        return checkTeacherType;
    }

    public List<TeacherInfoInClass>[] getTeacherInfoInClassListArray()
    {
        return teacherInfoInClassListArray;
    }

    public List<Subject> getUserSubjectList()
    {
        return userSubjectList;
    }

    public List<Cls>[] getClasses()
    {
        return classes;
    }

    public List<Book>[] getTextbooks()
    {
        return textbooks;
    }

    public List<TeacherInfo> getTeacherList()
    {
        return teacherList;
    }
    
    public List<Grade> getSchoolGradesList()
    {
        return schoolGradesList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("masterteacherinfo")
    public Resolution masterTeacherInfo()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        checkTeacherType = getParamInt("checkType", CHECK_BY_TEACHER);
        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_MASTER_TEACHER_INFO);

        schoolId = ((School) getCurrentSession().getAttribute("school")).getSchoolId();
        initMasterSubject();

        refreshTeacherList();

        return getDefaultMainPage();
    }

    @HandlesEvent("getteacherlistview")
    public Resolution getTeacherListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return getDefaultListView();
    }

    @HandlesEvent("getteachersubjectslistview")
    public Resolution getTeacherSubjectsListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int teaId = getParamInt("teaId", -1);
        if (teaId == -1)
        {
            return getStringResolution("<h2 align='center'>出现错误！</h2>");
        }
        List<Subject> teacherSubjectList = cmService.getTeacherSubjects(teaId);

        for (Subject subj : subjects)
        {
            subj.setIsSelected(false);
            for (Subject userSubj : teacherSubjectList)
            {
                if (userSubj.getSubjectId() == subj.getSubjectId())
                {
                    subj.setIsSelected(true);
                    break;
                }
            }
        }

        return new ForwardResolution(MASTER_TEACHER_SUBJECTS_LISTVIEW);
    }

    @HandlesEvent("getteacherclasseslistview")
    public Resolution getTeacherclassesListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int teaId = getParamInt("teaId", -1);
        if (teaId == -1)
        {
            return getStringResolution("<h2 align='center'>出现错误！</h2>");
        }

        classes = new ArrayList[enterSchoolYearList.size()];
        
        // 该老师的任课班级id
        List<Integer> userClassIds = new ArrayList<Integer>();
        List<Cls> userClassList = cmService.getClassListOfAUser(teaId);
        for (Cls uc : userClassList)
        {
            userClassIds.add(uc.getClsId());
        }

        for (int i = 0; i < enterSchoolYearList.size(); i++)
        {
            classes[i] = cmService.getClassByGrade(
                    schoolId, (int) enterSchoolYearList.get(i));

            for (Cls classInfo : classes[i])
            {
                if (userClassIds.contains(classInfo.getClsId()))
                {
                    classInfo.setIsSelected(true);
                }
            }
        }

        return new ForwardResolution(MASTER_TEACHER_CLASSES_LISTVIEW);
    }

    @HandlesEvent("getaddtextbookview")
    public Resolution getAddTextbookView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int teaId = getParamInt("teaId", -1);
        if (teaId == -1)
        {
            return getStringResolution("<h2 align='center'>出现错误！</h2>");
        }

        userSubjectList = cmService.getTeacherSubjects(teaId);

        textbooks = new ArrayList[userSubjectList.size()];
        for (int i = 0; i < userSubjectList.size(); i++)
        {
            textbooks[i] = cmService.getTextbookBySchoolId_SubjectId(
                    userSubjectList.get(i).getSubjectId(), schoolId);
        }

        // 老师没有收藏的课本id
        List<Integer> textbookIds = new ArrayList<Integer>();
        List<Book> notSelectedTextbooks = cmService.getTextbookForTeacherExceptFav(schoolId, teaId);
        for (Book tb : notSelectedTextbooks)
        {
            textbookIds.add(tb.getBookId());
        }

        for (int i = 0; i < userSubjectList.size(); i++)
        {
            for (Book tb : textbooks[i])
            {
                if (!textbookIds.contains(tb.getBookId()))
                {
                    tb.setIsSelected(true);
                }
            }
        }

        return new ForwardResolution(MASTER_TEACHER_TEXTBOOKS_LISTVIEW);
    }

    @HandlesEvent("saveteachersubjects")
    public Resolution saveTeacherSubjects()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int teaId = getParamInt("teaId", -1);
        String subjectIds = getParam("subjectIds", "null");
        if (teaId == -1 || subjectIds.equals("null"))
        {
            return getStringResolution("dataError"); // 参数不合法
        }

        try
        {
            doSaveTeacherSubjects(teaId, subjectIds);

            refreshTeacherList();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("saveteacherclasses")
    public Resolution saveTeacherClasses()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int teaId = getParamInt("teaId", -1);
        String classIds = getParam("classIds", "null");
        if (teaId == -1 || classIds.equals("null"))
        {
            return getStringResolution("dataError"); // 参数不合法
        }

        try
        {
            doSaveTeacherClasses(teaId, classIds);

            refreshTeacherList();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("saveteachertextbooks")
    public Resolution saveTeacherTextbooks()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String textbookIds = getParam("textbookIds", "null");
        int teaId = getParamInt("teaId", -1);
        if (teaId == -1 || textbookIds.equals("null"))
        {
            return getStringResolution("dataError"); // 参数不合法
        }

        try
        {
            daSaveTeacherTextbooks(textbookIds, teaId);

            refreshTeacherList();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }


    // private
    // --------------------------------------------------------------------------------
    private void refreshTeacherList()
    {
        // 获取老师列表
        teacherList = new ArrayList<TeacherInfo>();
        // 分页
        List<User> teacherUserList = cmService.getUserIdListByPage(schoolId, subjectId, Constants.UserRole.YJ_TEACHER, 0, 1000);
        for (User tea : teacherUserList)
        {
            teacherList.add(buildTeacherInfo(tea));
        }
        Collections.sort(teacherList, comparator);

        enterSchoolYearList = cmService.getClassEnterYearList(schoolId);
        
        schoolGradesList = cmService.generateSchoolGrade(enterSchoolYearList);
        
        if (checkTeacherType == CHECK_BY_CLASS)
        {
            teacherInfoInClassListArray = new ArrayList[enterSchoolYearList.size()];
            for (int i = 0; i < enterSchoolYearList.size(); i++)
            {
                teacherInfoInClassListArray[i] = new ArrayList<TeacherInfoInClass>();
            }

            for (TeacherInfo tea : teacherList)
            {
                List<ClsAsm> teaClsAsmList =
                        cmService.getClassAssignmentOfATeacherForMasterStatistics(tea.getUserId(), subjectId);

                float avrCaAvrScore = 0;
                float avrCaCorrectedRatio = 0;
                for (ClsAsm ca : teaClsAsmList)
                {
                    avrCaAvrScore += ca.getStandardAvrScore();
                    avrCaCorrectedRatio += ca.getCorrectRatio();
                }
                if (teaClsAsmList.size() > 0)
                {
                    avrCaAvrScore = avrCaAvrScore / teaClsAsmList.size();
                    avrCaCorrectedRatio = avrCaCorrectedRatio / teaClsAsmList.size();
                }
                tea.setAvrClassAssignmentAvrScore((float) Utils.roundDouble(avrCaAvrScore, 1));
                tea.setAvrClassAssignmentCorrectedRatio((float) Utils.roundDouble(avrCaCorrectedRatio, 1));

                for (Cls cls : tea.getClassList())
                {
                    addTeacherInClass(tea, cls);
                }
            }

            for (int i = 0; i < enterSchoolYearList.size(); i++)
            {
                List<TeacherInfoInClass> teaClsList = teacherInfoInClassListArray[i];
                Collections.sort(teaClsList, new Comparator<TeacherInfoInClass>()
                {
                    @Override
                    public int compare(TeacherInfoInClass o1, TeacherInfoInClass o2)
                    {
                        if (o1.getClassInfo().getClsId() != o2.getClassInfo().getClsId())
                        {
                            return o1.getClassInfo().getClsName().compareTo(o2.getClassInfo().getClsName());
                        }
                        else
                        {
                            return o1.getTeacherInfo().getUserId() - o2.getTeacherInfo().getUserId();
                        }
                    }
                });
            }
        }
    }

    private void addTeacherInClass(TeacherInfo tea, Cls cls)
    {
        TeacherInfoInClass teaCls = new TeacherInfoInClass();
        teaCls.setTeacherInfo(tea);
        teaCls.setClassInfo(cls);

        for (int i = 0; i < enterSchoolYearList.size(); i++)
        {
            int year = enterSchoolYearList.get(i);
            if (year == cls.getEntranceYear())
            {
                teacherInfoInClassListArray[i].add(teaCls);
                break;
            }
        }
    }

    private TeacherInfo buildTeacherInfo(User tea)
    {
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setUserId(tea.getUserId());
        teacherInfo.setTeacherName(tea.getUserName());

        // 设置老师科目
        StringBuilder teacherSubjectStrBuilder = new StringBuilder();
        List<Subject> teacherSubjectList = cmService.getTeacherSubjects(tea.getUserId());
        for (Subject subject : teacherSubjectList)
        {
            teacherSubjectStrBuilder.append(subject.getSubjectName() + " , ");
        }
        teacherInfo.setSubject(Utils.trimEnd(teacherSubjectStrBuilder.toString(), 3));

        // 老师活跃度
        int resourceCount = cmService.getResourceCountOfTeacher(tea.getUserId(), subjectId);
        int asmCount = cmService.getTeacherAssignmentCount(tea.getUserId(), subjectId);
        teacherInfo.setNumberOfResource(resourceCount);
        teacherInfo.setNumberOfAssignment(asmCount);
        teacherInfo.setTotalNumber(asmCount + resourceCount);

        // 老师教哪些班
        StringBuilder teacherClassStrBuilder = new StringBuilder();
        List<Cls> cList = cmService.getClassListOfAUser(tea.getUserId());
        for (Cls c : cList)
        {
            teacherClassStrBuilder.append(c.getClsName() + " , ");
        }
        teacherInfo.setClasses(Utils.trimEnd(teacherClassStrBuilder.toString(), 3));
        teacherInfo.setClassList(cList);

        return teacherInfo;
    }

    Comparator<TeacherInfo> comparator = new Comparator<TeacherInfo>()
    {
        public int compare(TeacherInfo s1, TeacherInfo s2)
        {
            if (s1.getTotalNumber() != s2.getTotalNumber())
            {
                return s2.getTotalNumber() - s1.getTotalNumber();
            }
            else
            {
                return s2.getNumberOfResource() - s1.getNumberOfResource();
            }
        }
    };

    private void doSaveTeacherSubjects(int teaId, String subjectIds) throws SqlAffectedCountException
    {
        // 先把该老师的所有userSubject都删除，再把这次选中的subject重新插入
        cmService.deleteUserSubjectByUserId(teaId);

        if (!subjectIds.equals("nochecked"))
        {
            subjectIds = Utils.trimEnd(subjectIds, 1);
            String[] subjectIdStr = subjectIds.split(",");
            for (String subjectId : subjectIdStr)
            {
                TeaSubject userSub = new TeaSubject();
                userSub.setTeaId(teaId);
                userSub.setSubjectId(Integer.parseInt(subjectId));
                cmService.insertUserSubject(userSub);
            }
        }
    }

    private void doSaveTeacherClasses(int teaId, String classIds) throws SqlAffectedCountException
    {
        // 先把该老师的所有userClass都删除，再把这次选中的Class重新插入
        cmService.deleteUserClassByUserId(teaId);

        if (!classIds.equals("nochecked"))
        {
            classIds = Utils.trimEnd(classIds, 1);
            String[] classIdStr = classIds.split(",");

            for (String clsId : classIdStr)
            {
                cmService.insertUserClass(teaId, Integer.parseInt(clsId));
            }
        }
    }

    private void daSaveTeacherTextbooks(String textbookIds, int teaId) throws SqlAffectedCountException
    {
        // 先把该老师的所有teacherTextbook删除,然后再重新插入
        cmService.deleteTeacterTextbookAllByTeacherId(teaId);

        if (!textbookIds.equals("nochecked"))
        {
            textbookIds = Utils.trimEnd(textbookIds, 1);
            String[] textbookIdStr = textbookIds.split(",");

            for (String bookId : textbookIdStr)
            {
                TeaBook ttb = new TeaBook();
                ttb.setTeaId(teaId);
                ttb.setBookId(Integer.parseInt(bookId));
                cmService.insertTeacherTextbook(ttb);
            }
        }

        // 判断是否需要清除userCache
        User teacher = cmService.getUser(teaId);
        int cacheTextbookId = teacher.getCacheBookId();
        if (cacheTextbookId != 0)
        {
            List<TeaBook> teacherTextbooks = cmService.getTeacherTextbook(
                    cacheTextbookId, teaId);
            if (teacherTextbooks.size() == 0)
            {
                // 课本已经被删除，需要清空userCache
                teacher.setCacheSubjectId(0);
                teacher.setCacheBookId(0);
                teacher.setCacheChapterId(0);
                teacher.setCacheSectionId(0);
                cmService.updateSchemaCache(teacher);
            }
        }
    }

    private Resolution getDefaultMainPage()
    {
        switch (checkTeacherType)
        {
        case CHECK_BY_CLASS:
            return new ForwardResolution(MASTER_TEACHERINFO_MAIN_BY_CLASS);

        case CHECK_BY_TEACHER:
            return new ForwardResolution(MASTER_TEACHERINFO_MAIN);

        default:
            return new ForwardResolution(MASTER_TEACHERINFO_MAIN_BY_CLASS);
        }
    }

    private Resolution getDefaultListView()
    {
        switch (checkTeacherType)
        {
        case CHECK_BY_CLASS:
            return new ForwardResolution(MASTER_TEACHER_LISTVIEW_BY_CLASS);

        case CHECK_BY_TEACHER:
            return new ForwardResolution(MASTER_TEACHER_LISTVIEW);

        default:
            return new ForwardResolution(MASTER_TEACHER_LISTVIEW_BY_CLASS);
        }
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected Resolution selectSubjectReturnList()
    {
        refreshTeacherList();
        return getDefaultListView();
    }

    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjMaster();
    }
}
