package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Utils;

public class Asm implements Serializable
{
    private static final long serialVersionUID = 657309816497752565L;

    // assignment表中的字段
    // --------------------------------------------------------------------------------
    protected int asmId;
    protected String asmName;
    protected int asmType = 1;
    protected int creatorId;
    protected int asmScore;
    protected int createTime;
    protected int startTime;
    protected int finishTime;
    protected int limitTime;
    protected int subjectId;
    protected int bookId;
    protected int chapterId;
    protected int sectionId;
    protected int isDeleted;
    protected int isFree;

    // 附加字段
    // --------------------------------------------------------------------------------
    protected boolean hasPublishedAny;
    protected String subjectName;
    protected String lang;
    protected int stuBookStatus; // 学生自主学习时用到

    protected int isMultiMedia; 
    protected boolean isPreReleased = false; // 幼教老师需要显示作业是否是预发布
   
    protected int needCorrectedcount;        // 幼教老师的任务中待批改数（各个班级之和）
    
    // 扩展存取器
    // --------------------------------------------------------------------------------
    
    public String getCreateTimeString()
    {
        return Utils.getSimpleTimeString(createTime);
    }
    
    public String getStartTimeString()
    {
        return Utils.getSimpleTimeString(startTime);
    }

    public String getFinishTimeString()
    {
        return Utils.getSimpleTimeString(finishTime);
    }

    public boolean getIsPreReleased()
    {
        return isPreReleased;
    }

    public void setIsPreReleased(boolean isPreReleased)
    {
        this.isPreReleased = isPreReleased;
    }

    // 存取器
    // --------------------------------------------------------------------------------
    public int getAsmId()
    {
        return asmId;
    }

    public void setAsmId(int asmId)
    {
        this.asmId = asmId;
    }

    public String getAsmName()
    {
        return asmName;
    }

    public void setAsmName(String asmName)
    {
        this.asmName = asmName;
    }

    public int getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(int creatorId)
    {
        this.creatorId = creatorId;
    }

    public int getAsmType()
    {
        return asmType;
    }

    public void setAsmType(int asmType)
    {
        this.asmType = asmType;
    }

    public int getAsmScore()
    {
        return asmScore;
    }

    public void setAsmScore(int asmScore)
    {
        this.asmScore = asmScore;
    }

    public int getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(int createTime)
    {
        this.createTime = createTime;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public int getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(int finishTime)
    {
        this.finishTime = finishTime;
    }

    public int getLimitTime()
    {
        return limitTime;
    }

    public void setLimitTime(int limitTime)
    {
        this.limitTime = limitTime;
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

    public int getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public boolean getHasPublishedAny()
    {
        return hasPublishedAny;
    }

    public void setHasPublishedAny(boolean hasPublishedAny)
    {
        this.hasPublishedAny = hasPublishedAny;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public int getIsFree()
    {
        return isFree;
    }

    public void setIsFree(int isFree)
    {
        this.isFree = isFree;
    }

    public int getStuBookStatus()
    {
        return stuBookStatus;
    }

    public void setStuBookStatus(int stuBookStatus)
    {
        this.stuBookStatus = stuBookStatus;
    }

    public int getIsMultiMedia()
    {
        return isMultiMedia;
    }

    public void setIsMultiMedia(int isMultiMedia)
    {
        this.isMultiMedia = isMultiMedia;
    }

    public int getNeedCorrectedcount()
    {
        return needCorrectedcount;
    }

    public void setNeedCorrectedcount(int needCorrectedcount)
    {
        this.needCorrectedcount = needCorrectedcount;
    }
    
}
