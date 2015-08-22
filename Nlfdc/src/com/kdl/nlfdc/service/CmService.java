package com.kdl.nlfdc.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.FileBean;

import org.apache.tomcat.util.json.JSONArray;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.TextbookSelectorClassify;
import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.domain.AdminMenu;
import com.kdl.nlfdc.domain.Asm;
import com.kdl.nlfdc.domain.AsmQue;
import com.kdl.nlfdc.domain.AsmRes;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Chapter;
import com.kdl.nlfdc.domain.City;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.ClsAsm;
import com.kdl.nlfdc.domain.FirstMenu;
import com.kdl.nlfdc.domain.FirstMenuWithSecondMenu;
import com.kdl.nlfdc.domain.Grade;
import com.kdl.nlfdc.domain.Login;
import com.kdl.nlfdc.domain.MachineLicense;
import com.kdl.nlfdc.domain.MasterStatisticsAssignment;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.Province;
import com.kdl.nlfdc.domain.Que;
import com.kdl.nlfdc.domain.Region;
import com.kdl.nlfdc.domain.Remark;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;
import com.kdl.nlfdc.domain.School;
import com.kdl.nlfdc.domain.SchoolScm;
import com.kdl.nlfdc.domain.Scm;
import com.kdl.nlfdc.domain.SecondMenu;
import com.kdl.nlfdc.domain.Section;
import com.kdl.nlfdc.domain.StuAnswerItem;
import com.kdl.nlfdc.domain.StuAsm;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.TeaBook;
import com.kdl.nlfdc.domain.TeaSubject;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.UserChatToken;
import com.kdl.nlfdc.domain.UserNotification;
import com.kdl.nlfdc.domain.VisitCount;
import com.kdl.nlfdc.exception.DataInconsistException;
import com.kdl.nlfdc.exception.SqlAffectedCountException;
import com.kdl.nlfdc.persistence.AsmMapper;
import com.kdl.nlfdc.persistence.BasicMapper;
import com.kdl.nlfdc.persistence.QueMapper;
import com.kdl.nlfdc.persistence.ResMapper;
import com.sun.swing.internal.plaf.basic.resources.basic;

@Service
public class CmService implements Serializable
{
    private static final long serialVersionUID = -5030426866333509214L;

    @Autowired
    protected BasicMapper basicMapper;
    @Autowired
    protected AsmMapper asmMapper;
    @Autowired
    protected QueMapper queMapper;
    @Autowired
    protected ResMapper resMapper;

    
    public VisitCount getVisitCount(int day)
    {
        return basicMapper.getVisitCount(day);
    }
   
    public int getSumVisitCount()
    {
        return basicMapper.getSumVisitCount();
    }
    
    public void insertVisitCount(VisitCount visitCount)
    {
        basicMapper.insertVisitCount(visitCount);
    }
    
    public void updateVisitCount(VisitCount visitCount)
    {
        basicMapper.updateVisitCount(visitCount);
    }
    
    public FirstMenu getFirstMenu(int firstMenuId)
    {
        return basicMapper.getFirstMenu(firstMenuId);
    }
    
    public List<FirstMenu> getAllFirstMenu()
    {
        return basicMapper.getAllFirstMenu();
    }
    
    public List<FirstMenuWithSecondMenu> getAllFirstMenuWithSecondMenu()
    {
        return basicMapper.getAllFirstMenuWithSecondMenu();
    }
    
    public SecondMenu getSecondMenu(int secondMenuId)
    {
        return basicMapper.getSecondMenu(secondMenuId);
    }
    
    public List<SecondMenu> getSecondMenuByFirstMenuId(int firstMenuId)
    {
        return basicMapper.getSecondMenuByFirstMenuId(firstMenuId);
    }
    
    public List<AdminMenu> getAdminMenus(int adminId)
    {
        return basicMapper.getAdminMenus(adminId);
    }
    
    public int getAdminNotificationCount(int firstMenuId, int secondMenuId)
    {
        return basicMapper.getAdminNotificationCount(firstMenuId, secondMenuId);
    }
    
    public List<Notification> getAdminNotificationList(int firstMenuId, int secondMenuId, int limitBegin, int pageSize)
    {
        return basicMapper.getAdminNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);
    }
    
    
    
    
    
    
    
    
    
    
    // log
    // --------------------------------------------------------------------------------
    private static final SimpleDateFormat logDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private synchronized void log(Object logText)
    {
        FileWriter filerWriter = null;
        BufferedWriter bufWriter = null;
        try
        {
            String toLog = String.valueOf(logText).replaceAll("[\r\n]", " ");

            StringBuffer logBuffer = new StringBuffer();
            logBuffer.append(logDateTimeFormat.format(new Date()));
            logBuffer.append(" ");
            logBuffer.append(new DecimalFormat("000000").format(Thread.currentThread().getId()));
            // logBuffer.append(" ");
            // logBuffer.append(getCurrentUserIp());
            logBuffer.append(" -> ");
            logBuffer.append(toLog);
            logBuffer.append("\n");

            String logFileName = Utils.getFullDateString(Utils.currentSeconds()) + ".serivce.log";
            String logFilePath = Constants.PATH_FILE + "log/" + logFileName;
            Utils.makeSureFileExist(logFilePath);

            File file = new File(logFilePath);
            filerWriter = new FileWriter(file, true);
            bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(logBuffer.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Utils.safeClose(bufWriter);
            Utils.safeClose(filerWriter);
        }
    }


    // 作业
    // --------------------------------------------------------------------------------
    public Asm getAsm(int asmId)
    {
        return asmMapper.getAsm(asmId);
    }

    public void insertAsm(Asm asm)
    {
        asmMapper.insertAsm(asm);
    }

    public void insertAsmRes(int asmId, int asmQueId, int resId, int resNum)
    {
        asmMapper.insertAsmRes(asmId, asmQueId, resId, resNum);
    }

    public List<AsmRes> getAsmResList(int asmId)
    {
        return asmMapper.getAsmResList(asmId);
    }

    public void updateAsm(Asm asm)
    {
        asmMapper.updateAsm(asm);
    }

    public int deleteAsm(int asmId)
    {
        return asmMapper.deleteAsm(asmId);
    }

    public void updateClsAsm(ClsAsm clsAsm)
    {
        // 只更新classAssignment一张表的数据
        asmMapper.updateClsAsm(clsAsm);
    }

    public void insertClsAsm(ClsAsm clsAsm)
    {
        // 出一份作业时会插入一条数据，记录这份作业在这个班的情况
        asmMapper.insertClsAsm(clsAsm);
    }

    public ClsAsm getClsAsm(int asmId, int clsId)
    {
        // 获取这份作业在这个班级的作答情况，比如平均分，批改人数
        return asmMapper.getClsAsm(asmId, clsId);
    }

    public List<ClsAsm> getClsAsmOfAnAsm(int asmId)
    {
        // 获取一份作业已经在哪些班级发布
        return asmMapper.getClsAsmOfAnAsm(asmId);
    }

    public void updateStuAsm(StuAsm stuAsm)
    {
        // 只更新studentAssignment一张表的数据
        asmMapper.updateStuAsm(stuAsm);
    }

    public void insertStuAsmList(List<StuAsm> stuAsmList)
    {
        asmMapper.insertStuAsmList(stuAsmList);
    }

    public StuAsm getStuAsm(int asmId, int stuId, int clsId)
    {
        // 获取一个学生对这份作业的作答情况，比如这个学生得了多少分，排第几名
        return asmMapper.getStuAsm(asmId, stuId, clsId);
    }

    public List<StuAsm> getStuAsmOfAClass(int asmId, int clsId)
    {
        // 获取一份作业在一个班的所有学生的作答情况
        return asmMapper.getStuAsmOfAClass(asmId, clsId);
    }

    public List<Asm> getAsmListForYjTea(int teaId, int isDeleted, int bookId,
            int chapterId, int sectionId, int limitBegin, int pageSize)
    {
        return asmMapper.getAsmListForYjTea(teaId, isDeleted, bookId, chapterId,
                sectionId, limitBegin, pageSize);
    }

    public int getAsmListForYjTeaCount(int teaId, int isDeleted, int bookId,
            int chapterId, int sectionId)
    {
        return asmMapper.getAsmListForYjTeaCount(teaId, isDeleted, bookId,
                chapterId, sectionId);
    }

    public List<ClsAsm> getClsAsmListForTeaClient(int creatorId, int clsId)
    {
        return asmMapper.getClsAsmListForTeaClient(creatorId, clsId);
    }

    public List<StuAsm> getStuAsmListForStu(int stuId, int clsId, int asmStatusType, int startTime, int endTime,
            int subjectId, int bookId, int chapterId, int sectionId, int beginIndex, int pageSize)
    {
        return asmMapper.getStuAsmListForStu(stuId, clsId, asmStatusType, startTime, endTime,
                subjectId, bookId, chapterId, sectionId, beginIndex, pageSize);
    }

    public int getTeacherAssignmentCount(int teaId, int subjectId)
    {
        return asmMapper.getTeacherAssignmentCount(teaId, subjectId);
    }

    public int getSchoolAssignmentCount(int schoolId, int subjectId)
    {
        // 教研员使用
        return asmMapper.getSchoolAssignmentCount(schoolId, subjectId);
    }

    public List<MasterStatisticsAssignment> getTeacherAssignmentListByPage(int teaId, int subjectId,
            int limitBegin, int pageSize)
    {
        // add cjia 2015.1.6 校长端查看老师作业在各个班级发布情况
        return asmMapper.getTeacherAssignmentListByPage(teaId, subjectId, limitBegin, pageSize);
    }

    public int getClassAssignmentCount(int clsId, int subjectId)
    {
        // add cjia 2015.1.7 校长端查看班级中作业统计情况
        return asmMapper.getClassAssignmentCount(clsId, subjectId);
    }

    public List<ClsAsm> getClassAssignmentForMasterStatistics(int clsId, int subjectId)
    {
        // add cjia 2015.1.7 校长端查看班级中作业统计情况
        return asmMapper.getClassAssignmentForMasterStatistics(clsId, subjectId);
    }

    public List<ClsAsm> getClassAssignmentOfATeacherForMasterStatistics(int teaId, int subjectId)
    {
        return asmMapper.getClassAssignmentOfATeacherForMasterStatistics(teaId, subjectId);
    }

    public List<MasterStatisticsAssignment> getClassAssignmentListByPage(int clsId, int subjectId, int limitBegin,
            int pageSize)
    {
        // add cjia 2015.1.7 校长端查看班级中作业统计情况
        return asmMapper.getClassAssignmentListByPage(clsId, subjectId, limitBegin, pageSize);
    }

    public int getAsmNeedCorrectedCountInAllClass(int asmId)
    {
        // 幼教老师查看任务列表时获取每份任务在各个班级中待批改的总人数
        return asmMapper.getAsmNeedCorrectedCountInAllClass(asmId);
    }

    public List<StuAsm> getOverFinishTimeNeedCorrectStuAsm(int expireTime, int limitExpireTime)
    {
        return asmMapper.getOverFinishTimeNeedCorrectStuAsm(expireTime, limitExpireTime);
    }

    // 作业题目基本操作
    // --------------------------------------------------------------------------------
    public AsmQue getAsmQue(int asmQueId)
    {
        return asmMapper.getAsmQue(asmQueId);
    }

    public void insertAsmQue(AsmQue asmQue)
    {
        asmMapper.insertAsmQue(asmQue);
    }

    public AsmQue getAsmQueOfAnAsm(int asmId)
    {
        return asmMapper.getAsmQueOfAnAsm(asmId);
    }


    // 学生作答情况
    // --------------------------------------------------------------------------------
    public StuAnswerItem getStuAnswerItem(int asmId, int clsId, int stuId, int asmQueId, int answerNum)
    {
        return asmMapper.getStuAnswerItem(asmId, clsId, stuId, asmQueId, answerNum);
    }

    public List<StuAnswerItem> getStuAnswerItemList(int asmId, int clsId, int stuId)
    {
        return asmMapper.getStuAnswerItemList(asmId, clsId, stuId);
    }


    // 用户
    // --------------------------------------------------------------------------------
    
    public Admin getAdminByAdminId(int adminId)
    {
        return basicMapper.getAdminByAdminId(adminId);
    }
    
    public Admin getAdminByLoginName(String loginName)
    {
        return basicMapper.getAdminByLoginName(loginName);
    }
    
    
    
    
    public User getUser(int userId)
    {
        return basicMapper.getUser(userId);
    }

    public void deleteUser(int userId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteUser(userId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public User getUserByLoginInfo(String loginInfo)
    {
        User u = null;
        if (loginInfo.matches(Constants.Email.EMAIL_PATTERN))
        {
            u = basicMapper.getUserByEmailAndSchoolId(loginInfo, 0);
        }
        else if (loginInfo.matches(Constants.UserRole.LOGIN_ID_PATTERN))
        {
            u = basicMapper.getUserByLoginIdAndSchoolId(loginInfo, 0);
        }
        else if (loginInfo.matches(Constants.UserRole.CELLPHONE_PATTERN))
        {
            u = basicMapper.getUserByCellphoneAndSchoolId(loginInfo, 0);
        }
        else
        {
            u = basicMapper.getUserByLoginNameAndSchoolId(loginInfo, 0);
        }
        return u;
    }

    public User getUserByLoginInfoAndSchoolId(String loginInfo, int schoolId)
    {
        if (loginInfo.matches(Constants.Email.EMAIL_PATTERN))
        {
            return basicMapper.getUserByEmailAndSchoolId(loginInfo, schoolId);
        }
        else if (loginInfo.matches(Constants.UserRole.LOGIN_ID_PATTERN))
        {
            return basicMapper.getUserByLoginIdAndSchoolId(loginInfo, schoolId);
        }
        else if (loginInfo.matches(Constants.UserRole.CELLPHONE_PATTERN))
        {
            return basicMapper.getUserByCellphoneAndSchoolId(loginInfo, schoolId);
        }
        else
        {
            return basicMapper.getUserByLoginNameAndSchoolId(loginInfo, schoolId);
        }
    }

    public User getUserByOpenid(String openid)
    {
        return basicMapper.getUserByOpenid(openid);
    }

    public List<User> getUserByClassId(int clsId)
    {
        return basicMapper.getUserByClassId(clsId);
    }

    public void insertUserAndGetUserId(User user) throws SqlAffectedCountException
    {
        if (basicMapper.insertUserAndGetUserId(user) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public void updateUser(User user)
    {
        basicMapper.updateUser(user);
    }

    public void updateSchemaCache(User user)
    {
        basicMapper.updateSchemaCache(user);
    }

    public int getCountOfSchoolMember(int schoolId, int userRole, int subjectId)
    {
        return basicMapper.getCountOfSchoolMember(schoolId, userRole, subjectId);
    }

    public List<User> getUserIdListByPage(int schoolId, int subjectId, int userRole, int limitBegin, int pageSize)
    {
        return basicMapper.getUserIdListByPage(schoolId, subjectId, userRole, limitBegin, pageSize);
    }

    public List<User> getTeachersOfClass(int clsId, int subjectId)
    {
        return basicMapper.getTeachersOfClass(clsId, subjectId);
    }

    public List<User> getTeacherListForAStudent(int userId)
    {
        return basicMapper.getTeacherListForAStudent(userId);
    }

    public List<User> getAllStudentsOfAClass(int clsId)
    {
        return basicMapper.getAllStudentsOfAClass(clsId);
    }

    public List<User> getUsersOfSchoolBySearchName(int schoolId, int inClassId, int outClassId, String searchName,
            int userRole)
    {
        // 按用户名搜索
        return basicMapper.getUsersOfSchoolBySearchName(schoolId, inClassId, outClassId, searchName, userRole);
    }

    public void insertUserFindPassword(int userId, int time) throws SqlAffectedCountException
    {
        if (basicMapper.insertUserFindPassword(userId, time, (byte) 0) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public List<User> getAllUsersOfSchoolByUserRole(int schoolId, int userRole)
    {
        return basicMapper.getAllUsersOfSchoolByUserRole(schoolId, userRole);
    }


    // 登录
    // --------------------------------------------------------------------------------
    public Login getLogin(int userId)
    {
        return basicMapper.getLogin(userId);
    }

    public Login getLoginByTokenId(String tokenId)
    {
        return basicMapper.getLoginByTokenId(tokenId);
    }

    public void updateLogin(Login login)
    {
        basicMapper.updateLogin(login);
    }

    public List<Login> getLoginByClientId(String clientId)
    {
        return basicMapper.getLoginByClientId(clientId);
    }

    public void updateClientId(Login login)
    {
        basicMapper.updateClientId(login);
    }

    public void updateAndroidLogin(Login login)
    {
        basicMapper.updateAndroidLogin(login);
    }

    public void insertLogin(Login login)
    {
        basicMapper.insertLogin(login);
    }

    public void insertAndroidLogin(Login login)
    {
        basicMapper.insertAndroidLogin(login);
    }

    public void deleteLogin(int userId)
    {
        basicMapper.deleteLogin(userId);
    }


    // 班级
    // --------------------------------------------------------------------------------
    public int getCountOfClassMember(int clsId, int userRole)
    {
        return basicMapper.getCountOfClassMember(clsId, userRole);
    }

    public Cls getClassInfo(int clsId)
    {
        return basicMapper.getClassInfo(clsId);
    }

    public List<Integer> getClassEnterYearList(int schoolId)
    {
        return basicMapper.getClassEnterYearList(schoolId);
    }

    public int getSchoolClassCount(int schoolId)
    {
        return basicMapper.getSchoolClassCount(schoolId);
    }

    public List<Cls> getClassByGrade(int schoolId, int entranceYear)
    {
        return basicMapper.getClassByGrade(schoolId, entranceYear);
    }

    public List<Cls> getClassListOfAUser(int userId)
    {
        return basicMapper.getClassListOfAUser(userId);
    }

    public List<Cls> getOtherClassListOfASchool(int schoolId, int clsId)
    {
        return basicMapper.getOtherClassListOfASchool(schoolId, clsId);
    }

    public void updateClass(Cls classInfo)
    {
        basicMapper.updateClass(classInfo);
    }

    public void deleteClass(int clsId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteClass(clsId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    // 用户与班级的关系
    // --------------------------------------------------------------------------------
    public List<User> getUsersOutOfClassByRole(int schoolId, int inClassId, int outClassId, int userRole)
    {
        return basicMapper.getUsersOutOfClassByRole(schoolId, inClassId, outClassId, userRole);
    }

    public List<User> getUsersInClassByRole(int inClassId, int userRole)
    {
        return basicMapper.getUsersInClassByRole(inClassId, userRole);
    }

    public void insertUserClass(int userId, int clsId) throws SqlAffectedCountException
    {
        if (basicMapper.insertUserClass(userId, clsId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public void deleteUserClass(int userId, int clsId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteUserClass(userId, clsId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public int deleteUserClassByClassId(int clsId)
    {
        return basicMapper.deleteUserClassByClassId(clsId);
    }

    public int deleteUserClassByUserId(int userId)
    {
        return basicMapper.deleteUserClassByUserId(userId);
    }


    // 科目
    // --------------------------------------------------------------------------------
    public Subject getSubject(int subjectId)
    {
        return basicMapper.getSubject(subjectId);
    }

    public List<Subject> getAllSubject()
    {
        return basicMapper.getAllSubject();
    }

    public List<Subject> getTeacherSubjects(int teaId)
    {
        return basicMapper.getTeacherSubjects(teaId);
    }

    public void insertUserSubject(TeaSubject teaSubject) throws SqlAffectedCountException
    {
        if (basicMapper.insertUserSubject(teaSubject) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public int deleteUserSubjectByUserId(int teaId)
    {
        return basicMapper.deleteUserSubjectByUserId(teaId);
    }

    public void deleteUserSubject(int teaId, int subjectId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteUserSubject(teaId, subjectId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public List<Subject> getSchoolSubjects(int schoolId)
    {
        return basicMapper.getSchoolSubjects(schoolId);
    }

    public List<Integer> getSubjectIdBySubjectName(String subjectName)
    {
        return basicMapper.getSubjectIdBySubjectName(subjectName);
    }

    public void deleteTeacherTextbookBySubjectId(int subjectId, int teaId)
    {
        basicMapper.deleteTeacherTextbookBySubjectId(subjectId, teaId);
    }

    // 大纲
    // --------------------------------------------------------------------------------
    public int insertSchema(Scm schema)
    {
        return basicMapper.insertSchema(schema);
    }

    public Scm getSchema(int scmId)
    {
        return basicMapper.getSchema(scmId);
    }

    public List<Scm> getAllSchema()
    {
        return basicMapper.getAllSchema();
    }

    public List<Scm> getSchemaBySchoolId(int schoolId)
    {
        return basicMapper.getSchemaBySchoolId(schoolId);
    }

    public List<Scm> getSchemaOutSchool(int schoolId)
    {
        return basicMapper.getSchemaOutSchool(schoolId);
    }

    public List<Integer> getSchemaIdBySchemaName(String scmName)
    {
        return basicMapper.getSchemaIdBySchemaName(scmName);
    }

    public void insertSchemaTextbook(Book textbook)
    {
        basicMapper.insertSchemaTextbook(textbook);
    }

    public void deleteSchemaTextbook(int selectedSchemaId, int bookId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteSchemaTextbook(selectedSchemaId, bookId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    // not_used
    public List<SchoolScm> getSchoolSchemaBySchoolId(int schoolId)
    {
        return basicMapper.getSchoolSchemaBySchoolId(schoolId);
    }

    public void insertSchoolSchema(SchoolScm schoolSchema) throws SqlAffectedCountException
    {
        if (basicMapper.insertSchoolSchema(schoolSchema) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public void deleteSchoolSchema(int schoolId, int scmId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteSchoolSchema(schoolId, scmId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }


    // 课本
    // --------------------------------------------------------------------------------
    public Book getTextbook(int bookId)
    {
        return basicMapper.getTextbook(bookId);
    }

    public void updateTextbook(Book textbook)
    {
        basicMapper.updateTextbook(textbook);
    }

    public void insertTextbook(Book textbook) throws SqlAffectedCountException
    {
        if (basicMapper.insertTextbook(textbook) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public List<Book> getAllTextbook()
    {
        return basicMapper.getAllTextbook();
    }

    public List<Book> getTextbookForStudent(int schoolId, int stuId)
    {
        return basicMapper.getTextbookForStudent(schoolId, stuId);
    }

    public List<Book> getTextbookOfASchool(int schoolId)
    {
        return basicMapper.getTextbookOfASchool(schoolId);
    }

    public List<Book> getTextbookBySchemaId(int scmId)
    {
        return basicMapper.getTextbookBySchemaId(scmId);
    }

    public List<Book> getTextbookOutOfSchema(int scmId, int category, int subjectId)
    {
        return basicMapper.getTextbookOutOfSchema(scmId, category, subjectId);
    }

    public int getTextbookCountForAdmin(int scmId, int subjectId, int category, int grade)
    {
        return basicMapper.getTextbookCountForAdmin(scmId, subjectId, category, grade);
    }

    public List<Book> getTextbookForAdmin(int scmId, int subjectId, int category, int grade, int limitBegin,
            int pageSize)
    {
        return basicMapper.getTextbookForAdmin(scmId, subjectId, category, grade, limitBegin, pageSize);
    }

    public List<Book> getTextbookForTeacher(int userId)
    {
        return basicMapper.getTextbookForTeacher(userId);
    }

    public List<Book> getTextbookBySchoolId_SubjectId(int subjectId, int schoolId)
    {
        return basicMapper.getTextbookBySchoolId_SubjectId(subjectId, schoolId);
    }

    public List<Book> getTextbookForTeacherExceptFav(int schoolId, int userId)
    {
        return basicMapper.getTextbookForTeacherExceptFav(schoolId, userId);
    }

    public List<Integer> getTextbookIdByTextbookName(String bookName)
    {
        return basicMapper.getTextbookIdByTextbookName(bookName);
    }

    public void insertTeacherTextbook(TeaBook tt) throws SqlAffectedCountException
    {
        if (basicMapper.insertTeacherTextbook(tt) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public int deleteTeacterTextbookAllByTeacherId(int teaId)
    {
        return basicMapper.deleteTeacterTextbookAllByTeacherId(teaId);
    }

    public void deleteTeacherTextbook(int userId, int bookId) throws SqlAffectedCountException
    {
        if (basicMapper.deleteTeacherTextbook(userId, bookId) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public List<TeaBook> getTeacherTextbook(int bookId, int teaId)
    {
        return basicMapper.getTeacherTextbook(bookId, teaId);
    }

    // 章节
    // --------------------------------------------------------------------------------
    public List<Chapter> getChapterByTextId(int bookId)
    {
        return basicMapper.getChapterByTextId(bookId);
    }

    public List<Section> getSectionByTextId(int bookId)
    {
        return basicMapper.getSectionByTextId(bookId);
    }

    public List<Section> getSectionByChapterId(int chapterId)
    {
        return basicMapper.getSectionByChapterId(chapterId);
    }

    public void insertChapter(Chapter chapter)
    {
        basicMapper.insertChapter(chapter);
    }

    public void insertSection(Section section)
    {
        basicMapper.insertSection(section);
    }


    // 学校
    // --------------------------------------------------------------------------------
    public School getSchool(int schoolId)
    {
        return basicMapper.getSchool(schoolId);
    }

    public List<School> getCategoryedSchoolInRegion(int regionId, int categoryId)
    {
        return basicMapper.getCategoryedSchoolInRegion(regionId, categoryId);
    }

    public List<School> getAllSchoolInCities(String provinceId, String cityId, int category)
    {
        return basicMapper.getAllSchoolInCities(provinceId, cityId, category);
    }

    public void insertSchool(School school)
    {
        basicMapper.insertSchool(school);
    }

    public void updateSchool(School school)
    {
        basicMapper.updateSchool(school);
    }

    public void deleteSchool(int schoolId)
    {
        basicMapper.deleteSchool(schoolId);
    }


    // 地区
    // --------------------------------------------------------------------------------
    public List<Province> getAllProvince()
    {
        return basicMapper.getAllProvince();
    }

    public List<City> getCityByProvinceId(String provinceId)
    {
        return basicMapper.getCityByProvinceId(provinceId);
    }

    public City getCity(String cityId)
    {
        return basicMapper.getCity(cityId);
    }

    public Region getRegion(int regionId)
    {
        return basicMapper.getRegion(regionId);
    }


    // 管理员
    // --------------------------------------------------------------------------------
    public List<User> getSchoolUsers(int schoolId, int userRole)
    {
        return basicMapper.getSchoolUsers(schoolId, userRole);
    }


    // 评语
    // --------------------------------------------------------------------------------
    public List<Remark> getAllRemark()
    {
        return basicMapper.getAllRemark();
    }


    // 机器授权
    // --------------------------------------------------------------------------------
    public MachineLicense getMachineLicense(String licenseCode)
    {
        return basicMapper.getMachineLicense(licenseCode);
    }

    public void updateMachineLicense(MachineLicense machineLicense)
    {
        basicMapper.updateMachineLicense(machineLicense);
    }

    // 基本操作
    // --------------------------------------------------------------------------------
    public Que getQuestionBank(int queId)
    {
        return queMapper.getQuestionBank(queId);
    }

    public void insertQuestionBank(Que que) throws SqlAffectedCountException
    {
        if (queMapper.insertQuestionBank(que) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    public void updateQuestionBank(Que que)
    {
        queMapper.updateQuestionBank(que);
    }

    // notification
    // --------------------------------------------------------------------------------
    public Notification getNotification(int notificationId)
    {
        return basicMapper.getNotification(notificationId);
    }

    public int getSelfCreatedNotificationCount(int userId, int createTime)
    {
        return basicMapper.getSelfCreatedNotificationCount(userId, createTime);
    }

    public List<Notification> getSelfCreatedNotification(int userId, int createTime, int limitBegin, int pageSize)
    {
        return basicMapper.getSelfCreatedNotification(userId, createTime, limitBegin, pageSize);
    }

    public int getReceivedNotificationCount(int userId, int createTime)
    {
        return basicMapper.getReceivedNotificationCount(userId, createTime);
    }

    public List<Notification> getReceivedNotification(int userId, int createTime, int limitBegin, int pageSize)
    {
        return basicMapper.getReceivedNotification(userId, createTime, limitBegin, pageSize);
    }

    public void insertNotification(Notification notification)
    {
        basicMapper.insertNotification(notification);
    }

    public void deleteNotification(int notificationId)
    {
        basicMapper.deleteNotification(notificationId);
    }

    public void insertUserNotification(List<UserNotification> userNotificationList)
    {
        basicMapper.insertUserNotification(userNotificationList);
    }

    public void setUserNotificationReaded(int userId, int notificationId)
    {
        basicMapper.setUserNotificationReaded(userId, notificationId);
    }

    public int getOneNotificationReceiveCount(int notificationId)
    {
        return basicMapper.getOneNotificationReceiveCount(notificationId);
    }

    public int getOneNotificationReadedCount(int notificationId)
    {
        return basicMapper.getOneNotificationReadedCount(notificationId);
    }
    
    // 查询用户未读通知数
    int getUnReadedReceivedNotificationCount(int userId)
    {
        return basicMapper.getUnReadedReceivedNotificationCount(userId);
    }

    // UserChatToken
    // --------------------------------------------------------------------------------
    public UserChatToken getUserChatToken(int userId)
    {
        return basicMapper.getUserChatToken(userId);
    }

    public void insertUserChatToken(UserChatToken userChatToken)
    {
        basicMapper.insertUserChatToken(userChatToken);
    }


    // 获取资源
    // --------------------------------------------------------------------------------

    public int getYjResUseCount(int resId)
    {
        return resMapper.getYjResUseCount(resId);
    }

    /**
     * 系统编辑查看系统资源
     */
    public int getSystemResourceCount(int resTag, int subjectId, int bookId, int chapterId,
            int sectionId, long updateTime)
    {
        return resMapper.getSystemResourceCount(resTag, subjectId, bookId, chapterId, sectionId, updateTime);
    }

    public List<Res> getSystemResource(int resTag, int subjectId, int bookId, int chapterId,
            int sectionId, long updateTime, int limitBegin, int pageSize)
    {
        return resMapper.getSystemResource(resTag, subjectId, bookId, chapterId, sectionId, updateTime, limitBegin,
                pageSize);
    }

    /**
     * 老师在“我的资源”里所看到的资源
     */
    public int getTeacherResourceCount(int teaId, int resTag, int subjectId, int bookId, int chapterId, int sectionId)
    {
        return resMapper.getTeacherResourceCount(teaId, resTag,
                subjectId, bookId, chapterId, sectionId);
    }

    public List<Res> getTeacherResource(int teaId, int resTag, int subjectId, int bookId, int chapterId, int sectionId,
            int limitBegin, int pageSize)
    {
        return resMapper.getTeacherResource(teaId, resTag,
                subjectId, bookId, chapterId, sectionId, limitBegin, pageSize);
    }

    /**
     * 老师查询“系统资源”
     */
    public int getSystemResourceForTeacherCount(int teaId, int resTag,
            int subjectId, int bookId, int chapterId, int sectionId)
    {
        return resMapper.getSystemResourceForTeacherCount(teaId, resTag,
                subjectId, bookId, chapterId, sectionId);
    }

    public List<Res> getSystemResourceForTeacher(int teaId, int resTag,
            int subjectId, int bookId, int chapterId, int sectionId, int limitBegin, int pageSize)
    {
        return resMapper.getSystemResourceForTeacher(teaId, resTag,
                subjectId, bookId, chapterId, sectionId, limitBegin, pageSize);
    }

    /**
     * 老师在“学校资源”里看到的资源
     */
    public int getSchoolResourceForTeacherCount(
            int teaId, int schoolId, int resTag, int shareToSchool,
            int subjectId, int bookId, int chapterId, int sectionId)
    {
        return resMapper.getSchoolResourceForTeacherCount(
                teaId, schoolId, resTag, shareToSchool,
                subjectId, bookId, chapterId, sectionId);
    }

    public List<Res> getSchoolResourceForTeacher(int teaId, int schoolId, int resTag, int shareToSchool,
            int subjectId, int bookId, int chapterId, int sectionId, int limitBegin, int pageSize)
    {
        return resMapper.getSchoolResourceForTeacher(teaId, schoolId, resTag, shareToSchool,
                subjectId, bookId, chapterId, sectionId, limitBegin, pageSize);
    }

    /**
     * 学生和校长在“学校资源”里看到的资源
     */
    public int getSchoolResourceCount(int schoolId, int resTag, int shareToSchool,
            int subjectId, int bookId, int chapterId, int sectionId,
            long createTime)
    {
        return resMapper.getSchoolResourceCount(schoolId, resTag, shareToSchool,
                subjectId, bookId, chapterId, sectionId,
                createTime);
    }

    public List<Res> getSchoolResource(int schoolId, int resTag, int shareToSchool,
            int subjectId, int bookId, int chapterId, int sectionId,
            long createTime, int limitBegin, int pageSize)
    {
        return resMapper.getSchoolResource(schoolId, resTag, shareToSchool,
                subjectId, bookId, chapterId, sectionId,
                createTime, limitBegin, pageSize);
    }

    // 资源基本操作
    // --------------------------------------------------------------------------------
    public Res getResourceInfo(int resId)
    {
        return resMapper.getResourceInfo(resId);
    }

    public void insertResourceInfo(Res res)
    {
        resMapper.insertResourceInfo(res);
    }

    public void insertResourceEntity(Res res)
    {
        resMapper.insertResourceEntity(res);
    }

    public void updateResourceInfo(Res res)
    {
        resMapper.updateResourceInfo(res);
    }

    // 资源文件的操作
    // --------------------------------------------------------------------------------
    public ResFile getResourceFile(int fileId)
    {
        return resMapper.getResourceFile(fileId);
    }

    public void insertResourceFile(ResFile resourceFile)
    {
        resMapper.insertResourceFile(resourceFile);
    }

    public void updateResourceFile(ResFile resourceFile)
    {
        resMapper.updateResourceFile(resourceFile);
    }

    public List<ResFile> getResourceFileByResourceId(int resId)
    {
        return resMapper.getResourceFileByResourceId(resId);
    }

    // 资源数
    // --------------------------------------------------------------------------------
    public int getResourceCountOfTeacher(int teaId, int subjectId)
    {
        return resMapper.getResourceCountOfTeacher(teaId, subjectId);
    }

    // 校长端查看老师、班级资源
    public List<Res> getTeacherResourceListByPage(int teaId, int subjectId, int limitBegin, int pageSize)
    {
        return resMapper.getTeacherResourceListByPage(teaId, subjectId, limitBegin, pageSize);
    }

    // ================================================================================
    // asm service
    // ================================================================================
    public void sendAssignmentToClass(int teaId, int asmId, int clsId)
    {
        int currentTime = Utils.currentSeconds();
        List<User> allStudents = basicMapper.getAllStudentsOfAClass(clsId);

        ClsAsm clsAsm = new ClsAsm();
        clsAsm.setAsmId(asmId);
        clsAsm.setTeaId(teaId);
        clsAsm.setClsId(clsId);
        clsAsm.setStatusId(Constants.AsmStatus.NEW_ASSIGNED);
        clsAsm.setAssignTime(currentTime);
        clsAsm.setStudentCount(allStudents.size());
        asmMapper.insertClsAsm(clsAsm);

        List<StuAsm> stuAsmListToInsert = new ArrayList<StuAsm>();
        final Asm asm = asmMapper.getAsm(asmId);

        for (User stu : allStudents)
        {
            StuAsm stuAsm = new StuAsm();
            stuAsm.setAsmId(asmId);
            stuAsm.setStuId(stu.getUserId());
            stuAsm.setClsId(clsId);
            stuAsm.setTeaId(teaId);
            stuAsm.setAssignTime(currentTime);
            stuAsm.setStatusId(Constants.AsmStatus.NEW_ASSIGNED);
            stuAsmListToInsert.add(stuAsm);
        }

        if (stuAsmListToInsert.size() > 0)
        {
            asmMapper.insertStuAsmList(stuAsmListToInsert);
        }

        final List<User> allStus = allStudents;
        final String asmName = asm.getAsmName();
        int startTime = asm.getStartTime();
        int finishTime = asm.getFinishTime();
        // 用于模拟定时推送（设置的定时显示功能）， 幼教创建任务的startTime、finishTime一定都不为0（按现在的逻辑）
        if (startTime == 0 || finishTime == 0)
        {
            startTime = Utils.currentSeconds();
            finishTime = startTime + 86400;
        }
        final String beginTime = Utils.getFullTimeWithSecondsString(startTime);
        final String endTime = Utils.getFullTimeWithSecondsString(finishTime);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (User stu : allStus)
                {
                    pushMessageToSingle(stu.getUserId(), "新任务！", "新任务：" + asmName, beginTime, endTime);
                }
            }
        }).run();
    }


    public void pushMessageToSingle(int stuId, String title, String msg, String beginTime, String endTime)
    {

        String appId = "sAWqjJaeFv9Raa8RBvjFh6";
        String appkey = "TO5DSKZh2o9QSv27CIfB99";
        String master = "gfwLe6jwvv8jSpFwaWsw59";
        String host = "http://sdk.open.api.igexin.com/apiex.htm";

        Login login = getLogin(stuId);
        if (login == null || Utils.stringEmpty(login.getClientId()))
        {
            return;
        }

        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(login.getClientId());

        try
        {
            IGtPush push = new IGtPush(host, appkey, master);
            push.connect();

            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            message.setOfflineExpireTime(24 * 3600 * 1000);// 离线有效时间，单位为毫秒，可选
            // message.setPushNetWorkType(1);
            // //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。

            if (login.getClientType() == 1)
            {
                // Android
                NotificationTemplate template = new NotificationTemplate();
                // 设置APPID与APPKEY
                template.setAppId(appId);
                template.setAppkey(appkey);
                // 设置通知栏标题与内容
                template.setTitle(title);
                template.setText(msg);
                // 配置通知栏图标
                template.setLogo("push.png");
                // 配置通知栏网络图标
                template.setLogoUrl("");
                // 设置通知是否响铃，震动，或者可清除
                template.setIsRing(true);
                template.setIsVibrate(true);
                template.setIsClearable(true);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                template.setTransmissionType(1);
                template.setTransmissionContent("点击进入");

                // 设置定时发送
                template.setDuration(beginTime, endTime);

                message.setData(template);
//                IPushResult ret = push.pushMessageToSingle(message, target);
                push.pushMessageToSingle(message, target);
            }
            else
            {
                TransmissionTemplate template = new TransmissionTemplate();
                template.setAppId(appId);
                template.setAppkey(appkey);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                template.setTransmissionType(1);
                // template.setTransmissionContent("请输入需要透传的内容");
                template.setTransmissionContent(msg);

                // 设置定时发送
                template.setDuration(beginTime, endTime);

                try
                {
                    template.setPushInfo("", 1, msg, "default", "", "", "", "");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                message.setData(template);
//                IPushResult ret = push.pushMessageToSingle(message, target);
                push.pushMessageToSingle(message, target);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void pushMessageToList(String title, String msg, List<Integer> userIdList)
    {

        String appId = "sAWqjJaeFv9Raa8RBvjFh6";
        String appkey = "TO5DSKZh2o9QSv27CIfB99";
        String master = "gfwLe6jwvv8jSpFwaWsw59";
        String host = "http://sdk.open.api.igexin.com/apiex.htm";

        List<Target> androidTargets = new ArrayList<Target>();
        List<Target> iosTargets = new ArrayList<Target>();
        for (int i = 0; i < userIdList.size(); i++)
        {
            Login login = getLogin(userIdList.get(i));
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(login.getClientId());
            if (login.getClientType() == 1)
            {
                androidTargets.add(target);
            }
            else
            {
                iosTargets.add(target);
            }
        }


        try
        {
            IGtPush push = new IGtPush(host, appkey, master);
            push.connect();

            ListMessage message = new ListMessage();
            message.setOffline(true);
            message.setOfflineExpireTime(24 * 3600 * 1000);// 离线有效时间，单位为毫秒，可选
            // message.setPushNetWorkType(1);
            // //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。

            if (androidTargets.size() > 0)
            {
                // Android
                NotificationTemplate template = new NotificationTemplate();
                // 设置APPID与APPKEY
                template.setAppId(appId);
                template.setAppkey(appkey);
                // 设置通知栏标题与内容
                template.setTitle(title);
                template.setText(msg);
                // 配置通知栏图标
                template.setLogo("icon.png");
                // 配置通知栏网络图标
                template.setLogoUrl("");
                // 设置通知是否响铃，震动，或者可清除
                template.setIsRing(true);
                template.setIsVibrate(true);
                template.setIsClearable(true);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                template.setTransmissionType(2);
                template.setTransmissionContent("点击进入");
                message.setData(template);

                String taskId = push.getContentId(message);
                // 使用taskID对目标进行推送
//                IPushResult ret = push.pushMessageToList(taskId, androidTargets);
                push.pushMessageToList(taskId, androidTargets);
            }
            if (iosTargets.size() > 0)
            {
                TransmissionTemplate template = new TransmissionTemplate();
                template.setAppId(appId);
                template.setAppkey(appkey);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                template.setTransmissionType(2);
                // template.setTransmissionContent("请输入需要透传的内容");
                template.setTransmissionContent(msg);

                try
                {
                    template.setPushInfo("", 1, title, "default", msg, "", "", "");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                // template.setPushInfo("actionLocKey", 4, "message",
                // "sound","payload", "locKey", "locArgs",
                // "launchImage","ContentAvailable");

                message.setData(template);
                String taskId = push.getContentId(message);
                // 使用taskID对目标进行推送
//                IPushResult ret = push.pushMessageToList(taskId, iosTargets);
                push.pushMessageToList(taskId, iosTargets);
            }

        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }

    }


    public synchronized void updateAsmStatus(StuAsm stuAsm)
    {
        // 判断学生状态为“待订正”或“完成”
        if (stuAsm.getEndSendbackTime() < stuAsm.getBeginSendbackTime())
        {
            stuAsm.setEndSendbackTime(Utils.currentSeconds());

            stuAsm.setStatusId(Constants.AsmStatus.FINISHED);
            asmMapper.updateStuAsm(stuAsm);

            // 当学生状态为“完成”，且所有学生都为“完成”，将老师的状态置为“完成”
            if (stuAsm.getStatusId() == (Constants.AsmStatus.FINISHED))
            {
                boolean isTeaFinish = true;

                List<StuAsm> tmpStuAsmlist = asmMapper.getStuAsmOfAClass(stuAsm.getAsmId(), stuAsm.getClsId());
                for (StuAsm tmpStuAsm : tmpStuAsmlist)
                {
                    if (tmpStuAsm.getStatusId() != (Constants.AsmStatus.FINISHED))
                    {
                        isTeaFinish = false;
                        break;
                    }
                }

                if (isTeaFinish)
                {
                    ClsAsm clsAsm = asmMapper.getClsAsm(stuAsm.getAsmId(), stuAsm.getClsId());
                    clsAsm.setStatusId(Constants.AsmStatus.FINISHED);
                    asmMapper.updateClsAsm(clsAsm);
                }
            }
        }
    }

    public synchronized void updateStatistics(int asmId, int clsId)
    {
        // 查出这个班级所有的学生作答，计算统计数据并更新studentAssignment表和classAssignment表
        ClsAsm clsAsm = asmMapper.getClsAsm(asmId, clsId);
        List<StuAsm> allStuAsmList = asmMapper.getStuAsmOfAClass(asmId, clsId);

        List<StuAsm> correctedList = new ArrayList<StuAsm>();
        List<StuAsm> submittedList = new ArrayList<StuAsm>();
        for (StuAsm sa : allStuAsmList)
        {
            if (sa.getIsCorrected() == 1)
            {
                correctedList.add(sa);
            }
            if (sa.getIsSubmitted() == 1)
            {
                submittedList.add(sa);
            }
        }

        clsAsm.setStudentCount(allStuAsmList.size());
        clsAsm.setCorrectedCount(correctedList.size());
        clsAsm.setSubmittedCount(submittedList.size());

        setMaxMinAvrScore(clsAsm, correctedList);

        setFangChaBiaoZhunCha(clsAsm, correctedList);

        Utils.setListRanking(correctedList);

        asmMapper.updateClsAsm(clsAsm);
        for (StuAsm sa : correctedList)
        {
            asmMapper.updateStuAsm(sa);
        }

        log("updated: " + clsAsm.getAsmId() + ", " + clsAsm.getAvrScore() + ", " + clsAsm.getMaxScore()
                + ", " + clsAsm.getMinScore() + ", " + clsAsm.getFangCha() + ", " + clsAsm.getBiaoZhunCha() + ", ");
    }

    public synchronized void updateStatisticsCount(int asmId, int clsId)
    {
        // 查出这个班级所有的学生作答，计算统计数据并更新studentAssignment表和classAssignment表
        ClsAsm clsAsm = asmMapper.getClsAsm(asmId, clsId);
        List<StuAsm> allStuAsmList = asmMapper.getStuAsmOfAClass(asmId, clsId);

        int correctedCount = 0;
        int submittedCount = 0;
        for (StuAsm sa : allStuAsmList)
        {
            if (sa.getIsCorrected() == 1)
            {
                correctedCount++;
            }
            if (sa.getIsSubmitted() == 1)
            {
                submittedCount++;
            }
        }

        clsAsm.setStudentCount(allStuAsmList.size());
        clsAsm.setCorrectedCount(correctedCount);
        clsAsm.setSubmittedCount(submittedCount);

        asmMapper.updateClsAsm(clsAsm);
        log("updated: " + clsAsm.getAsmId() + ", " + clsAsm.getAvrScore() + ", " + clsAsm.getMaxScore()
                + ", " + clsAsm.getMinScore() + ", " + clsAsm.getFangCha() + ", " + clsAsm.getBiaoZhunCha() + ", ");
    }

    public String generateAsmQueHtmlFileContent(String queTypeString, String contentHtml)
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<html><head>")
                .append("<script src='../jquery-1.7.1.min.js' type='text/javascript' ></script>")
                .append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />")
                .append("<style> ul {margin: 0;padding: 0}.problem_self {width: 800px;padding: 10px;font-size: 14px;}.problem_title {margin-bottom: 10px;}</style></head>")
                .append("<body><div class='problem_self'><div class='problem_title'>")
                .append(queTypeString)
                .append("</div>")
                .append("<div class='problem_content'>")
                .append(contentHtml)
                .append("</div></div></body></html>");

        return stringBuilder.toString();
    }

    // yj service
    public void correctYjAsm(int asmId, int clsId, int stuId, int score, String feedback) throws Exception
    {
        String baseDir = Constants.PATH_FILE + Utils.groupUserId(stuId) + "/";
        String allDir = baseDir + asmId + "/";      // 作答+批改文件夹
        String unzipDir = baseDir + asmId + "_c/";  // 批改文件夹
        String zipFilePath = baseDir + asmId + "_c.zip";
        String jsonFileName = asmId + ".json";
        Utils.makeSureDirExists(unzipDir);
        int now = Utils.currentSeconds();

        // 更新json，stu_answer和stu_answer_item
        String filePath = allDir + jsonFileName;
        Utils.makeSureFileExist(filePath);

        JSONObject json = new JSONObject(Utils.readFromFile(filePath, "UTF-8"));
        json.put("e" + "ndSubmitTime", String.valueOf(now));
        json.put("b" + "eginSendbackTime", String.valueOf(now));
        JSONObject stuAnsJO = json.getJSONArray("a" + "nswerList").getJSONObject(0); // 每份幼教作业只有一个题目
        updateYjStuAnswer(asmId, clsId, stuId, score, feedback, stuAnsJO, now);

        // 更新stu_asm
        StuAsm stuAsm = asmMapper.getStuAsm(asmId, stuId, clsId);
        stuAsm.setStatusId(Constants.AsmStatus.SENDBACK);
        stuAsm.setEndSubmitTime(now);
        stuAsm.setBeginSendbackTime(now);
        stuAsm.setIsCorrected(1);
        stuAsm.setStuAsmScore(score);
        asmMapper.updateStuAsm(stuAsm);

        // 更新班级数据
        updateStatistics(asmId, clsId);

        String beginTime = Utils.getFullTimeWithSecondsString(now);
        // int 86400=24*60*60
        String endTime = Utils.getFullTimeWithSecondsString(now + 86400);
        pushMessageToSingle(stuId, "收到批改！", "收到批改：" + stuAsm.getAsmName(), beginTime, endTime);

        Utils.writeFileAllText(unzipDir + jsonFileName, json.toString());
        Utils.copyDir(unzipDir, allDir);
        Utils.zipFile(unzipDir, zipFilePath);
    }

    private void updateYjStuAnswer(int asmId, int clsId, int stuId, int score, String feedback, JSONObject stuAnsJO,
            int createTime)
            throws JSONException
    {
        stuAnsJO.put("s" + "tudentScore", String.valueOf(score));

        JSONArray answerItemArray = stuAnsJO.getJSONArray("a" + "nswerItemList");

        // 将老师批语存入数据库
        if (!"".equals(feedback))
        {
            StuAnswerItem stuAnsItem = new StuAnswerItem();
            stuAnsItem.setAsmId(asmId);
            stuAnsItem.setClsId(clsId);
            stuAnsItem.setStuId(stuId);
            AsmQue asmQue = getAsmQueOfAnAsm(asmId);
            stuAnsItem.setAsmQueId(asmQue.getAsmQueId());

            stuAnsItem.setAnswerNum(answerItemArray.length() + 1);
            stuAnsItem.setAnswerType(1);
            stuAnsItem.setAnswerItem(feedback);
            stuAnsItem.setCreateTime(createTime);
            stuAnsItem.setCycleNum(0);
            stuAnsItem.setIsPiGai(1);
            asmMapper.insertStuAnswerItem(stuAnsItem);

            // 将老师批语加到json里
            JSONObject teaRemark = new JSONObject();
            teaRemark.put("a" + "nswerNum", String.valueOf(stuAnsItem.getAnswerNum()));
            teaRemark.put("a" + "nswerType", String.valueOf(stuAnsItem.getAnswerType()));
            teaRemark.put("a" + "nswerItem", String.valueOf(stuAnsItem.getAnswerItem()));
            teaRemark.put("c" + "reateTime", String.valueOf(stuAnsItem.getCreateTime()));
            teaRemark.put("c" + "ycleNum", String.valueOf(stuAnsItem.getCycleNum()));
            teaRemark.put("i" + "sPiGai", String.valueOf(stuAnsItem.getIsPiGai()));

            answerItemArray.put(teaRemark);
        }
    }

    // basic service
    // --------------------------------------------------------------------------------
    public List<Grade> generateSchoolGrade(List<Integer> enterSchoolYearList)
    {
        List<Grade> schoolGradesList = new ArrayList<Grade>();
        for (int enterSchoolYear : enterSchoolYearList)
        {
            schoolGradesList.add(getGradeByEnterYear(enterSchoolYear));
        }
        return schoolGradesList;
    }

    public Grade getGradeByEnterYear(int enterYear)
    {
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY) + 1;
        int moveOn = month >= 9 ? 1 : 0;  // 9月入学

        int startIndex = 1;

        Grade grade = new Grade();
        grade.setGradeIndex(startIndex + (thisYear - enterYear - 1) + moveOn);

        return grade;
    }

    /**
     * 根据userInfo中的CacheTextbookId..初始化tsClassifyModule
     *
     * @param tsClassifyModule
     * @param teacher
     */
    public void selectCache(TextbookSelectorClassify tsClassifyModule, User teacher)
    {
        tsClassifyModule.selectSubject(teacher.getCacheSubjectId());
        tsClassifyModule.selectTextbook(teacher.getCacheBookId());
        tsClassifyModule.selectChapter(teacher.getCacheChapterId());
        // tsClassifyModule.selectSection(teacher.getCacheSectionId());
    }

    public String getCityJsonStringByProvinceId(String provinceId)
    {
        JSONArray jsonArray = new JSONArray();
        List<City> cityList = basicMapper.getCityByProvinceId(provinceId);
        for (City city : cityList)
        {
            JSONObject json = new JSONObject();
            try
            {
                json.put("cityId", city.getCityId());
                json.put("cityName", city.getCityName());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            jsonArray.put(json);
        }

        return jsonArray.toString();
    }

    public List<Cls>[] getClassInfoListOfASchool(int schoolId, List<Integer> enterSchoolYearList, int subjectId)
    {
        List<Cls>[] tmplassInfoList = new ArrayList[enterSchoolYearList.size()];
        for (int i = 0; i < enterSchoolYearList.size(); i++)
        {
            tmplassInfoList[i] = getClassByGrade(schoolId, (int) enterSchoolYearList.get(i));
            for (Cls classInfo : tmplassInfoList[i])
            {
                int clsId = classInfo.getClsId();

                // 学生数
                classInfo.setNumberOfStudents(getCountOfClassMember(clsId, Constants.UserRole.YJ_PARENT));

                // 老师信息
                classInfo.setClassTeachers(getTeachersOfClass(clsId, subjectId));

                // 作业数及作业统计
                List<ClsAsm> allClassAssignment = getClassAssignmentForMasterStatistics(
                        clsId, subjectId);

                float submittedRatio = 0;
                float correctedRatio = 0;
                for (ClsAsm ca : allClassAssignment)
                {
                    submittedRatio += ca.getSubmitRatio();
                    correctedRatio += ca.getCorrectRatio();
                }

                if (allClassAssignment.size() > 0)
                {
                    submittedRatio = submittedRatio / allClassAssignment.size();
                    correctedRatio = correctedRatio / allClassAssignment.size();
                }

                classInfo.setNumberOfClassAssignment(allClassAssignment.size());
                classInfo.setAvrClassAssignmentSubmittedRatio((float) Utils.roundDouble(submittedRatio, 1));
                classInfo.setAvrClassAssignmentCorrectedRatio((float) Utils.roundDouble(correctedRatio, 1));
            }
        }
        return tmplassInfoList;
    }

    public void insertClass(String clsName, int classEnterYear, int schoolId) throws SqlAffectedCountException
    {
        Cls classInfo = new Cls();
        classInfo.setClsName(clsName);
        classInfo.setEntranceYear(classEnterYear);
        classInfo.setSchoolId(schoolId);

        if (basicMapper.insertClass(classInfo) != 1)
        {
            throw new SqlAffectedCountException();
        }
    }

    // user service
    // --------------------------------------------------------------------------------
    public void insertUsers(List<String> userNames, int userRole, String password, int clsId, int schoolId)
            throws Exception
    {
        if (userRole == Constants.UserRole.YJ_TEACHER)
        {
            // 插入幼教老师
            for (int i = 0; i < userNames.size(); i++)
            {
                doInsertUser(userNames.get(i).trim(), password, Constants.UserRole.YJ_TEACHER, clsId, schoolId);
            }
        }
        else if (userRole == Constants.UserRole.YJ_PARENT)
        {
            // 插入幼教家长
            for (int i = 0; i < userNames.size(); i++)
            {
                doInsertUser(userNames.get(i).trim(), password, Constants.UserRole.YJ_PARENT, clsId, schoolId);
            }
        }
    }

    // private
    // --------------------------------------------------------------------------------
    private void doInsertUser(String userName, String password, int userRole, int clsId, int schoolId)
            throws SqlAffectedCountException
    {
        String end = "";

        switch (userRole)
        {
        case Constants.UserRole.YJ_TEACHER:
            end = "yt";
            break;

        case Constants.UserRole.YJ_PARENT:
            end = "yp";
            break;
        }
        int userId = insertUser(userName, password, userRole, schoolId, end);
        insertUserClass(userId, clsId);
    }


    private int insertUser(String userName, String password, int userRole, int schoolId, String end)
            throws SqlAffectedCountException
    {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setUserRole(userRole);
        user.setSchoolId(schoolId);

        insertUserAndGetUserId(user);
        int userId = user.getUserId();
        String loginId = userId + end;

        user.setLoginId(loginId);
        user.setLoginName(loginId); // 初始化成loginId，以后可以改
        basicMapper.updateUser(user);
        return userId;
    }

    private void setFangChaBiaoZhunCha(ClsAsm clsAsm, List<StuAsm> correctedList)
    {
        float fangCha = 0.0f;
        float biaoZhunCha = 0.0f;
        for (StuAsm stuAsm : correctedList)
        {
            float difference = stuAsm.getStuAsmScore() - clsAsm.getAvrScore();
            fangCha += difference * difference;
        }

        if (clsAsm.getCorrectedCount() == 0)
        {
            clsAsm.setFangCha(0);
            clsAsm.setBiaoZhunCha(0);
        }
        else
        {
            fangCha = fangCha / (clsAsm.getCorrectedCount());
            biaoZhunCha = (float) Math.sqrt(fangCha);
            clsAsm.setFangCha((float) Math.round(fangCha * 10) / 10);
            clsAsm.setBiaoZhunCha((float) Math.round(biaoZhunCha * 10) / 10);
        }
    }

    private void setMaxMinAvrScore(ClsAsm classAsm, List<StuAsm> correctedList)
    {
        int stuCorrectedCount = classAsm.getCorrectedCount();

        if (stuCorrectedCount == 0)
        {
            classAsm.setAvrScore(0);
            classAsm.setMaxScore(0);
            classAsm.setMinScore(0);
            return;
        }

        float maxScore = 0.0f;
        float minScore = 10000.0f;
        float avrScore = 0.0f;
        for (StuAsm stuAsm : correctedList)
        {
            float stuAssignScore = stuAsm.getStuAsmScore();
            avrScore += stuAssignScore;
            if (maxScore < stuAssignScore)
            {
                maxScore = stuAssignScore;
            }
            else if (minScore > stuAssignScore)
            {
                minScore = stuAssignScore;
            }
        }

        avrScore = avrScore / stuCorrectedCount;
        avrScore = (float) Math.round(avrScore * 10) / 10;
        if (minScore == 10000.0f)
        {
            minScore = 0.0f;
        }
        classAsm.setAvrScore(avrScore);
        classAsm.setMaxScore(maxScore);
        classAsm.setMinScore(minScore);
    }

    // ================================================================================
    // mobile asm
    // ================================================================================
    public void stuSubmitAsmAnswerV2(String tag, int asmId, int clsId, int stuId, FileBean uploadFile)
            throws IOException, JSONException, DataInconsistException
    {
        StuAsm stuAsm = getStuAsm(asmId, stuId, clsId);
        JSONObject fileJson = saveStuAnswerZipAndGetJsonV2(stuAsm, uploadFile);

        JSONArray answerArray = fileJson.getJSONArray("a" + "nswerList");

        for (int i = 0; i < answerArray.length(); i++)
        {
            JSONObject stuAnsJO = answerArray.getJSONObject(i);


            JSONArray answerItemArray = stuAnsJO.getJSONArray("a" + "nswerItemList");
            for (int j = 0; j < answerItemArray.length(); j++)
            {
                JSONObject stuAnsItemJO = answerItemArray.getJSONObject(j);

                stuUpdateStuAnswerItem(stuAnsJO, stuAnsItemJO, asmId, clsId, stuId);
            }
        }

        stuAsm.setBeginAnswerTime(Integer.parseInt(fileJson.getString("b" + "eginAnswerTime")));
        stuAsm.setEndAnswerTime(Integer.parseInt(fileJson.getString("e" + "ndAnswerTime")));

        stuAsm.setIsSubmitted(1);

        stuAsm.setBeginSubmitTime(Utils.currentSeconds());
        stuAsm.setStatusId(Constants.AsmStatus.ANSWER_SUBMITTED);
        updateStuAsm(stuAsm);

        updateStatisticsCount(asmId, clsId);
    }


    public void teaSubmitAnswerZipV2(String tag, int asmId, int clsId, int stuId,
            int useUploadFile, HttpServletRequest request, FileBean uf)
            throws IOException, JSONException, DataInconsistException
    {
        StuAsm stuAsm = getStuAsm(asmId, stuId, clsId);
        if (stuAsm.getBeginSendbackTime() <= stuAsm.getEndSubmitTime())
        {
            JSONObject fileJson = teaSaveAnswerZipAndGetJsonV2(stuAsm, useUploadFile, request, uf);
            log(tag + "file saved and got json");

            JSONArray answerArray = fileJson.getJSONArray("a" + "nswerList");

            // 数组长度为1
            JSONObject stuAnsJO = answerArray.getJSONObject(0);
            stuAsm.setStuAsmScore(Float.parseFloat(Utils.getStrFromJsonObj(stuAnsJO, "s" + "tudentScore", "")));
            JSONArray answerItemArray = stuAnsJO.getJSONArray("a" + "nswerItemList");
            for (int j = 0; j < answerItemArray.length(); j++)
            {
                JSONObject stuAnsItemJO = answerItemArray.getJSONObject(j);
                teaUpdateStuAnswerItem(stuAnsJO, stuAnsItemJO, asmId, clsId, stuId);
            }

            log(tag + "answers updated");

            stuAsm.setBeginSendbackTime(Utils.currentSeconds());
            stuAsm.setStatusId(Constants.AsmStatus.SENDBACK);
            stuAsm.setIsCorrected(1);
            updateStuAsm(stuAsm);
            log(tag + "student asm updated");

            updateStatistics(asmId, clsId);
            log(tag + "statistics updated");

            int currentSeconds = Utils.currentSeconds();
            String beginTime = Utils.getFullTimeWithSecondsString(currentSeconds);
            // int 86400=24*60*60
            String endTime = Utils.getFullTimeWithSecondsString(currentSeconds + 86400);

            pushMessageToSingle(stuId, "收到批改！", "收到批改：" + stuAsm.getAsmName(), beginTime, endTime);
        }
    }

    // AbstractCommonProblem
    public JSONObject generateJsonObj(User pwdUser)
    {
        try
        {
            JSONObject tempJson = new JSONObject();
            if (pwdUser == null)
            {
                return tempJson;
            }
            tempJson.put("role", pwdUser.getUserRole());
            tempJson.put("name", pwdUser.getUserName());
            tempJson.put("email", pwdUser.getEmail());
            tempJson.put("loginId", pwdUser.getLoginId());
            tempJson.put("loginName", pwdUser.getLoginName());
            tempJson.put("password", pwdUser.getPassword());

            return tempJson;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public void updateUserPwd(User user, String pwd)
    {
        user.setPassword(pwd);
        basicMapper.updateUser(user);
    }

    // AbstractQueEditor
    public String saveUploadFile(FileBean toSaveFile) throws IOException
    {
        String savePath = Constants.PATH_FILE + "notificationImage/";
        Utils.makeSureDirExists(savePath);
        String fileName = toSaveFile.getFileName();
        String saveFileName = "notification_" + Utils.getNextLongId() + "." + Utils.getFileSuffix(fileName);

        toSaveFile.save(new File(savePath + saveFileName));
        return Constants.URL_FILE + "notificationImage/" + saveFileName;
    }

    public String saveWebImages(String webImagePathString)
    {
        StringBuilder webImageNameString = new StringBuilder();

        for (String webImagePath : Utils.splitStrList(webImagePathString, ","))
        {
            String webImageType = Utils.getWebImageSuffix(webImagePath);

            String imageFileName = "question_" + Utils.getNextLongId() + "." + webImageType;
            String imageFileDir = Constants.PATH_FILE + "question/image/";
            Utils.makeSureDirExists(imageFileDir);

            if (!Utils.saveWebFileToLocal(webImagePath, imageFileDir + imageFileName))
            {
                log("ERROR: save '" + webImagePath + "' failed. ");
                return "error";
            }

            webImageNameString.append(imageFileName + ",");
        }

        if (Utils.stringNotEmpty(webImageNameString.toString()))
        {
            return webImageNameString.substring(0, webImageNameString.length() - 1);
        }
        else
        {
            return webImageNameString.toString();
        }
    }

    public boolean deleteQuestionBank(int queId)
    {
        Que que = getQuestionBank(queId);
        que.setIsDeleted(1);
        try
        {
            updateQuestionBank(que);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void doInsertQuestionBank(Que que)
    {
        queMapper.insertQuestionBank(que);
    }

    // AbstractResList
    public void setTsClassifyModule(TextbookSelectorClassify tsClassifyModule, String classifySchemaItemId,
            List<Res> resourceList) throws NullPointerException
    {
        int resId = Integer.parseInt(classifySchemaItemId);
        Res res = findResByResId(resId, resourceList);
        if (classifySchemaItemId.equals("null_id") || res == null)
        {
            throw new NullPointerException();
        }

        tsClassifyModule.selectSubject(res.getSubjectId());
        tsClassifyModule.selectTextbook(res.getBookId());
        tsClassifyModule.selectChapter(res.getChapterId());
        // tsClassifyModule.selectSection(res.getSectionId());
    }

    public void doTextbookClassify(TextbookSelectorClassify tsClassifyModule, String classifySchemaItemId,
            List<Res> resourceList, int userId) throws Exception
    {
        int resId = Integer.parseInt(classifySchemaItemId);
        Res res = findResByResId(resId, resourceList);

        res.setBookId(tsClassifyModule.getCurrentTextbook().getBookId());
        res.setSubjectId(tsClassifyModule.getCurrentTextbook().getSubjectId());
        res.setChapterId(tsClassifyModule.getCurrentChapter().getChapterId());
        // res.setSectionId(tsClassifyModule.getCurrentSection().getSectionId());

        res.setUpdatorId(userId);
        res.setUpdateTime(Utils.currentSeconds());
        updateResourceInfo(res);
    }

    public void deleteResource(int resId, List<Res> resourceList, int userId) throws Exception
    {
        Res res = findResByResId(resId, resourceList);

        res.setIsDeleted(1);
        res.setUpdatorId(userId);
        res.setUpdateTime(Utils.currentSeconds());
        updateResourceInfo(res);
    }

    public Res findResByResId(int resId, List<Res> resourceList)
    {
        Res res = null;
        for (Res r : resourceList)
        {
            if (r.getResId() == resId)
            {
                res = r;
                break;
            }
        }
        return res;
    }

    public void setResUseCount(List<Res> resourceList)
    {
        for (Res res : resourceList)
        {
            res.setYjResUseCount(getYjResUseCount(res.getResId()));
        }
    }

    // private
    // --------------------------------------------------------------------------------
    private JSONObject saveStuAnswerZipAndGetJsonV2(StuAsm stuAsm, FileBean uploadFile)
            throws IOException, JSONException
    {
        String uploadDir = Constants.PATH_FILE + Utils.groupUserId(stuAsm.getStuId()) + "/";
        String unzipDir = uploadDir + stuAsm.getAsmId() + "_a/";
        String unzipPath = uploadDir + stuAsm.getAsmId() + "_a.zip";
        String targetDir = uploadDir + stuAsm.getAsmId() + "/";

        Utils.makeSureDirExists(uploadDir);

        uploadFile.save(new File(unzipPath));
        Utils.unzipFile(unzipPath, unzipDir);
        Utils.copyDir(unzipDir, targetDir);

        return new JSONObject(Utils.readFromFile(unzipDir + stuAsm.getAsmId() + ".json", "UTF-8"));
    }

    private void stuUpdateStuAnswerItem(JSONObject stuAnsJO, JSONObject stuAnsItemJO, int asmId, int clsId, int stuId)
            throws JSONException, DataInconsistException
    {

        int asmQueId = stuAnsJO.getInt("q" + "uestionId");
        int answerNum = stuAnsItemJO.getInt("a" + "nswerNum");

        StuAnswerItem tryStuAnsItem = getStuAnswerItem(asmId, clsId, stuId, asmQueId, answerNum);
        if (tryStuAnsItem != null)
        {
            return;
        }

        int answerType = stuAnsItemJO.getInt("a" + "nswerType");
        int createTime = Utils.getIntFromJsonObj(stuAnsItemJO, "c" + "reateTime", 0);
        int cycleNum = stuAnsItemJO.getInt("c" + "ycleNum");
        int isPiGai = stuAnsItemJO.getInt("i" + "sPiGai");

        String answerItem = Utils.getStrFromJsonObj(stuAnsItemJO, "a" + "nswerItem", "");
        if (answerType != Constants.AnswerItemType.TEXT)
        {
            if (Utils.stringEmpty(answerItem)
                    || !Utils
                            .fileExist(Constants.PATH_FILE + Utils.groupUserId(stuId) + "/" + asmId + "/" + answerItem))
            {
                throw new DataInconsistException();
            }
        }

        StuAnswerItem stuAnsItem = new StuAnswerItem();
        stuAnsItem.setAsmId(asmId);
        stuAnsItem.setClsId(clsId);
        stuAnsItem.setStuId(stuId);
        stuAnsItem.setAsmQueId(asmQueId);
        stuAnsItem.setAnswerNum(answerNum);

        stuAnsItem.setAnswerType(answerType);
        stuAnsItem.setAnswerItem(answerItem);
        stuAnsItem.setCreateTime(createTime);
        stuAnsItem.setCycleNum(cycleNum);
        stuAnsItem.setIsPiGai(isPiGai);

        asmMapper.insertStuAnswerItem(stuAnsItem);
    }

    private JSONObject teaSaveAnswerZipAndGetJsonV2(StuAsm stuAsm, int useUploadFile, HttpServletRequest request,
            FileBean uploadFile) throws IOException, JSONException
    {
        String baseDir = Constants.PATH_FILE + Utils.groupUserId(stuAsm.getStuId()) + "/";
        String allDir = baseDir + stuAsm.getAsmId() + "/";      // 作答+批改文件夹
        String unzipDir = baseDir + stuAsm.getAsmId() + "_c/";  // 批改文件夹
        String zipFilePath = baseDir + stuAsm.getAsmId() + "_c.zip";
        String jsonFileName = stuAsm.getAsmId() + ".json";

        if (useUploadFile == 1)
        {
            uploadFile.save(new File(zipFilePath));
        }
        else
        {
            byte[] uploadedBytes = Utils.getPostedBytes(request);

            Utils.writeFileAllBytes(zipFilePath, uploadedBytes);
        }

        Utils.unzipFile(zipFilePath, unzipDir);
        Utils.copyDir(unzipDir, allDir);

        return new JSONObject(Utils.readFromFile(unzipDir + jsonFileName, "UTF-8"));
    }

    private void teaUpdateStuAnswerItem(JSONObject stuAnsJO, JSONObject stuAnsItemJO, int asmId, int clsId, int stuId)
            throws JSONException, DataInconsistException
    {
        int asmQueId = stuAnsJO.getInt("q" + "uestionId");
        int answerNum = stuAnsItemJO.getInt("a" + "nswerNum");

        StuAnswerItem tryStuAnsItem = getStuAnswerItem(asmId, clsId, stuId, asmQueId, answerNum);
        if (tryStuAnsItem != null)
        {
            return;
        }

        int answerType = stuAnsItemJO.getInt("a" + "nswerType");
        int createTime = Utils.getIntFromJsonObj(stuAnsItemJO, "c" + "reateTime", 0);
        int cycleNum = stuAnsItemJO.getInt("c" + "ycleNum");
        int isPiGai = stuAnsItemJO.getInt("i" + "sPiGai");

        String answerItem = Utils.getStrFromJsonObj(stuAnsItemJO, "a" + "nswerItem", "");
        if (answerType != Constants.AnswerItemType.TEXT)
        {
            if (Utils.stringEmpty(answerItem)
                    || !Utils
                            .fileExist(Constants.PATH_FILE + Utils.groupUserId(stuId) + "/" + asmId + "/" + answerItem))
            {
                throw new DataInconsistException();
            }
        }

        StuAnswerItem stuAnsItem = new StuAnswerItem();
        stuAnsItem.setAsmId(asmId);
        stuAnsItem.setClsId(clsId);
        stuAnsItem.setStuId(stuId);
        stuAnsItem.setAsmQueId(asmQueId);
        stuAnsItem.setAnswerNum(answerNum);

        stuAnsItem.setAnswerType(answerType);
        stuAnsItem.setAnswerItem(answerItem);
        stuAnsItem.setCreateTime(createTime);
        stuAnsItem.setCycleNum(cycleNum);
        stuAnsItem.setIsPiGai(isPiGai);

        asmMapper.insertStuAnswerItem(stuAnsItem);
    }

}
