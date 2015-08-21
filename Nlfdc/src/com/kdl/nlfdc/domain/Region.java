package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class Region implements Serializable
{
    private static final long serialVersionUID = -7928394567176250103L;

    private int regionId;
    private String regionName;
    private String cityId;

    public int getRegionId()
    {
        return regionId;
    }

    public void setRegionId(int regionId)
    {
        this.regionId = regionId;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

}
