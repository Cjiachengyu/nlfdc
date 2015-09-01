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
    
    void deleteAdminMenu(int adminId);
    
    void insertAdminMenu(AdminMenu adminMenu);
    
    Admin getAdminByAdminId(int adminId);
    
    void insertAdminAndGetAdminId(Admin admin);
    
    Admin getAdminByLoginName(String loginName);
    
    Admin getAdminByLoginNameIgnoreDelete(String loginName);
    
    void disableAdmin(int adminId);

    void enableAdmin(int adminId);
    
    List<Admin> getAllCommonAdmin();
   
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
   
    int getSearchNotificationListCount(String searchText);

    List<Notification> getSearchNotificationList(
            @Param("searchText") String searchText,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);
    
    void deleteNotification(int notificationId);

    void unDeleteNotification(int notificationId);
    
    List<Image> getImageList(int type);
    
    void updateImage(Image image);
   
    void updateImageNotificationId(
            @Param("imageId") int imageId,
            @Param("notificationId") int notificationId);
   
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

}

