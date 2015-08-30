package com.kdl.nlfdc.action.web;

import java.io.IOException;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.springframework.dao.DuplicateKeyException;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.MenuSelector;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Image;
import com.kdl.nlfdc.domain.Notification;
import com.kdl.nlfdc.domain.User;

/**
 * 超级管理员和普通管理员都用这个类
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
@SessionScope
@UrlBinding("/adminnotificationmanageaction")
public class AdminManage extends AbstractActionBean
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String MANAGE = "/WEB-INF/jsp/admin/AdminNotificationManage.jsp";
    private static final String NOTIFICATION_LISTVIEW = "/WEB-INF/jsp/component/AdminNotificationListView.jsp";
    private static final String PUBLISH_NOTIFICATION_PAGE = "/WEB-INF/jsp/component/AdminNotificationPublish.jsp";
    private static final String MANAGE_ADMIN_VIEW_NOTIFICATION = "/WEB-INF/jsp/admin/AdminNotificationView.jsp";
    private static final String MANAGE_IMAGES_TO_LINK = "/WEB-INF/jsp/admin/ImagesToLink.jsp";

    private List<Notification> notificationList;
    private FileBean image;
    private Notification viewIngNotification;
    private List<Image> newsImageList;
    private List<Image> rollImageList;
    private int linkingImageNotificationId;

    public List<Image> getNewsImageList()
    {
        return newsImageList;
    }

    public List<Image> getRollImageList()
    {
        return rollImageList;
    }

    public Notification getViewIngNotification()
    {
        return viewIngNotification;
    }

    public List<Notification> getNotificationList()
    {
        return notificationList;
    }

    public FileBean getImage()
    {
        return image;
    }

    public void setImage(FileBean image)
    {
        this.image = image;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("commonmanage")
    public Resolution commonManage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.NOTIFICATION_MANAGE);

        pageModule = new PageModule(15);

        if (menuSelector == null || menuSelector.getCurrentFirstMenuId() == MenuSelector.MENU_ITEM_ALL)
        {
            menuSelector = initMenuSelector();
            menuSelector.selectDefaultFirstMenu();
        }

        refreshNotificationList();
        return new ForwardResolution(MANAGE);
    }

    @HandlesEvent("selectfirstmenu")
    public Resolution selectFirstMenu()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int firstMenuId = getParamInt("firstMenuId", 0);

        if (firstMenuId == menuSelector.getCurrentFirstMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            menuSelector.selectFirstMenu(firstMenuId);

            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
    }

    @HandlesEvent("selectsecondmenu")
    public Resolution selectSecondMenu()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int secondMenuId = getParamInt("secondMenuId", 0);

        if (secondMenuId == menuSelector.getCurrentSecondMenuId())
        {
            return getStringResolution("not_change");
        }
        else
        {
            System.out.println("select secondMenu " + secondMenuId);
            menuSelector.selectSecondMenu(secondMenuId);

            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
    }

    @HandlesEvent("turntopublishpage")
    public Resolution turnToPublishPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        return new ForwardResolution(PUBLISH_NOTIFICATION_PAGE);
    }

    /**
     * 通知中插入图片
     * 
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
            System.out.println("image == null");
            return getStringTimeoutResolution();
        }

        try
        {
            String fileUrl = cmService.saveUploadFile(image);
            return getStringResolution(fileUrl);
        }
        catch (IOException e)
        {
            System.out.println("upload_image_exception");
            return getStringResolution("upload_image_exception");
        }
    }

    @HandlesEvent("dopublishnotification")
    public Resolution doPublishNotification()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        String title = getParam("title");
        String content = getParam("content");

        if (paramIsValid(title, content))
        {
            try
            {
                Notification notification = new Notification();
                notification.setCreatorId(getCurrentAdminId());
                notification.setCreateTime(Utils.currentSeconds());
                notification.setFirstMenuId(menuSelector.getCurrentFirstMenuId());
                notification.setSecondMenuId(menuSelector.getCurrentSecondMenuId());
                notification.setTitle(title);
                notification.setContent(content);

                cmService.insertNotification(notification);

                return getStringResolution("ok");
            }
            catch (Exception e)
            {
                return getStringResolution("exception");
            }
        }
        else
        {
            return getStringResolution("paramInValid");
        }
    }


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
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
        catch (Exception e)
        {
            return getStringResolution("exeption");
        }
    }

    @HandlesEvent("undeletenotification")
    public Resolution unDeleteNotification()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int notificationId = getParamInt("notificationId");

        try
        {
            cmService.unDeleteNotification(notificationId);
            refreshNotificationList();
            return new ForwardResolution(NOTIFICATION_LISTVIEW);
        }
        catch (Exception e)
        {
            return getStringResolution("exeption");
        }
    }

    @HandlesEvent("viewnotification")
    public Resolution viewNotification()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int notificationId = getParamInt("notificationId");

        viewIngNotification = cmService.getNotification(notificationId);

        if (viewIngNotification == null)
        {
            return new ForwardResolution(MANAGE);
        }
        else
        {
            return new ForwardResolution(MANAGE_ADMIN_VIEW_NOTIFICATION);
        }
    }

    @HandlesEvent("getlinkimageview")
    public Resolution getLinkImageView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        linkingImageNotificationId = getParamInt("linkingImageNotificationId");

        newsImageList = cmService.getImageList(1);
        rollImageList = cmService.getImageList(2);

        return new ForwardResolution(MANAGE_IMAGES_TO_LINK);
    }

    @HandlesEvent("dolinkimage")
    public Resolution doLinkImage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        int imageId = getParamInt("imageId");

        try
        {
            cmService.updateImageNotificationId(imageId, linkingImageNotificationId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return getStringResolution("error");
        }
    }

    // private
    // ------------------------------------------------------------------------
    private void refreshNotificationList()
    {
        int adminId = getCurrentAdminId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();
        int firstMenuId = menuSelector.getCurrentFirstMenuId();
        int secondMenuId = menuSelector.getCurrentSecondMenuId();

        log("admin refresh notification adminId: " + adminId + ", firstMenuId: " + firstMenuId + ", secondMenuId: "
                + secondMenuId
                + " limitBegin: " + limitBegin + ", pageSize: " + pageSize);

        int notificationCount = cmService.getAdminNotificationCount(firstMenuId, secondMenuId);

        notificationList = cmService.getAdminNotificationList(firstMenuId, secondMenuId, limitBegin, pageSize);

        pageModule.changeItemsCount(notificationCount);

        log("admin refresh notification result, notificationCount: " + notificationCount + ", listSize: " +
                notificationList.size());
    }

    private boolean paramIsValid(String title, String content)
    {
        if (!title.equals("") && !content.equals("") && title.length() < Constants.MaxLength.NOTIFICATION_TITLE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // override
    // ------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return makeSureSuperAdmin() || makeSureCommonAdmin();
    }

}
