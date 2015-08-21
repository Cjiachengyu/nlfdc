package com.kdl.nlfdc.persistence;

import com.kdl.nlfdc.domain.Que;

public interface QueMapper
{
    // 基本操作
    // --------------------------------------------------------------------------------
    public Que getQuestionBank(int queId);

    public int insertQuestionBank(Que que);

    public int updateQuestionBank(Que que);

}
