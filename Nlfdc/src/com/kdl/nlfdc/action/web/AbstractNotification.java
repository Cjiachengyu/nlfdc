package com.kdl.nlfdc.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.NotificationInfo;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.UserNotification;

/**
 * 通知处理类，负责发送通知，查看通知
 * 
 * @author cjia
 *
 * @version 创建时间：2015年8月4日
 */
@SessionScope
public abstract class AbstractNotification extends AbstractActionBean
{
    private static final long serialVersionUID = -5910515759401308416L;

    protected static final String NOTIFICATION_LIST_VIEW = "/WEB-INF/jsp/component/NotificationListView.jsp";
    protected static final String RECEIVED_NOTIFICATION_LIST_VIEW = "/WEB-INF/jsp/component/ReceivedNotificationListView.jsp";
    protected static final String VIEW_NOTIFICATION      = "/WEB-INF/jsp/component/NotificationView.jsp";
    protected static final String PUBLISH_NOTIFICATION_PAGE = "/WEB-INF/jsp/component/CommonNotificationPublish.jsp";

    private Notification     viewIngNotification;
    private NotificationInfo viewIngNotificationInfo;
    protected List<Cls>      classesToChoose;
    protected FileBean       image;
    protected int createTime;
    protected String dateString = Utils.getFullDateString(Utils.currentSeconds());
    protected List<Notification> notificationList;
    
    public Notification getViewIngNotification()
    {
        return viewIngNotification;
    }
    public NotificationInfo getViewIngNotificationInfo()
    {
        return viewIngNotificationInfo;
    }
    public FileBean getImage()
    {
        return image;
    }
    public void setImage(FileBean image)
    {
        this.image = image;
    }
    public List<Cls> getClassesToChoose()
    {
        return classesToChoose;
    }
    public int getCreateTime()
    {
        return createTime;
    }
    public String getDateString()
    {
        return dateString;
    }
    public List<Notification> getNotificationList()
    {
        return notificationList;
    }

    
    // resolution
    // --------------------------------------------------------------------------------
    @HandlesEvent("deletenotification")
    public Resolution deleteNotification()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int notificationId = getParamInt("notificationId");

        try
        {
            cmService.deleteNotification(notificationId);
            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LIST_VIEW);
        }
        catch (Exception e)
        {
            return getStringResolution("exeption");
        }
    }

    @HandlesEvent("selectdate")
    public Resolution selectDate()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        try
        {
            log(getParam("dateString"));
            createTime = (int) (Constants.SDF_FULL_TIME.parse(getParam("dateString") + " 23:59:59").getTime() / 1000);
        }
        catch (Exception e)
        {
            createTime = Utils.currentSeconds();
        }

        refreshNotificationList();
        
        int isGet = getParamInt("isGet", 0);
        if (isGet == 1)
        {
            return new ForwardResolution(RECEIVED_NOTIFICATION_LIST_VIEW);
        }
        else
        {
            return new ForwardResolution(NOTIFICATION_LIST_VIEW);
        }
    }
    
    /**
     * 通知中插入图片
     * @return
     * @throws IOException
     */
    @HandlesEvent("uploadimage")
    public Resolution uploadImage() throws IOException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        if (image == null)
        {
            return getStringTimeoutResolution();
        }

        try
        {
            String fileUrl = cmService.saveUploadFile(image);
            return getStringResolution(fileUrl);
        }
        catch (IOException e)
        {
            return getStringResolution("upload_image_exception");
        }
    }
   
    protected abstract void refreshNotificationList();
    
    protected abstract Resolution getNotificationMainPage();
    
    protected boolean paramIsValid(String title, String content, String targetType, String classIdsStr)
    {
        if (!title.equals("") && !content.equals("") && title.length() < Constants.MaxLength.NOTIFICATION_TITLE)
        {
            if (targetType.equals(Constants.NotificationTargetType.SOME_CLASS) && classIdsStr.equals(""))
            {
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    protected List<Integer> getTargetUserIdList(User creator, String targetType, String classIdsStr)
    {
        if (targetType.equals(Constants.NotificationTargetType.ALL_USER))
        {
            //全园
            List<User> targetUserList = cmService.getAllUsersOfSchoolByUserRole(creator.getSchoolId(), 0);
            return getUserIdList(targetUserList);
        }
        else if (targetType.equals(Constants.NotificationTargetType.ALL_TEACHER))
        {
            //全体老师
            List<User> targetUserList = cmService.getAllUsersOfSchoolByUserRole(creator.getSchoolId(), Constants.UserRole.YJ_TEACHER);
            return getUserIdList(targetUserList);
        }
        else if (targetType.equals(Constants.NotificationTargetType.ALL_PARENT))
        {
            //全体家长
            List<User> targetUserList = cmService.getAllUsersOfSchoolByUserRole(creator.getSchoolId(), Constants.UserRole.YJ_PARENT);
            return getUserIdList(targetUserList);
        }
        else if (targetType.equals(Constants.NotificationTargetType.SOME_CLASS))
        {
            //部分班级
            if (creator.getUserRole() == Constants.UserRole.YJ_MASTER)
            {
                //校长，查询班级所有用户
                List<User> allClassUsers = new ArrayList<User>();
                String[] classIds = classIdsStr.split(",");
                
                for (String classId : classIds)
                {
                    List<User> classUsers = cmService.getUserByClassId(Integer.parseInt(classId));
                    allClassUsers.addAll(classUsers);
                }
                
                return getUserIdList(allClassUsers);
            }
            else if(creator.getUserRole() == Constants.UserRole.YJ_TEACHER)
            {
                //老师，查询班级家长
                List<User> allClassParents = new ArrayList<User>();
                
                String[] classIds = classIdsStr.split(",");
                
                for (String classId : classIds)
                {
                    List<User> classParents = cmService.getAllStudentsOfAClass(Integer.parseInt(classId));
                    allClassParents.addAll(classParents);
                }
                
                return getUserIdList(allClassParents);
            }
        }
        
        return null;
    }
    
    protected List<UserNotification> getTargetUserNotificationList(int notificationId, List<Integer> allTargetUserIdList)
    {
        List<UserNotification> allUserNotificationList = new ArrayList<UserNotification>();
        for(Integer i : allTargetUserIdList)
        {
            UserNotification userNotification = new UserNotification();
            userNotification.setUserId(i);
            userNotification.setNotificationId(notificationId);
            allUserNotificationList.add(userNotification);
        }
        
        return allUserNotificationList;
    }
    
    protected void pushMessage(String notifyTargetType, String notificationTitle, List<Integer> allTargetUserIdList)
    {
        String messageTitle = getPushMessageTitle(notifyTargetType);
        int now = Utils.currentSeconds();
        String beginTime = Utils.getFullTimeWithSecondsString(now);
        // int 86400=10*24*60*60
        String endTime = Utils.getFullTimeWithSecondsString(now + 864000);
        for(Integer i : allTargetUserIdList)
        {
            cmService.pushMessageToSingle(i, messageTitle, notificationTitle, beginTime, endTime);
        }
    }
    
    // private
    // --------------------------------------------------------------------------------
    private List<Integer> getUserIdList(List<User> targetUserList)
    {
        List<Integer> targetUserIdList = new ArrayList<Integer>();
        for(User user : targetUserList)
        {
            targetUserIdList.add(user.getUserId());
        }
        
        return targetUserIdList;
    }
    
    private String getPushMessageTitle(String notifyTargetType)
    {
        if (notifyTargetType.equals(Constants.NotificationTargetType.ALL_USER))
        {
            return Constants.NotificationTargetType.ALL_USER_STRING;
        }
        else if (notifyTargetType.equals(Constants.NotificationTargetType.ALL_TEACHER))
        {
            return Constants.NotificationTargetType.ALL_TEACHER_STRING;
        }
        else if (notifyTargetType.equals(Constants.NotificationTargetType.ALL_PARENT))
        {
            return Constants.NotificationTargetType.ALL_PARENT_STRING;
        }
        else
        {
            return Constants.NotificationTargetType.SOME_CLASS_STRING;
        }
    }
    
}
