package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;

public class Book implements Serializable
{
    private static final long serialVersionUID = -1714743869304022136L;

    // textbook表中的字段
    // --------------------------------------------------------------------------------
    private int bookId;
    private String bookName;
    private String publisher;
    private int grade = 0;
    private int term = 0;
    private int subjectId;
    private String cover = "";

    // 扩展字段
    // --------------------------------------------------------------------------------
    private int scmId;
    private String scmName;
    private String subjectName;
    private boolean isSelected; // 左边栏用到
    private int freeAsmCount;   // 购物车用到
    private int isInCart = 0;
    
    // 扩展存取器
    // --------------------------------------------------------------------------------
    public String getCoverUrl()
    {
        try
        {
            if (Utils.stringNotEmpty(cover))
            {
                String path = Constants.Book.BOOK_COVER_FOLDER + cover;

                if (Utils.fileExist(Constants.PATH_FILE + path))
                {
                    return Constants.URL_FILE + path;
                }
            }
        }
        catch (Exception e)
        {
        }
        return "";
    }

    // 存取器
    // --------------------------------------------------------------------------------
    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public String getScmName()
    {
        return scmName;
    }

    public void setScmName(String scmName)
    {
        this.scmName = scmName;
    }

    public int getTerm()
    {
        return term;
    }

    public void setTerm(int term)
    {
        this.term = term;
    }

    public int getScmId()
    {
        return scmId;
    }

    public void setScmId(int scmId)
    {
        this.scmId = scmId;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
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

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public int getFreeAsmCount()
    {
        return freeAsmCount;
    }

    public void setFreeAsmCount(int freeAsmCount)
    {
        this.freeAsmCount = freeAsmCount;
    }

    public int getIsInCart()
    {
        return isInCart;
    }

    public void setIsInCart(int isInCart)
    {
        this.isInCart = isInCart;
    }

}
