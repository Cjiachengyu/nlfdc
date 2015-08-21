package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class SchoolScm implements Serializable
{
    private static final long serialVersionUID = 8297315793918062332L;
    
    private int schoolId;
    private String schoolName;
    private int category;
    private int scmId;
    private String scmName;
    private String isSelected = "0";

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }
    
    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public int getScmId()
    {
        return scmId;
    }

    public void setScmId(int scmId)
    {
        this.scmId = scmId;
    }

    public String getScmName()
    {
        return scmName;
    }

    public void setScmName(String scmName)
    {
        this.scmName = scmName;
    }

    public String getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(String isSelected)
    {
        this.isSelected = isSelected;
    }
    
}
