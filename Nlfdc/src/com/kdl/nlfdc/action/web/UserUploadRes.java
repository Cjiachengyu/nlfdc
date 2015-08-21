package com.kdl.nlfdc.action.web;

import java.io.File;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

@SessionScope
@UrlBinding("/uploadtresourceaction")
public class UserUploadRes extends AbstractActionBean
{
    private static final long serialVersionUID = 5499138486413915304L;

    private FileBean file;
    private String resName;
    private String filePath;
    private String fileMd5;

    private int resTag;
    private int bookId;
    private int chapterId;
    private int sectionId;
    private int shareToSchool;

    private int resourceFromType;
    private int resType;
    private int subjectId;
    private User currentUser;

    // 记录前一个res的上传时间，如果批量上传文件时，避免所有的资源的上传时间相同，这样会使资源的上移下移出现问题
    private int preResUploadTime;   

    public void setFile(FileBean file)
    {
        this.file = file;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("uploadresourcefile")
    public Resolution uploadResourceFile()
    {
        logRequest();
        
        if (file == null || getCurrentUser() == null)
        {
            return null;
        }

        try
        {
            String savePath = Constants.PATH_FILE + Utils.groupUserId(getCurrentUserId()) + "/resource/";
            Utils.makeSureDirExists(savePath);

            String fileName = file.getFileName();
            if (fileName.length() > Constants.MaxLength.RES_NAME)
            {
                fileName = fileName.substring(0, 80);
            }
            
            resName = fileName;
            File tempFile = new File(savePath + fileName);
            file.save(tempFile);

            fileMd5 = Utils.getMd5(savePath + fileName);
            filePath = savePath + fileMd5 + "-" + file.getSize() + "." + Utils.getFileSuffix(fileName);

            File destFile = new File(filePath);
            if (destFile.exists())
            {
                tempFile.delete();
            }
            else
            {
                tempFile.renameTo(destFile);
            }

            saveResource(resourceFromType, resType, currentUser, subjectId);
        }
        catch (Exception e)
        {
        }
        return null;
    }

    @HandlesEvent("receiveparam")
    public Resolution receviceParam()
    {
        logRequest();
        currentUser = getCurrentUser();
        if (currentUser == null)
        {
            return getStringTimeoutResolution();
        }

        resTag = getParamInt("resTag", -1);
        bookId = getParamInt("bookId", -1);
        chapterId = getParamInt("chapterId", 0);
        sectionId = getParamInt("sectionId", 0);
        shareToSchool = getParamInt("shareToSchool", -1);

        if (!validParams(resTag, bookId, shareToSchool))
        {
            return getStringResolution("invalid_params");
        }

        // 上传文件之前先上传资源的参数，同时生成公用的其他信息（可能同时上传多个资源）
        resourceFromType = getResourceFromType(currentUser);
        resType = Constants.ResourceType.FILE;
        subjectId = bookId == 0 ? 0 : cmService.getTextbook(bookId).getSubjectId();

        return getStringResolution("ok");
    }


    // private
    // --------------------------------------------------------------------------
    private boolean validParams(int resTag, int bookId, int shareToSchool)
    {
        if (resTag == -1 || bookId == -1 || shareToSchool == -1)
        {
            return false;
        }
        return true;
    }

    private void insertResourceFile(Res r)
    {
        ResFile rf = new ResFile();
        rf.setResId(r.getResId());
        rf.setFilePath(filePath);
        rf.setFileSuffix(Utils.getFileSuffix(resName));
        rf.setFileSize((int) file.getSize());
        rf.setFileMd5(fileMd5);

        cmService.insertResourceFile(rf);
    }

    private Res insertResourceInfo(int resTag, int bookId, int chapterId, int sectionId,
            int resourceFromType, int resType, int creatorId, int subjectId, int now)
    {
        Res r = new Res();
        r.setResName(resName);
        r.setResType(resType);
        r.setFromType(resourceFromType);
        r.setResTag(resTag);

        r.setCreatorId(creatorId);
        r.setCreateTime(now);
        r.setUpdatorId(creatorId);
        r.setUpdateTime(now);
        r.setResOrder(now);

        r.setSubjectId(subjectId);
        r.setBookId(bookId);
        r.setChapterId(chapterId);
        r.setSectionId(sectionId);

        cmService.insertResourceInfo(r);
        return r;
    }

    private void shareResource(int shareToSchool, int creatorId, int now, Res r)
            throws NumberFormatException, SqlAffectedCountException
    {
        r.setTeaId(creatorId);
        r.setSchoolId(getCurrentUser().getSchoolId());
        r.setShareToSchool(shareToSchool);

        cmService.insertResourceEntity(r);
    }

    private int getResourceFromType(User currentUser)
    {
        int resourceFromType;
        switch (currentUser.getUserRole())
        {
        case Constants.UserRole.YJ_TEACHER:
            resourceFromType = Constants.ResourceFromType.FROM_PERSONAL;
            break;
        case Constants.UserRole.YJ_EDITOR:
        case Constants.UserRole.YJ_ADMIN:
            resourceFromType = Constants.ResourceFromType.FROM_COMPANY;
            break;
        default:
            resourceFromType = Constants.ResourceFromType.FROM_PERSONAL;
        }
        return resourceFromType;
    }

    private void saveResource(int resourceFromType, int resType, User currentUser, int subjectId)
            throws NumberFormatException, SqlAffectedCountException
    {
        // 不管是老师还是编辑都设置userId为creatorId
        int creatorId = currentUser.getUserId();
        int now = Utils.currentSeconds();
        // 批量上传文件时，强行区分上传时间
        if (now <= preResUploadTime)
        {
            now = preResUploadTime + 1;
        }
        preResUploadTime = now;
        
        Res r = insertResourceInfo(resTag, bookId, chapterId, sectionId, resourceFromType, resType,
                creatorId, subjectId, now);

        if (currentUser.getUserRole() == Constants.UserRole.YJ_TEACHER
                || currentUser.getUserRole() == Constants.UserRole.YJ_TEACHER)
        {
            // 如果是老师则插入一条ResourceEntity,然后再共享资源到班级
            shareResource(shareToSchool, creatorId, now, r);
        }

        // 插入资源文件
        insertResourceFile(r);
    }

}
