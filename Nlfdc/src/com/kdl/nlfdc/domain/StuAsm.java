package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.IRankable;

public class StuAsm extends Asm implements Serializable, IRankable
{
    private static final long serialVersionUID = -6128210383528326324L;

    // studentAssignment表中的数据
    // --------------------------------------------------------------------------------
    protected int stuId;
    protected int clsId;
    protected int teaId;
    protected int statusId;
    protected int assignTime;

    protected int beginAnswerTime;
    protected int endAnswerTime;
    protected int beginSubmitTime; // 学生从客户端提交到服务器
    protected int endSubmitTime; // 老师从服务器下载到客户端
    protected int beginSendbackTime; // 老师从客户端提交到服务器
    protected int endSendbackTime; // 学生从服务器下载到客户端

    protected int isSubmitted;
    protected int isCorrected;

    protected int answerTime;
    protected int cycleCount;
    protected float stuAsmScore;
    protected int ranking;

    // 附加字段
    // --------------------------------------------------------------------------------
    protected String clsName;
    protected String studentName;

    // 附加存取器
    // --------------------------------------------------------------------------------
    
    public String getBeginSubmitTimeString()
    {
        return Utils.getSimpleTimeString(beginSubmitTime);
    }
    
    public String getAsmAnswerTimeString()
    {
        return Utils.convertSecondsToString(answerTime, lang);
    }

    // IRankable接口
    // --------------------------------------------------------------------------------
    @Override
    public double getRankValue()
    {
        return -stuAsmScore;
    }

    @Override
    public boolean isCountIn()
    {
        return isCorrected == 1;
    }

    // 存取器
    // --------------------------------------------------------------------------------
    public int getStuId()
    {
        return stuId;
    }

    public void setStuId(int stuId)
    {
        this.stuId = stuId;
    }

    public int getBeginAnswerTime()
    {
        return beginAnswerTime;
    }

    public void setBeginAnswerTime(int beginAnswerTime)
    {
        this.beginAnswerTime = beginAnswerTime;
    }

    public int getEndAnswerTime()
    {
        return endAnswerTime;
    }

    public void setEndAnswerTime(int endAnswerTime)
    {
        this.endAnswerTime = endAnswerTime;
    }

    public int getBeginSubmitTime()
    {
        return beginSubmitTime;
    }

    public void setBeginSubmitTime(int beginSubmitTime)
    {
        this.beginSubmitTime = beginSubmitTime;
    }

    public int getEndSubmitTime()
    {
        return endSubmitTime;
    }

    public void setEndSubmitTime(int endSubmitTime)
    {
        this.endSubmitTime = endSubmitTime;
    }

    public int getBeginSendbackTime()
    {
        return beginSendbackTime;
    }

    public void setBeginSendbackTime(int beginSendbackTime)
    {
        this.beginSendbackTime = beginSendbackTime;
    }

    public int getEndSendbackTime()
    {
        return endSendbackTime;
    }

    public void setEndSendbackTime(int endSendbackTime)
    {
        this.endSendbackTime = endSendbackTime;
    }

    public int getCycleCount()
    {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount)
    {
        this.cycleCount = cycleCount;
    }

    public int getIsSubmitted()
    {
        return isSubmitted;
    }

    public void setIsSubmitted(int isSubmitted)
    {
        this.isSubmitted = isSubmitted;
    }

    public int getIsCorrected()
    {
        return isCorrected;
    }

    public void setIsCorrected(int isCorrected)
    {
        this.isCorrected = isCorrected;
    }

    public float getStuAsmScore()
    {
        return stuAsmScore;
    }

    public void setStuAsmScore(float stuAsmScore)
    {
        this.stuAsmScore = stuAsmScore;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public int getRanking()
    {
        return ranking;
    }

    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }

    public int getAnswerTime()
    {
        return answerTime;
    }

    public void setAnswerTime(int answerTime)
    {
        this.answerTime = answerTime;
    }

    public int getClsId()
    {
        return clsId;
    }

    public void setClsId(int clsId)
    {
        this.clsId = clsId;
    }

    public int getTeaId()
    {
        return teaId;
    }

    public void setTeaId(int teaId)
    {
        this.teaId = teaId;
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

    public String getClsName()
    {
        return clsName;
    }

    public void setClsName(String clsName)
    {
        this.clsName = clsName;
    }
    
    public String getAssignStatusString()
    {
        if ( Constants.AsmStatus.NEW_ASSIGNED <= statusId && statusId <= Constants.AsmStatus.FINISHED)
        {
            return Constants.AsmStatus.STU_STRING_ZH[statusId];
        }
        else
        {
            return "";
        }
    }
}
