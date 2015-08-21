package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class AsmQue extends Que implements Serializable, Comparable<AsmQue>
{
    private static final long serialVersionUID = -6621725914411998130L;

    private int asmQueId;
    private int asmId;
    private int queNum = 0;
    private float queScore = 0.0f;
    private float subScore = 0.0f;

    // --------------------------------------------------------------------------------
    public int getAsmQueId()
    {
        return asmQueId;
    }

    public void setAsmQueId(int asmQueId)
    {
        this.asmQueId = asmQueId;
    }

    public int getAsmId()
    {
        return asmId;
    }

    public void setAsmId(int asmId)
    {
        this.asmId = asmId;
    }

    public int getQueNum()
    {
        return queNum;
    }

    public void setQueNum(int queNum)
    {
        this.queNum = queNum;
    }

    public float getQueScore()
    {
        return queScore;
    }

    public void setQueScore(float queScore)
    {
        this.queScore = queScore;
    }

    public float getSubScore()
    {
        return subScore;
    }

    public void setSubScore(float subScore)
    {
        this.subScore = subScore;
    }


    @Override
    public int compareTo(AsmQue arg0)
    {
        if (arg0 == null)
        {
            return 0;
        }
        else
        {
            return this.getQueNum() - arg0.getQueNum();
        }
    }
}
