package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 老师查看作业完成情况
 */
public class ClsAsmComplete implements Serializable
{
    private static final long serialVersionUID = -6128210383528326324L;

    private int asmId;
    private int asmType = 1;
    private String asmName;
    private int clsId;
    private String clsName;
    private int statusId;

    private int createTime;
    private String createTimeString;
    private int assignTime;
    private String assignTimeString;

    private int startTime;
    private String startTimeString;
    private int finishTime;
    private String finishTimeString;
    private int limitTime;

    private List<StuAsm> allAssignList;
    private List<StuAsm> undownloadedList;
    private List<StuAsm> finishedList;
    private List<StuAsm> unfinishedList;
    private List<StuAsm> uncorrectedList;
    private int allAssignCount;
    private int undownloadedCount;
    private int finishedCount;
    private int unfinishedCount;
    private int uncorrectedCount;


    public int getAsmId()
    {
        return asmId;
    }

    public void setAsmId(int asmId)
    {
        this.asmId = asmId;
    }

    public int getAsmType()
    {
        return asmType;
    }

    public void setAsmType(int asmType)
    {
        this.asmType = asmType;
    }

    public String getAsmName()
    {
        return asmName;
    }

    public void setAsmName(String asmName)
    {
        this.asmName = asmName;
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

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
    }

    public int getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(int createTime)
    {
        this.createTime = createTime;
    }

    public String getCreateTimeString()
    {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString)
    {
        this.createTimeString = createTimeString;
    }

    public int getAssignTime()
    {
        return assignTime;
    }

    public void setAssignTime(int assignTime)
    {
        this.assignTime = assignTime;
    }

    public String getAssignTimeString()
    {
        return assignTimeString;
    }

    public void setAssignTimeString(String assignTimeString)
    {
        this.assignTimeString = assignTimeString;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public String getStartTimeString()
    {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString)
    {
        this.startTimeString = startTimeString;
    }

    public int getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(int finishTime)
    {
        this.finishTime = finishTime;
    }

    public String getFinishTimeString()
    {
        return finishTimeString;
    }

    public void setFinishTimeString(String finishTimeString)
    {
        this.finishTimeString = finishTimeString;
    }

    public int getLimitTime()
    {
        return limitTime;
    }

    public void setLimitTime(int limitTime)
    {
        this.limitTime = limitTime;
    }

    public List<StuAsm> getAllAssignList()
    {
        return allAssignList;
    }

    public void setAllAssignList(List<StuAsm> allAssignList)
    {
        this.allAssignList = allAssignList;
    }

    public List<StuAsm> getUndownloadedList()
    {
        return undownloadedList;
    }

    public void setUndownloadedList(List<StuAsm> undownloadedList)
    {
        this.undownloadedList = undownloadedList;
    }

    public List<StuAsm> getFinishedList()
    {
        return finishedList;
    }

    public void setFinishedList(List<StuAsm> finishedList)
    {
        this.finishedList = finishedList;
    }

    public List<StuAsm> getUnfinishedList()
    {
        return unfinishedList;
    }

    public void setUnfinishedList(List<StuAsm> unfinishedList)
    {
        this.unfinishedList = unfinishedList;
    }

    public List<StuAsm> getUncorrectedList()
    {
        return uncorrectedList;
    }

    public void setUncorrectedList(List<StuAsm> uncorrectedList)
    {
        this.uncorrectedList = uncorrectedList;
    }

    public int getAllAssignCount()
    {
        return allAssignCount;
    }

    public void setAllAssignCount(int allAssignCount)
    {
        this.allAssignCount = allAssignCount;
    }

    public int getUndownloadedCount()
    {
        return undownloadedCount;
    }

    public void setUndownloadedCount(int undownloadedCount)
    {
        this.undownloadedCount = undownloadedCount;
    }

    public int getFinishedCount()
    {
        return finishedCount;
    }

    public void setFinishedCount(int finishedCount)
    {
        this.finishedCount = finishedCount;
    }

    public int getUnfinishedCount()
    {
        return unfinishedCount;
    }

    public void setUnfinishedCount(int unfinishedCount)
    {
        this.unfinishedCount = unfinishedCount;
    }

    public int getUncorrectedCount()
    {
        return uncorrectedCount;
    }

    public void setUncorrectedCount(int uncorrectedCount)
    {
        this.uncorrectedCount = uncorrectedCount;
    }
}
