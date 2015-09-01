package com.kdl.nlfdc.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sourceforge.stripes.action.FileBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.domain.AdminMenu;
import com.kdl.nlfdc.domain.FirstMenu;
import com.kdl.nlfdc.domain.FirstMenuWithSecondMenu;
import com.kdl.nlfdc.domain.Image;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.SecondMenu;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.UserNotification;
import com.kdl.nlfdc.domain.VisitCount;
import com.kdl.nlfdc.exception.SqlAffectedCountException;
import com.kdl.nlfdc.persistence.BasicMapper;

@Service
public class CmService implements Serializable
{
    private static final long serialVersionUID = -5030426866333509214L;

    @Autowired
    protected BasicMapper basicMapper;

    
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
    
    public void deleteAdminMenu(int adminId)
    {
        basicMapper.deleteAdminMenu(adminId);
    }
    
    public void insertAdminMenu(AdminMenu adminMenu)
    {
        basicMapper.insertAdminMenu(adminMenu);
    }
    
    public int getAdminNotificationCount(int firstMenuId, int secondMenuId)
    {
        return basicMapper.getAdminNotificationCount(firstMenuId, secondMenuId);
    }
    
    public List<Notification> getAdminNotificationList(int firstMenuId, int secondMenuId, int limitBegin, int pageSize)
    {
        return basicMapper.getAdminNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);
    }
    
    public int getCommonNotificationCount(int firstMenuId, int secondMenuId)
    {
        return basicMapper.getCommonNotificationCount(firstMenuId, secondMenuId);
    }
    
    public List<Notification> getCommonNotificationList(int firstMenuId, int secondMenuId, int limitBegin, int pageSize)
    {
        return basicMapper.getCommonNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);
    }
    
    public int getSearchNotificationListCount(String searchText)
    {
        return basicMapper.getSearchNotificationListCount(searchText);
    }
    
    public List<Notification> getSearchNotificationList(String searchText, int limitBegin, int pageSize)
    {
        return basicMapper.getSearchNotificationList(searchText, limitBegin, pageSize);
    }
    
    public void deleteNotification(int notificationId)
    {
        basicMapper.deleteNotification(notificationId);
    }
    
    public void unDeleteNotification(int notificationId)
    {
        basicMapper.unDeleteNotification(notificationId);
    }

    public void insertAdminAndGetAdminId(Admin admin)
    {
        basicMapper.insertAdminAndGetAdminId(admin);
    }
    
    public void updateAdmin(Admin admin)
    {
        basicMapper.updateAdmin(admin);
    }
    
    public Admin getAdminByAdminId(int adminId)
    {
        return basicMapper.getAdminByAdminId(adminId);
    }
    
    public Admin getAdminByLoginName(String loginName)
    {
        return basicMapper.getAdminByLoginName(loginName);
    }
    
    public Admin getAdminByLoginNameIgnoreDelete(String loginName)
    {
        return basicMapper.getAdminByLoginNameIgnoreDelete(loginName);
    }
    
    public List<Admin> getAllCommonAdmin()
    {
        return basicMapper.getAllCommonAdmin();
    }
   
    public void disableAdmin(int adminId)
    {
        basicMapper.disableAdmin(adminId);
    }
    
    public void enableAdmin(int adminId)
    {
        basicMapper.enableAdmin(adminId);
    }
    
    public List<Image> getImageList(int type)
    {
        return basicMapper.getImageList(type);
    }
    
    public void updateImage(Image image)
    {
        basicMapper.updateImage(image);
    }
    
    public void updateImageNotificationId(int imageId, int notificationId)
    {
        basicMapper.updateImageNotificationId(imageId, notificationId);
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


}
