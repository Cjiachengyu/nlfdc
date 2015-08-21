package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

public class Scm implements Serializable
{
    private static final long serialVersionUID = -6550091459464612622L;

    private int scmId;
    private String scmName;
    private int category;
    private boolean isSelected;

    private List<Book> textBookList;
    
    public List<Book> getTextBookList()
    {
        return textBookList;
    }

    public void setTextBookList(List<Book> textBookList)
    {
        this.textBookList = textBookList;
    }

    public int getScmId()
    {
        return scmId;
    }

    public void setScmId(int scmId)
    {
        this.scmId = scmId;
    }

    public String getScmName()
    {
        return scmName;
    }

    public void setScmName(String scmName)
    {
        this.scmName = scmName;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public int getCategory()
    {
        return this.category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }
}
