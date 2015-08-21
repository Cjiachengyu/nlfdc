package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class Remark implements Serializable
{
    private static final long serialVersionUID = -8613194692724172281L;

    private int remarkId;
    private String remark;
    private String lang;

    public int getRemarkId()
    {
        return remarkId;
    }

    public void setRemarkId(int remarkId)
    {
        this.remarkId = remarkId;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getLang()
    {
        return this.lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }
}
