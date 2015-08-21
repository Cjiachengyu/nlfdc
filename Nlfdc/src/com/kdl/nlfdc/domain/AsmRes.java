package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 对应数据库表中asm_res
 * 
 * @author cjia
 *
 * @version 创建时间：2015年5月26日
 */
public class AsmRes implements Serializable
{
    private static final long serialVersionUID = -8069696687414346564L;

    private int asmId;
    private int resId;
    private int asmQueId;
    private int resNum;
    
    
    public int getAsmId()
    {
        return asmId;
    }
    public void setAsmId(int asmId)
    {
        this.asmId = asmId;
    }
    public int getResId()
    {
        return resId;
    }
    public void setResId(int resId)
    {
        this.resId = resId;
    }
    public int getAsmQueId()
    {
        return asmQueId;
    }
    public void setAsmQueId(int asmQueId)
    {
        this.asmQueId = asmQueId;
    }
    public int getResNum()
    {
        return resNum;
    }
    public void setResNum(int resNum)
    {
        this.resNum = resNum;
    }
}
