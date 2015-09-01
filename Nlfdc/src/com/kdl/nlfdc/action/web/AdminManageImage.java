package com.kdl.nlfdc.action.web;

import java.io.File;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Image;

/**
 * 超级管理员和普通管理员都用这个类
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
@SessionScope
@UrlBinding("/adminimagemanageaction")
public class AdminManageImage extends AbstractActionBean
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String MANAGE_IMAGE = "/WEB-INF/jsp/admin/AdminImageManage.jsp";

    private List<Image> newsImageList;      // 新闻图片
    private List<Image> rollImageList;      // 滚动图片
    private FileBean imageFile;
    private String newImageName;
    
    public FileBean getImageFile()
    {
        return imageFile;
    }
    public void setImageFile(FileBean imageFile)
    {
        this.imageFile = imageFile;
    }
    public List<Image> getNewsImageList()
    {
        return newsImageList;
    }
    public List<Image> getRollImageList()
    {
        return rollImageList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("manageuser")
    public Resolution manageUser()
    {
        logRequest();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.ADMIN_IMAGE_MANAGE);
        
        refreshImageList();
        return new ForwardResolution(MANAGE_IMAGE);
    }
   
    
    @HandlesEvent("uploadimage")
    public Resolution uploadImage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        if (imageFile == null)
        {
            return getStringResolution("error");
        }
        
        newImageName = Utils.getFullDateString(Utils.currentSeconds())+"_"+ Utils.currentSeconds() + "." + Utils.getFileSuffix(imageFile.getFileName()).toLowerCase();
        String saveDir = Constants.PATH_FILE + Constants.IMAGE_FOLDER;
        Utils.makeSureDirExists(saveDir);

        try
        {
            imageFile.save(new File(saveDir + newImageName));
            
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }
    
    
    @HandlesEvent("updateimage")
    public Resolution updateImage()
    {
        logRequest();
        if (!sessionIsValid() || imageFile == null || newImageName == null)
        {
            return getStringTimeoutResolution();
        }

        try
        {
            int imageId = getParamInt("imageId");
            
            if (imageId < 5)
            {
                // 新闻图片
                for (Image image : newsImageList)
                {
                    if (image.getImageId() == imageId)
                    {
                        image.setImageUrl( Constants.URL_FILE + Constants.IMAGE_FOLDER + newImageName);
                        cmService.updateImage(image);
                        return getStringResolution(image.getImageUrl());
                    }
                }
            }
            else
            {
                for (Image image : rollImageList)
                {
                    if (image.getImageId() == imageId)
                    {
                        image.setImageUrl( Constants.URL_FILE + Constants.IMAGE_FOLDER + newImageName);
                        cmService.updateImage(image);
                        
                        return getStringResolution(image.getImageUrl());
                    }
                }
            }
            
            return getStringResolution("error");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    // private 
    // ------------------------------------------------------------------------
    private void refreshImageList()
    {
        newsImageList = cmService.getImageList(1);
        
        rollImageList = cmService.getImageList(2);
        
    }
    
    
    // override
    // ------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return makeSureSuperAdmin() || makeSureCommonAdmin();
    }
    
}
