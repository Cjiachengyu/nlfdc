package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

import com.kdl.nlfdc.action.Constants;

/**
 * for Researcher
 * 
 * @author cjia
 *
 * @date 2014年9月15日
 */
public class SchoolInfo implements Serializable
{
    private static final long serialVersionUID = -6253006045922391263L;
    
    private String schoolName;
    private List<User> masters;
    private int numOfTeachers;
    private int numOfStudents;
    private int numOfClasses;
    private int numOfAssignments;
    private int numOfResources;

    // add for admin
    private int category;
    private int schoolId;


    public String getSchoolCategoryString()
    {
        switch (category)
        {
        case Constants.Category.ALL:
            return "全部阶段";
        case Constants.Category.PRIMARY_SCHOOL:
            return "小学";
        case Constants.Category.MIDDLE_SCHOOL:
            return "初中";
        case Constants.Category.HIGH_SCHOOL:
            return "高中";
        default:
            return "未知";
        }
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public List<User> getMasters()
    {
        return masters;
    }

    public void setMasters(List<User> masters)
    {
        this.masters = masters;
    }

    public int getNumOfTeachers()
    {
        return numOfTeachers;
    }

    public void setNumOfTeachers(int numOfTeachers)
    {
        this.numOfTeachers = numOfTeachers;
    }

    public int getNumOfStudents()
    {
        return numOfStudents;
    }

    public void setNumOfStudents(int numOfStudents)
    {
        this.numOfStudents = numOfStudents;
    }

    public int getNumOfClasses()
    {
        return numOfClasses;
    }

    public void setNumOfClasses(int numOfClasses)
    {
        this.numOfClasses = numOfClasses;
    }

    public int getNumOfAssignments()
    {
        return numOfAssignments;
    }

    public void setNumOfAssignments(int numOfAssignments)
    {
        this.numOfAssignments = numOfAssignments;
    }

    public int getNumOfResources()
    {
        return numOfResources;
    }

    public void setNumOfResources(int numOfResources)
    {
        this.numOfResources = numOfResources;
    }

}
