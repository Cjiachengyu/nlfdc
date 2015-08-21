package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 校长端按班级查看老师的时候，有很多重复的条目，这里用于构建类似于userClass的东西
 */
public class TeacherInfoInClass implements Serializable
{
    private static final long serialVersionUID = 4473854372722329536L;

    private Cls classInfo;
    private TeacherInfo teacherInfo;

    // getter setter
    // --------------------------------------------------------------------------------
    public Cls getClassInfo()
    {
        return classInfo;
    }

    public void setClassInfo(Cls classInfo)
    {
        this.classInfo = classInfo;
    }

    public TeacherInfo getTeacherInfo()
    {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo)
    {
        this.teacherInfo = teacherInfo;
    }

}
