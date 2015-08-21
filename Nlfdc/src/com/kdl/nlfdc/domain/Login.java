package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class Login implements Serializable
{
    private static final long serialVersionUID = 4347039467256656233L;

    private int userId;
    private String tokenId = "";
    private int loginTime;
    private String androidId;
    private String clientId;
    private int clientType;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }

    public int getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(int loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getAndroidId()
    {
        return androidId;
    }

    public void setAndroidId(String androidId)
    {
        this.androidId = androidId;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public int getClientType()
    {
        return clientType;
    }

    public void setClientType(int clientType)
    {
        this.clientType = clientType;
    }
    
    

}