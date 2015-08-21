package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;

/*
 * jhuang
 * 多媒体子答案
 * */
public class StuAnswerItem implements Serializable, Comparable<StuAnswerItem>
{
    private static final long serialVersionUID = -8480024315279035121L;

    private int asmId;
    private int clsId;
    private int stuId;
    private int asmQueId;

    private int answerNum;
    private int answerType;
    private String answerItem;
    private int createTime;
    private int cycleNum;
    private int isPiGai;

    // 附加存取器
    // --------------------------------------------------------------------------------
    public String getFileUrl()
    {
        try
        {
            String relativePath = Utils.groupUserId(stuId) + "/" + asmId + "/" + answerItem;
            if (Utils.fileExist(Constants.PATH_FILE + relativePath))
            {
                return Constants.URL_FILE + relativePath;
            }
        }
        catch (Exception e)
        {
        }
        return "";
    }

    public int compareTo(StuAnswerItem arg0)
    {
        if (arg0 == null)
        {
            return 0;
        }

        return this.answerNum - arg0.getAnswerNum();
    }

    // 存取器
    // --------------------------------------------------------------------------------
    public int getAsmId()
    {
        return asmId;
    }

    public void setAsmId(int asmId)
    {
        this.asmId = asmId;
    }

    public int getClsId()
    {
        return clsId;
    }

    public void setClsId(int clsId)
    {
        this.clsId = clsId;
    }

    public int getStuId()
    {
        return stuId;
    }

    public void setStuId(int stuId)
    {
        this.stuId = stuId;
    }

    public int getAsmQueId()
    {
        return asmQueId;
    }

    public void setAsmQueId(int asmQueId)
    {
        this.asmQueId = asmQueId;
    }

    public int getAnswerNum()
    {
        return answerNum;
    }

    public void setAnswerNum(int answerNum)
    {
        this.answerNum = answerNum;
    }

    public int getAnswerType()
    {
        return answerType;
    }

    public void setAnswerType(int answerType)
    {
        this.answerType = answerType;
    }

    public String getAnswerItem()
    {
        return answerItem;
    }

    public void setAnswerItem(String answerItem)
    {
        this.answerItem = answerItem;
    }

    public int getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(int createTime)
    {
        this.createTime = createTime;
    }

    public int getCycleNum()
    {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum)
    {
        this.cycleNum = cycleNum;
    }

    public int getIsPiGai()
    {
        return isPiGai;
    }

    public void setIsPiGai(int isPiGai)
    {
        this.isPiGai = isPiGai;
    }


}
