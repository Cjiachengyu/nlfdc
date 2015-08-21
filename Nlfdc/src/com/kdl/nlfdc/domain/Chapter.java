package com.kdl.nlfdc.domain;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable
{
    private static final long serialVersionUID = 7860878172224299753L;

    private int chapterId;
    private String chapterName;
    private int bookId;
    private boolean isSelected;
    private List<Section> sectionList;

    public int getChapterId()
    {
        return chapterId;
    }

    public void setChapterId(int chapterId)
    {
        this.chapterId = chapterId;
    }

    public String getChapterName()
    {
        return chapterName;
    }

    public void setChapterName(String chapterName)
    {
        this.chapterName = chapterName;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public List<Section> getSectionList()
    {
        return this.sectionList;
    }

    public void setSectionList(List<Section> sectionList)
    {
        this.sectionList = sectionList;
    }
}
