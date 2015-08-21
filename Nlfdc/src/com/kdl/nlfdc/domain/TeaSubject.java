package com.kdl.nlfdc.domain;

import java.io.Serializable;

public class TeaSubject implements Serializable
{

    private static final long serialVersionUID = 5120195390873415892L;
    
    private int teaId;
    private int subjectId;

    public int getTeaId()
    {
        return teaId;
    }

    public void setTeaId(int teaId)
    {
        this.teaId = teaId;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

}