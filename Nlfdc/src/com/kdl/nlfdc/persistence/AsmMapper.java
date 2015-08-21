package com.kdl.nlfdc.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kdl.nlfdc.domain.*;

public interface AsmMapper
{
    Asm getAsm(int asmId);

    void insertAsm(Asm asm);

    void updateAsm(Asm asm);
    
    int deleteAsm(int asmId);

    int getTeacherAssignmentCount(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId);

    // 老师网页端获取作业列表
    // --------------------------------------------------------------------------------

    List<Asm> getAsmListForYjTea(
            @Param("userId") int userId,
            @Param("isDeleted") int isDeleted,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getAsmListForYjTeaCount(
            @Param("userId") int userId,
            @Param("isDeleted") int isDeleted,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId);

    // add cjia 2014.7.28
    void insertClsAsm(ClsAsm clsAsm);

    ClsAsm getClsAsm(
            @Param("asmId") int asmId,
            @Param("clsId") int clsId);

    List<ClsAsm> getClsAsmOfAnAsm(
            @Param("asmId") int asmId);

    void insertStuAsmList(List<StuAsm> stuAsmList);

    StuAsm getStuAsm(
            @Param("asmId") int asmId,
            @Param("stuId") int stuId,
            @Param("clsId") int clsId);

    List<StuAsm> getStuAsmOfAClass(
            @Param("asmId") int asmId,
            @Param("clsId") int clsId);

    List<StuAsm> getStuAsmListForStu(
            @Param("stuId") int stuId,
            @Param("clsId") int clsId,
            @Param("asmStatusType") int asmStatusType,
            @Param("startTime") int startTime,
            @Param("endTime") int endTime,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("beginIndex") int beginIndex,
            @Param("pageSize") int pageSize);

    List<ClsAsm> getClsAsmListForTeaClient(
            @Param("creatorId") int creatorId,
            @Param("clsId") int clsId);

    void updateClsAsm(ClsAsm clsAsm);

    void updateStuAsm(StuAsm stuAsm);

    int getSchoolAssignmentCount(
            @Param("schoolId") int schoolId,
            @Param("subjectId") int subjectId);

    List<MasterStatisticsAssignment> getTeacherAssignmentListByPage(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    List<MasterStatisticsAssignment> getClassAssignmentListByPage(
            @Param("clsId") int clsId,
            @Param("subjectId") int subjectId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getClassAssignmentCount(
            @Param("clsId") int clsId,
            @Param("subjectId") int subjectId);

    List<ClsAsm> getClassAssignmentForMasterStatistics(
            @Param("clsId") int clsId,
            @Param("subjectId") int subjectId);

    List<ClsAsm> getClassAssignmentOfATeacherForMasterStatistics(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId);

    void insertAsmRes(
            @Param("asmId") int asmId,
            @Param("asmQueId") int asmQueId,
            @Param("resId") int resId,
            @Param("resNum") int resNum);
    
    List<AsmRes> getAsmResList(
            @Param("asmId") int asmId);
    
    // asm_que
    // --------------------------------------------------------------------------------
    AsmQue getAsmQue(int asmQueId);

    AsmQue getAsmQueOfAnAsm(int asmId);

    void insertAsmQue(AsmQue asmQue);
    
    // stu_ans
    // --------------------------------------------------------------------------------
    //jhuang
    StuAnswerItem getStuAnswerItem(
            @Param("asmId") int asmId,
            @Param("clsId") int clsId,
            @Param("stuId") int stuId,
            @Param("asmQueId") int asmQueId,
            @Param("answerNum") int answerNum);
    
    List<StuAnswerItem> getStuAnswerItemList(
            @Param("asmId") int asmId,
            @Param("clsId") int clsId,
            @Param("stuId") int stuId);
    
    void insertStuAnswerItem(StuAnswerItem stuAnswerItem);

    int getAsmNeedCorrectedCountInAllClass(int asmId);
    
    List<StuAsm> getOverFinishTimeNeedCorrectStuAsm(
            @Param("expireTime") int expireTime,
            @Param("limitExpireTime") int limitExpireTime);
    
}
