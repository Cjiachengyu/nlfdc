package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class City implements Serializable
{
    private static final long serialVersionUID = -7928394567176250103L;

    private String cityId;
    private String cityName;
    private String provinceId;

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }
}