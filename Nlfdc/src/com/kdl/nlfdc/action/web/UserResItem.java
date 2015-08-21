package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;

@SessionScope
@UrlBinding("/userresourceitemaction")
public class UserResItem extends AbstractActionBean
{
    private static final long serialVersionUID = 1840576784329274263L;

    protected static final String MAIN_VIDEO_VIEW = "/WEB-INF/jsp/user/UserResourceVideoViewNew.jsp";
    protected static final String MAIN_IMG_VIEW = "/WEB-INF/jsp/user/UserResourceImgView.jsp";
    protected static final String MAIN_AUDIO_VIEW = "/WEB-INF/jsp/user/UserResourceAudioViewNew.jsp";
    protected static final String MAIN_AUDIO_MOBILE_VIEW = "/WEB-INF/jsp/user/UserResourceAudioMobileViewNew.jsp";
    protected static final String MAIN_WORD_PAGE = "/WEB-INF/jsp/user/UserResourceTextFileView.jsp";
    protected static final String TURN_TO_DOWNLOAD_PAGE = "/WEB-INF/jsp/user/UserResourceTurnToDownload.jsp";

    // view
    // --------------------------------------------------------------------------------
    protected Res resource;
    protected List<ResFile> resourceFileList; // 板书功能用到
    protected String fileUrl = "";            // vedio、img、audio公用
    protected String fileType = "";
    protected int isMobile = 0;
    
    
    public int getIsMobile()
    {
        return isMobile;
    }

    public Res getResource()
    {
        return resource;
    }

    public List<ResFile> getResourceFileList()
    {
        return resourceFileList;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }

    public String getFileType()
    {
        return fileType;
    }

    // private
    // --------------------------------------------------------------------------------

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("gotoresourceitem")
    public Resolution gotoResourceItem()
    {
        logRequest();

        if (getCurrentUser() == null)
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId");
        isMobile = getParamInt("isMobile", 0);
        
        resource = cmService.getResourceInfo(resId);
        resourceFileList = cmService.getResourceFileByResourceId(resId);

        if (resource.getResType() == Constants.ResourceType.FILE)
        {
            // 除了板书以外其他的都是一个资源对应一个文件
            for (ResFile resourceFile : resourceFileList)
            {
                fileType = resourceFile.getFileSuffix().toLowerCase();
                fileUrl = resourceFile.getFileUrl();
                
                // 视频
                if (Constants.ResourceFileType.PLAYABLE_VIDEO_TYPE_LIST.contains(fileType))
                {
                    return new ForwardResolution(MAIN_VIDEO_VIEW);
                }

                // 图片
                if (Constants.ResourceFileType.IMG_TYPE_LIST.contains(fileType))
                {
                    if (isMobile == 1)
                    {
                        return new ForwardResolution(MAIN_IMG_VIEW);
                    }
                    else 
                    {
                        return getStringResolution("#_image_#"+fileUrl);
                    }
                }

                // 音频
                if (Constants.ResourceFileType.PLAYABLE_AUDIO_TYPE_LIST.contains(fileType))
                {
                    if (isMobile == 1)
                    {
                        return new ForwardResolution(MAIN_AUDIO_MOBILE_VIEW);
                    }
                    else
                    {
                        return new ForwardResolution(MAIN_AUDIO_VIEW);
                    }
                }

                // txt, pdf ..
                if (Constants.ResourceFileType.WORD_TYPE_LIST.contains(fileType))
                {
                    if (isMobile == 1)
                    {
                        return new ForwardResolution(TURN_TO_DOWNLOAD_PAGE);
                    }
                    else
                    {
                        return new ForwardResolution(MAIN_WORD_PAGE);
                    }
                }
            }

            return new ForwardResolution(TURN_TO_DOWNLOAD_PAGE);
        }
        else
        {
            log("ERROR: unknown resType");
            return new ForwardResolution(TURN_TO_DOWNLOAD_PAGE);
        }
    }

    @HandlesEvent("deletecheckedfile")
    public Resolution deleteCheckedFile()
    {
        logRequest();

        if (getCurrentUser() == null)
        {
            return getStringTimeoutResolution();
        }

        String checkedFileIdList = getParam("checkedFileIdStringArray");
        String[] fileIdStringArray = checkedFileIdList.trim().split("-");
        for (int i = 0; i < fileIdStringArray.length; i++)
        {
            log("delete file: " + fileIdStringArray[i]);
            ResFile resourceFile = cmService.getResourceFile(Integer.parseInt(fileIdStringArray[i]));
            resourceFile.setIsDeleted(1);
            cmService.updateResourceFile(resourceFile);
        }

        int resId = getParamInt("resId", 0);
        resourceFileList = cmService.getResourceFileByResourceId(resId);

        return getStringResolution("ok");
    }

    @HandlesEvent("handledownloadrequest")
    public Resolution handleDownloadRequest()
    {
        return downloadResourceFile();
    }

    // private
    // --------------------------------------------------------------------------------
    private Resolution downloadResourceFile()
    {
        if (getParamInt("isMobile", 0) == 1)
        {
            int resId = getParamInt("resId");
            resource = cmService.getResourceInfo(resId);
            resourceFileList = cmService.getResourceFileByResourceId(resId);
        }
        
        ResFile resourceFile = resourceFileList.get(0);
        String resName = resource.getResName();
        
        if (resName.contains("."))
        {
            resName = resName.substring(0, resName.lastIndexOf("."));
        }
        resName = resName + "." + resourceFile.getFileSuffix();

        return getFileResolution(resourceFile.getFilePath(), resName);
    }
}
