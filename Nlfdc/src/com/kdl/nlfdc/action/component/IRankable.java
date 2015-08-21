package com.kdl.nlfdc.action.component;

public interface IRankable
{
    public double getRankValue(); // 排名的基准

    public boolean isCountIn(); // 是否参与排名

    public void setRanking(int rank); // 设置排名结果的接口
}
