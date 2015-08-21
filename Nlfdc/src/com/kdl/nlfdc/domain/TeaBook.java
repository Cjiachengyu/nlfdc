package com.kdl.nlfdc.domain;

import java.io.Serializable;

/**
 * 老师收藏课本
 */
public class TeaBook implements Serializable
{
    private static final long serialVersionUID = 8685551080028278550L;

    private int teaId;
    private int bookId;

    public int getTeaId()
    {
        return teaId;
    }

    public void setTeaId(int teaId)
    {
        this.teaId = teaId;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

}
