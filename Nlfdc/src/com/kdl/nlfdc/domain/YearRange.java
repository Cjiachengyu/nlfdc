package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 添加班级、编辑班级用到，控制可选择的班级入学年份
 * 该类没有对应到数据库中的某张表
 * 
 * @author cjia
 *
 * @version 创建时间：2015年2月15日
 */
public class YearRange implements Serializable
{
    private static final long serialVersionUID = 8243559968081539051L;

    private int thisYear;
    private int minYear;

    public int getThisYear()
    {
        return thisYear;
    }

    public void setThisYear(int thisYear)
    {
        this.thisYear = thisYear;
    }

    public int getMinYear()
    {
        return minYear;
    }

    public void setMinYear(int minYear)
    {
        this.minYear = minYear;
    }

    /**
     * 根据学校的分类获取到年级数，然后得到该学校班级可以设定的入学年份范围，避免设置出不合理的班级入学年份。 （小学有六个年份选择，初中只能有三个）
     * 
     * @param school
     */
    public void initData(School school)
    {
        Calendar c = Calendar.getInstance();
        thisYear = c.get(Calendar.YEAR);

        minYear = thisYear - 2;
        int month = c.get(Calendar.MONTH) + 1;

        // 在某一年中还没过9月，不能添加当前年入学的班级
        if (month < 9)
        {
            thisYear--;
            minYear--;
        }
    }

}
