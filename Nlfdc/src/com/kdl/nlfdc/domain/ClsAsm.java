package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Utils;

public class ClsAsm extends Asm implements Serializable
{
    private static final long serialVersionUID = -6128810383528326324L;

    // classAsm表中的数据
    // --------------------------------------------------------------------------------
    protected int clsId;
    protected int teaId;
    protected int statusId;
    protected int assignTime;

    protected int studentCount;
    protected int submittedCount;
    protected int correctedCount;

    protected float maxScore;
    protected float minScore;
    protected float avrScore;

    protected float fangCha;
    protected float biaoZhunCha;

    // 附加字段
    // --------------------------------------------------------------------------------
    protected String clsName;

    // 附加存取器
    // --------------------------------------------------------------------------------
    public float getStandardAvrScore()
    {
        if (asmScore != 0)
        {
            return Utils.getPercent(avrScore, asmScore, 2);
        }
        else
        {
            return 0;
        }
    }

    public float getSubmitRatio()
    {
        if (studentCount == 0)
        {
            return 0;
        }
        else
        {
            return Utils.getPercent(submittedCount, studentCount, 1);
        }
    }

    public float getCorrectRatio()
    {
        if (submittedCount == 0)
        {
            return 0;
        }
        else
        {
            return Utils.getPercent(correctedCount, submittedCount, 1);
        }
    }


    // getter setter
    // --------------------------------------------------------------------------------
    public int getTeaId()
    {
        return teaId;
    }

    public void setTeaId(int teaId)
    {
        this.teaId = teaId;
    }

    public int getClsId()
    {
        return clsId;
    }

    public void setClsId(int clsId)
    {
        this.clsId = clsId;
    }

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
    }

    public int getAssignTime()
    {
        return assignTime;
    }

    public void setAssignTime(int assignTime)
    {
        this.assignTime = assignTime;
    }

    public int getCorrectedCount()
    {
        return correctedCount;
    }

    public void setCorrectedCount(int correctedCount)
    {
        this.correctedCount = correctedCount;
    }

    public float getMaxScore()
    {
        return maxScore;
    }

    public void setMaxScore(float maxScore)
    {
        this.maxScore = maxScore;
    }

    public float getMinScore()
    {
        return minScore;
    }

    public void setMinScore(float minScore)
    {
        this.minScore = minScore;
    }

    public float getAvrScore()
    {
        return avrScore;
    }

    public void setAvrScore(float avrScore)
    {
        this.avrScore = avrScore;
    }

    public float getFangCha()
    {
        return fangCha;
    }

    public void setFangCha(float fangCha)
    {
        this.fangCha = fangCha;
    }

    public float getBiaoZhunCha()
    {
        return biaoZhunCha;
    }

    public void setBiaoZhunCha(float biaoZhunCha)
    {
        this.biaoZhunCha = biaoZhunCha;
    }

    public String getClsName()
    {
        return clsName;
    }

    public void setClsName(String clsName)
    {
        this.clsName = clsName;
    }

    public int getSubmittedCount()
    {
        return submittedCount;
    }

    public void setSubmittedCount(int submittedCount)
    {
        this.submittedCount = submittedCount;
    }

    public int getStudentCount()
    {
        return studentCount;
    }

    public void setStudentCount(int studentCount)
    {
        this.studentCount = studentCount;
    }

}
