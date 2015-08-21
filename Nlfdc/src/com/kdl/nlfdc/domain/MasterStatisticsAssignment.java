package com.kdl.nlfdc.domain;

import java.util.List;

/**
 * 2015.1.6 用于统计老师作业数据，每份作业发布到哪些班级，班级学生作答情况
 * 
 * @author cjia
 *
 */
public class MasterStatisticsAssignment
{
    private int asmId;
    private String asmName;
    private String creatorName;

    private List<ClsAsm> publishedClass;


    // getter setter
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

    public String getCreatorName()
    {
        return creatorName;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public List<ClsAsm> getPublishedClass()
    {
        return publishedClass;
    }

    public void setPublishedClass(List<ClsAsm> publishedClass)
    {
        this.publishedClass = publishedClass;
    }

}
