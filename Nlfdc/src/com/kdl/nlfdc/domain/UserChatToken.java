package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class UserChatToken implements Serializable
{
    private static final long serialVersionUID = -664451044394685964L;
    
    private int     userId;
    private String  token;
    
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public String getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        this.token = token;
    }

}
