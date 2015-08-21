package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class Province implements Serializable
{
    private static final long serialVersionUID = -5523215671012194576L;

    private String provinceId;
    private String provinceName;

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    public String getProvinceName()
    {
        return provinceName;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }
}