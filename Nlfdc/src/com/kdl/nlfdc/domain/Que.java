package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class Que implements Serializable
{
    private static final long serialVersionUID = -7265960687262175201L;

    // questionBank表中的字段
    // --------------------------------------------------------------------------------
    protected int queId;

    protected int schoolId;
    protected int creatorId;
    protected int createTime;
    protected int updateTime;
    protected int updatorId;

    protected String contentHtml;

    protected int subjectId;
    protected int bookId;
    protected int chapterId;
    protected int sectionId;

    protected int isDeleted;

    // tempAsmQuestion表中的字段
    // --------------------------------------------------------------------------------
    protected int userId;
    protected int queNum;

    // 附加getter
    // --------------------------------------------------------------------------------
    public int getUpdatorId()
    {
        return updatorId;
    }

    public void setUpdatorId(int updatorId)
    {
        this.updatorId = updatorId;
    }

    public int getQueId()
    {
        return queId;
    }

    public void setQueId(int queId)
    {
        this.queId = queId;
    }

    public int getCreatorId()
    {
        return creatorId;
    }

    public void setCreatorId(int creatorId)
    {
        this.creatorId = creatorId;
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public String getContentHtml()
    {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml)
    {
        this.contentHtml = contentHtml;
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

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getQueNum()
    {
        return queNum;
    }

    public void setQueNum(int queNum)
    {
        this.queNum = queNum;
    }

}
