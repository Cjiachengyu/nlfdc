package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

import com.kdl.nlfdc.action.Utils;

public class Res implements Serializable
{
    private static final long serialVersionUID = -5251833970861062839L;

    // resourceInfo表中的字段
    // --------------------------------------------------------------------------------
    private int resId;
    private String resName;
    private int resType;   // 文件，板书
    private int fromType;       // 系统资源，学校资源，个人资源

    private int creatorId;
    private int createTime;
    private int updatorId;
    private int updateTime;

    private int resTag;
    private int isDeleted;
    private int openTimes;

    private int subjectId;
    private int bookId;
    private int chapterId;
    private int sectionId;
    private int resOrder;


    // resourceEntity和resourceClass表中的字段
    // --------------------------------------------------------------------------------
    private int resEntityId;
    private int teaId;
    private int schoolId;
    private int shareToSchool;
    private int clsId; // 学生获取资源列表时，被发到不同的班的资源暂时视为不同的资源

    // 附加字段
    // --------------------------------------------------------------------------------
    private String subjectName;
    private String creatorName;
    private String teacherName;
    private boolean isInMyResource;
    
    //幼教老师中用到的字段：  资源文件的类型（图片、音频、视频）
    private boolean isAddedToAsm;
    private String resFilePath;
    private String resFileSuffix;
    private int yjResUseCount;          // 记录一条资源被使用的次数
    private String resFileType;         // 资源文件的类型
    
    // 幼教编辑、管理员用
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;
    
    // 附加存取器
    // --------------------------------------------------------------------------------
    
    public String getSimpleTimeString()
    {
        return Utils.getSimpleTimeString(createTime);
    }
    
    // --------------------------------------------------------------------------------
    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public String getCreatorName()
    {
        return creatorName;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public int getResId()
    {
        return resId;
    }

    public void setResId(int resId)
    {
        this.resId = resId;
    }

    public int getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(int creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getResName()
    {
        return resName;
    }

    public void setResName(String resName)
    {
        this.resName = resName;
    }

    public int getResType()
    {
        return resType;
    }

    public void setResType(int resType)
    {
        this.resType = resType;
    }

    public int getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(int createTime)
    {
        this.createTime = createTime;
    }

    public int getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(int updateTime)
    {
        this.updateTime = updateTime;
    }

    public int getOpenTimes()
    {
        return openTimes;
    }

    public void setOpenTimes(int openTimes)
    {
        this.openTimes = openTimes;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public int getChapterId()
    {
        return chapterId;
    }

    public void setChapterId(int chapterId)
    {
        this.chapterId = chapterId;
    }

    public int getSectionId()
    {
        return sectionId;
    }

    public void setSectionId(int sectionId)
    {
        this.sectionId = sectionId;
    }

    public int getResTag()
    {
        return resTag;
    }

    public void setResTag(int resTag)
    {
        this.resTag = resTag;
    }

    public int getFromType()
    {
        return fromType;
    }

    public void setFromType(int fromType)
    {
        this.fromType = fromType;
    }

    public int getUpdatorId()
    {
        return updatorId;
    }

    public void setUpdatorId(int updatorId)
    {
        this.updatorId = updatorId;
    }

    public int getResEntityId()
    {
        return resEntityId;
    }

    public void setResEntityId(int resEntityId)
    {
        this.resEntityId = resEntityId;
    }

    public int getTeaId()
    {
        return teaId;
    }

    public void setTeaId(int teaId)
    {
        this.teaId = teaId;
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public int getShareToSchool()
    {
        return shareToSchool;
    }

    public void setShareToSchool(int shareToSchool)
    {
        this.shareToSchool = shareToSchool;
    }

    public boolean getIsInMyResource()
    {
        return isInMyResource;
    }

    public void setIsInMyResource(boolean isInMyResource)
    {
        this.isInMyResource = isInMyResource;
    }

    public int getClsId()
    {
        return clsId;
    }

    public void setClsId(int clsId)
    {
        this.clsId = clsId;
    }

    public boolean getIsAddedToAsm()
    {
        return isAddedToAsm;
    }

    public void setIsAddedToAsm(boolean isAddedToAsm)
    {
        this.isAddedToAsm = isAddedToAsm;
    }

    public String getResFilePath()
    {
        return resFilePath;
    }

    public void setResFilePath(String resFilePath)
    {
        this.resFilePath = resFilePath;
    }

    public String getResFileSuffix()
    {
        return resFileSuffix;
    }

    public void setResFileSuffix(String resFileSuffix)
    {
        this.resFileSuffix = resFileSuffix;
    }

    public int getYjResUseCount()
    {
        return yjResUseCount;
    }

    public void setYjResUseCount(int yjResUseCount)
    {
        this.yjResUseCount = yjResUseCount;
    }

    public String getResFileType()
    {
        return resFileType;
    }

    public void setResFileType(String resFileType)
    {
        this.resFileType = resFileType;
    }
    
    public int getResOrder()
    {
        return resOrder;
    }

    public void setResOrder(int resOrder)
    {
        this.resOrder = resOrder;
    }

    public boolean getCanMoveUp()
    {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp)
    {
        this.canMoveUp = canMoveUp;
    }

    public boolean getCanMoveDown()
    {
        return canMoveDown;
    }

    public void setCanMoveDown(boolean canMoveDown)
    {
        this.canMoveDown = canMoveDown;
    }
    
}
