package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;

public class School implements Serializable
{
    private static final long serialVersionUID = 382605292361647916L;

    private int schoolId;
    private String schoolName;
    private int category;
    private int regionId;
    private String cityId;
    private String provinceId;
    private String schoolMotto = "";
    private String schoolIcon = "";

    public String getSchoolIconUrl()
    {
        if (Utils.stringNotEmpty(schoolIcon))
        {
            if (Utils.fileExist(Constants.PATH_FILE + "schoolIcon/" + schoolIcon))
                return Constants.URL_FILE + "schoolIcon/" + schoolIcon + "?v=" + System.currentTimeMillis();
        }

        return null;
    }

    public String getSchoolMotto()
    {
        return schoolMotto;
    }

    public void setSchoolMotto(String schoolMotto)
    {
        this.schoolMotto = schoolMotto;
    }

    public String getSchoolIcon()
    {
        return schoolIcon;
    }

    public void setSchoolIcon(String schoolIcon)
    {
        this.schoolIcon = schoolIcon;
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }

    public int getRegionId()
    {
        return regionId;
    }

    public void setRegionId(int regionId)
    {
        this.regionId = regionId;
    }

}
