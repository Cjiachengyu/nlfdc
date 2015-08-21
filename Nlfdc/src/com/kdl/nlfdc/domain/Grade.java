package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 显示班级
 * 
 * @author cjia
 *
 */
public class Grade implements Serializable
{
    private static final long serialVersionUID = -8355885660583964924L;

    private int gradeIndex;
    private String[] gradeStrings = {"全部年级", "小班", "中班", "大班"};

    public int getGradeIndex()
    {
        return gradeIndex;
    }

    public void setGradeIndex(int gradeIndex)
    {
        this.gradeIndex = gradeIndex;
    }

    public String getGradeString()
    {
        if (gradeIndex >= 0 && gradeIndex <= 3)
        {
            return gradeStrings[gradeIndex];
        }
        else
        {
            return "未知";
        }
    }
}
