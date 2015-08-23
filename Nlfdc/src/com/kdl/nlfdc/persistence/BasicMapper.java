package com.kdl.nlfdc.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kdl.nlfdc.domain.*;

public interface BasicMapper
{
    VisitCount getVisitCount(int day);
    
    int getSumVisitCount();

    void insertVisitCount(VisitCount visitCount);
    
    void updateVisitCount(VisitCount visitCount);
   
    FirstMenu getFirstMenu(int firstMenuId);
    
    List<FirstMenu> getAllFirstMenu();

    List<FirstMenuWithSecondMenu> getAllFirstMenuWithSecondMenu();
    
    SecondMenu getSecondMenu(int secondMenuId);
    
    List<SecondMenu> getSecondMenuByFirstMenuId(int firstMenuId);
    
    List<AdminMenu> getAdminMenus(int adminId);
    
    Admin getAdminByAdminId(int adminId);
    
    Admin getAdminByLoginName(String loginName);
   
    int getAdminNotificationCount(
            @Param("firstMenuId") int firstMenuId,
            @Param("secondMenuId") int secondMenuId);
    
    List<Notification> getAdminNotificationList(
            @Param("firstMenuId") int firstMenuId,
            @Param("secondMenuId") int secondMenuId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize
            );
    
    int getCommonNotificationCount(
            @Param("firstMenuId") int firstMenuId,
            @Param("secondMenuId") int secondMenuId);
    
    List<Notification> getCommonNotificationList(
            @Param("firstMenuId") int firstMenuId,
            @Param("secondMenuId") int secondMenuId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize
            );
    
    
    void deleteNotification(int notificationId);

    void unDeleteNotification(int notificationId);
     
    
    // old
    // ------------------------------------------------------
    List<User> getTeacherListForAStudent(int userId);

    List<User> getAllStudentsOfAClass(int clsId);

    List<User> getUserIdListByPage(
            @Param("schoolId") int schoolId,
            @Param("subjectId") int subjectId,
            @Param("userRole") int userRole,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    User getUser(int userId);

    int deleteUser(int userId);

    User getUserByOpenid(String openid);

    User getUserByCellphoneAndSchoolId(
            @Param("cellphone") String cellphone,
            @Param("schoolId") int schoolId);

    User getUserByLoginIdAndSchoolId(
            @Param("loginId") String loginId,
            @Param("schoolId") int schoolId);

    User getUserByLoginNameAndSchoolId(
            @Param("loginName") String loginName,
            @Param("schoolId") int schoolId);

    User getUserByEmailAndSchoolId(
            @Param("email") String email,
            @Param("schoolId") int schoolId);

    int insertUserAndGetUserId(User user);

    int updateUser(User user);

    int updateSchemaCache(User user);

    MachineLicense getMachineLicense(String licenseCode);

    void updateMachineLicense(MachineLicense machineLicense);

    List<User> getSchoolUsers(
            @Param("schoolId") int schoolId,
            @Param("userRole") int userRole);

    int getCountOfSchoolMember(
            @Param("schoolId") int schoolId,
            @Param("userRole") int userRole,
            @Param("subjectId") int subjectId);

    List<User> getUserByClassId(int clsId);

    int insertUserFindPassword(
            @Param("userId") int userId,
            @Param("time") int time,
            @Param("isUsed") byte isUsed);

    // login
    // --------------------------------------------------------------------------------
    Login getLogin(int userId);

    Login getLoginByTokenId(String tokenId);

    void updateLogin(Login login);
    
    List<Login> getLoginByClientId(String clientId);
    
    void updateClientId(Login login);

    void updateAndroidLogin(Login login);

    void insertLogin(Login login);

    void insertAndroidLogin(Login login);

    void deleteLogin(int userId);

    // user_cls
    // --------------------------------------------------------------------------------
    int getCountOfClassMember(
            @Param("clsId") int clsId,
            @Param("userRole") int userRole);

    int insertUserClass(
            @Param("userId") int userId,
            @Param("clsId") int clsId);

    int deleteUserClass(
            @Param("userId") int userId,
            @Param("clsId") int clsId);

    int deleteUserClassByClassId(int clsId);

    List<Scm> getAllSchema();

    Scm getSchema(int scmId);

    List<Scm> getSchemaBySchoolId(int schoolId);

    List<Scm> getSchemaOutSchool(
            @Param("schoolId") int schoolId);

    List<Integer> getSchemaIdBySchemaName(String scmName);

    List<Book> getAllTextbook();

    List<Book> getTextbookForStudent(
            @Param("schoolId") int schoolId,
            @Param("stuId") int stuId);

    List<Book> getTextbookOfASchool(int schoolId);

    List<Book> getTextbookBySchemaId(int scmId);

    List<Book> getTextbookForTeacherExceptFav(
            @Param("schoolId") int schoolId,
            @Param("userId") int userId);

    List<Book> getTextbookOutOfSchema(
            @Param("scmId") int scmId,
            @Param("category") int category,
            @Param("subjectId") int subjectId);
    
    int getTextbookCountForAdmin(
            @Param("scmId") int scmId,
            @Param("subjectId") int subjectId,
            @Param("category") int category,
            @Param("grade") int grade);
    
    List<Book> getTextbookForAdmin(
            @Param("scmId") int scmId,
            @Param("subjectId") int subjectId,
            @Param("category") int category,
            @Param("grade") int grade,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);
    
    Book getTextbook(int bookId);

    List<Integer> getTextbookIdByTextbookName(String bookName);

    List<Integer> getSubjectIdBySubjectName(String subjectName);

    List<Chapter> getChapterByTextId(int bookId);

    List<Chapter> getAllChapter();

    List<Section> getSectionByChapterId(int chapterId);

    List<Section> getSectionByTextId(int bookId);

    void insertChapter(Chapter chapter);

    void insertSection(Section section);

    int insertTextbook(Book textbook);

    void insertSchemaTextbook(Book textbook);

    int insertSchema(Scm schema);

    int deleteSchemaTextbook(
            @Param("selectedSchemaId") int selectedSchemaId,
            @Param("bookId") int bookId);

    int updateTextbook(Book textbook);

    void updateChapter(Chapter chapter);

    void updateSection(Section section);

    void deleteChapter(int chapterId);

    void deleteSection(int sectionId);

    /**
     * 2014.8.8 added cjia 根据班级id查出该班级所有老师的id
     */
    List<User> getTeachersOfClass(
            @Param("clsId") int clsId,
            @Param("subjectId") int subjectId);

    List<User> getUsersOfSchoolBySearchName(
            @Param("schoolId") int schoolId,
            @Param("inClassId") int inClassId,
            @Param("outClassId") int outClassId,
            @Param("searchName") String searchName,
            @Param("userRole") int userRole);

    List<Book> getTextbookForTeacher(int userId);

    int deleteTeacherTextbook(
            @Param("userId") int userId,
            @Param("bookId") int bookId);

    int insertTeacherTextbook(TeaBook tt);

    List<User> getUsersOutOfClassByRole(
            @Param("schoolId") int schoolId,
            @Param("inClassId") int inClassId,
            @Param("outClassId") int outClassId,
            @Param("userRole") int userRole);

    List<User> getUsersInClassByRole(
            @Param("inClassId") int inClassId,
            @Param("userRole") int userRole);

    int deleteTeacherTextbookBySubjectId(
            @Param("subjectId") int subjectId,
            @Param("teaId") int teaId);

    List<TeaBook> getTeacherTextbook(
            @Param("bookId") int bookId,
            @Param("teaId") int teaId);

    int deleteUserClassByUserId(int userId);

    List<Book> getTextbookBySchoolId_SubjectId(
            @Param("subjectId") int subjectId,
            @Param("schoolId") int schoolId);

    int deleteTeacterTextbookAllByTeacherId(int teaId);

    int insertUserSubject(TeaSubject teaSubject);

    int deleteUserSubject(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId);

    int deleteUserSubjectByUserId(int teaId);

    School getSchool(int schoolId);

    List<School> getCategoryedSchoolInRegion(
            @Param("regionId") int regionId,
            @Param("categoryId") int categoryId);

    List<School> getAllSchoolInCities(
            @Param("provinceId") String provinceId,
            @Param("cityId") String cityId,
            @Param("category") int category);

    void insertSchool(School school);

    int updateSchool(School school);

    void deleteSchool(int schoolId);

    List<Province> getAllProvince();

    City getCity(String cityId);

    List<City> getCityByProvinceId(String provinceId);

    Region getRegion(int regionId);

    // cls
    // --------------------------------------------------------------------------------
    Cls getClassInfo(int clsId);

    List<Cls> getClassByGrade(
            @Param("schoolId") int schoolId,
            @Param("entranceYear") int entranceYear);

    int insertClass(Cls classInfo);

    int updateClass(Cls classInfo);

    int deleteClass(int clsId);

    List<Integer> getClassEnterYearList(int schoolId);

    int getSchoolClassCount(int schoolId);

    List<Cls> getClassListOfAUser(int userId);

    List<Cls> getOtherClassListOfASchool(
            @Param("schoolId") int schoolId,
            @Param("clsId") int clsId);
    
    // schema
    // --------------------------------------------------------------------------------
    List<SchoolScm> getSchoolSchemaBySchoolId(int schoolId);

    int insertSchoolSchema(SchoolScm schoolSchema);

    int deleteSchoolSchema(
            @Param("schoolId") int schoolId, 
            @Param("scmId") int scmId);

    // subject
    // --------------------------------------------------------------------------------
    List<Subject> getAllSubject();

    Subject getSubject(int subjectId);

    List<Subject> getTeacherSubjects(int teaId);

    List<Subject> getSchoolSubjects(int schoolId);

    // remark
    // --------------------------------------------------------------------------------
    // to-be-deleted
    List<Remark> getAllRemark();
    
    // notification 
    // --------------------------------------------------------------------------------
    Notification getNotification(int notificationId);
    
    int getSelfCreatedNotificationCount(
            @Param("userId") int userId,
            @Param("createTime") int createTime);
    
    List<Notification> getSelfCreatedNotification(
            @Param("userId") int userId,
            @Param("createTime") int createTime,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);
    
    int getReceivedNotificationCount(
            @Param("userId") int userId,
            @Param("createTime") int createTime);
    
    List<Notification> getReceivedNotification(
            @Param("userId") int userId,
            @Param("createTime") int createTime,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);
    
    int insertNotification(Notification notification);
    
    
    void insertUserNotification(List<UserNotification> userNotificationList);
    
    void setUserNotificationReaded(
            @Param("userId") int userId,
            @Param("notificationId") int notificationId);
    
    int getOneNotificationReceiveCount(int notificationId);
    
    int getOneNotificationReadedCount(int notificationId);
   
    int getUnReadedReceivedNotificationCount(int userId);
    
    // UserChatToken
    // --------------------------------------------------------------------------------
    UserChatToken getUserChatToken(int userId);
    
    void insertUserChatToken(UserChatToken userChatToken);
   
    List<User> getAllUsersOfSchoolByUserRole(
            @Param("schoolId") int schoolId,
            @Param("userRole") int userRole);

    
}

