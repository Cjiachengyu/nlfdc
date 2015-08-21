package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class MachineLicense implements Serializable
{
    private static final long serialVersionUID = -593821755367549928L;

    private String licenseCode;
    private String machineId;
    private String licenseResult;

    public String getLicenseResult()
    {
        return licenseResult;
    }

    public void setLicenseResult(String licenseResult)
    {
        this.licenseResult = licenseResult;
    }

    public String getLicenseCode()
    {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode)
    {
        this.licenseCode = licenseCode;
    }

    public String getMachineId()
    {
        return machineId;
    }

    public void setMachineId(String machineId)
    {
        this.machineId = machineId;
    }
}
