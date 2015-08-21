package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

public class Subject implements Serializable
{
    private static final long serialVersionUID = -8546747212376921537L;

    private int subjectId;
    private String subjectName;

    // 附加字段
    // --------------------------------------------------------------------------------
    private boolean isSelected;
    private List<Book> bookList;

    // 存取器
    // --------------------------------------------------------------------------------
    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public List<Book> getBookList()
    {
        return bookList;
    }

    public void setBookList(List<Book> bookList)
    {
        this.bookList = bookList;
    }

}
