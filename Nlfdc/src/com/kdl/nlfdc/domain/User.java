package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;

public class User implements Serializable
{
    private static final long serialVersionUID = 8960991871702804325L;

    // userInfo表中的字段
    // --------------------------------------------------------------------------------
    private int userId;
    private String loginId;
    private String loginName;
    private String email;
    private String cellphone;
    private String password;

    private String userName;
    private int userRole;
    private int schoolId = 0;

    private int cacheSubjectId;
    private int cacheBookId;
    private int cacheChapterId;
    private int cacheSectionId;

    private String portraitUrl = "";

    // 附加字段
    // --------------------------------------------------------------------------------
    private String schoolName;


    // 附加存取器
    // --------------------------------------------------------------------------------


    // 存取器
    // --------------------------------------------------------------------------------
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }

    public int getUserRole()
    {
        return userRole;
    }

    public void setUserRole(int userRole)
    {
        this.userRole = userRole;
    }

    public int getCacheSubjectId()
    {
        return cacheSubjectId;
    }

    public void setCacheSubjectId(int cacheSubjectId)
    {
        this.cacheSubjectId = cacheSubjectId;
    }

    public int getCacheBookId()
    {
        return cacheBookId;
    }

    public void setCacheBookId(int cacheBookId)
    {
        this.cacheBookId = cacheBookId;
    }

    public int getCacheChapterId()
    {
        return cacheChapterId;
    }

    public void setCacheChapterId(int cacheChapterId)
    {
        this.cacheChapterId = cacheChapterId;
    }

    public int getCacheSectionId()
    {
        return cacheSectionId;
    }

    public void setCacheSectionId(int cacheSectionId)
    {
        this.cacheSectionId = cacheSectionId;
    }

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public String getPortraitUrl()
    {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl)
    {
        this.portraitUrl = portraitUrl;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getCellphone()
    {
        return cellphone;
    }

    public void setCellphone(String cellphone)
    {
        this.cellphone = cellphone;
    }
}
