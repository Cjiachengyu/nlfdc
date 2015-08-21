package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 用于保存教师信息；校长端查看时要看到每个教师的姓名，学科，活跃度，任课班级等信息
 * 
 * @author cjia
 *         2014.7.2
 */
public class TeacherInfo implements Serializable
{
    private static final long serialVersionUID = 8659540102073145309L;

    private int userId;
    private String teacherName;
    private String subject;         // 所教科目
    private int numberOfResource;   // 资源数
    private int numberOfAssignment; // 作业数
    private int totalNumber;         // 活跃度 = 资源数 + 作业数
    private String classes;         // 任课班级（可能有多个）

    // 按班级显示的时候用到
    private List<Cls> classList;
    private float avrClassAssignmentAvrScore;
    private float avrClassAssignmentCorrectedRatio;

    // getter setter
    // --------------------------------------------------------------------------------
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public int getTotalNumber()
    {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber)
    {
        this.totalNumber = totalNumber;
    }

    public int getNumberOfResource()
    {
        return numberOfResource;
    }

    public void setNumberOfResource(int numberOfResource)
    {
        this.numberOfResource = numberOfResource;
    }

    public int getNumberOfAssignment()
    {
        return numberOfAssignment;
    }

    public void setNumberOfAssignment(int numberOfAssignment)
    {
        this.numberOfAssignment = numberOfAssignment;
    }

    public String getClasses()
    {
        return classes;
    }

    public void setClasses(String classes)
    {
        this.classes = classes;
    }

    public List<Cls> getClassList()
    {
        return classList;
    }

    public void setClassList(List<Cls> classList)
    {
        this.classList = classList;
    }

    public float getAvrClassAssignmentAvrScore()
    {
        return avrClassAssignmentAvrScore;
    }

    public void setAvrClassAssignmentAvrScore(float avrClassAssignmentAvrScore)
    {
        this.avrClassAssignmentAvrScore = avrClassAssignmentAvrScore;
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
