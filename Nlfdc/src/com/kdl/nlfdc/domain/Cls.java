package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

import com.kdl.nlfdc.action.Utils;

public class Cls implements Serializable
{
    private static final long serialVersionUID = -1909171358610547144L;

    // classInfo 表中的字段
    // --------------------------------------------------------------------------------
    private int clsId;
    private String clsName;
    private int entranceYear;
    private int schoolId;

    // 关联表字段
    // --------------------------------------------------------------------------------
    private String schoolName;
    private int category;
    private String cityId;
    private String provinceId;

    // 附加字段
    // --------------------------------------------------------------------------------
    private int numberOfStudents;
    private int numberOfTeachers;
    private int numberOfClassAssignment;
    private List<User> classTeachers;

    private float avrClassAssignmentAvrScore;
    private float maxClassAssignmentAvrScore;
    private float avrClassAssignmentSubmittedRatio;
    private float avrClassAssignmentCorrectedRatio;

    private boolean isSelected;
    private boolean isSharedToClass;

    private int resourceSharedTime; // add cjia 2015.1.8

    // 附加存取器
    // --------------------------------------------------------------------------------
    public String getResourceSharedTimeString()
    {
        return Utils.getFullTimeString(resourceSharedTime);
    }

    // getter setter
    // --------------------------------------------------------------------------------
    public boolean getIsSharedToClass()
    {
        return isSharedToClass;
    }

    public void setIsSharedToClass(boolean isSharedToClass)
    {
        this.isSharedToClass = isSharedToClass;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public int getNumberOfTeachers()
    {
        return numberOfTeachers;
    }

    public void setNumberOfTeachers(int numberOfTeachers)
    {
        this.numberOfTeachers = numberOfTeachers;
    }

    public List<User> getClassTeachers()
    {
        return classTeachers;
    }

    public void setClassTeachers(List<User> classTeachers)
    {
        this.classTeachers = classTeachers;
    }

    public int getNumberOfClassAssignment()
    {
        return numberOfClassAssignment;
    }

    public void setNumberOfClassAssignment(int numberOfClassAssignment)
    {
        this.numberOfClassAssignment = numberOfClassAssignment;
    }

    public int getNumberOfStudents()
    {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents)
    {
        this.numberOfStudents = numberOfStudents;
    }

    public int getClsId()
    {
        return clsId;
    }

    public void setClsId(int clsId)
    {
        this.clsId = clsId;
    }

    public String getClsName()
    {
        return clsName;
    }

    public void setClsName(String clsName)
    {
        this.clsName = clsName;
    }

    public int getEntranceYear()
    {
        return entranceYear;
    }

    public void setEntranceYear(int entranceYear)
    {
        this.entranceYear = entranceYear;
    }

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

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    public int getResourceSharedTime()
    {
        return resourceSharedTime;
    }

    public void setResourceSharedTime(int resourceSharedTime)
    {
        this.resourceSharedTime = resourceSharedTime;
    }

    public float getAvrClassAssignmentAvrScore()
    {
        return avrClassAssignmentAvrScore;
    }

    public void setAvrClassAssignmentAvrScore(float avrClassAssignmentAvrScore)
    {
        this.avrClassAssignmentAvrScore = avrClassAssignmentAvrScore;
    }

    public float getMaxClassAssignmentAvrScore()
    {
        return maxClassAssignmentAvrScore;
    }

    public void setMaxClassAssignmentAvrScore(float maxClassAssignmentAvrScore)
    {
        this.maxClassAssignmentAvrScore = maxClassAssignmentAvrScore;
    }

    public float getAvrClassAssignmentSubmittedRatio()
    {
        return avrClassAssignmentSubmittedRatio;
    }

    public void setAvrClassAssignmentSubmittedRatio(float avrClassAssignmentSubmittedRatio)
    {
        this.avrClassAssignmentSubmittedRatio = avrClassAssignmentSubmittedRatio;
    }

    public float getAvrClassAssignmentCorrectedRatio()
    {
        return avrClassAssignmentCorrectedRatio;
    }

    public void setAvrClassAssignmentCorrectedRatio(float avrClassAssignmentCorrectedRatio)
    {
        this.avrClassAssignmentCorrectedRatio = avrClassAssignmentCorrectedRatio;
    }

}
