package com.kdl.nlfdc.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;

public interface ResMapper
{
    // 获取资源
    // --------------------------------------------------------------------------------
    int getTeacherResourceCount(
            @Param("teaId") int teaId,
            @Param("resTag") int resTag,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId);

    List<Res> getTeacherResource(
            @Param("teaId") int teaId,
            @Param("resTag") int resTag,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getSystemResourceForTeacherCount(
            @Param("teaId") int teaId,
            @Param("resTag") int resTag,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId);

    List<Res> getSystemResourceForTeacher(
            @Param("teaId") int teaId,
            @Param("resTag") int resTag,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getSchoolResourceCount(
            @Param("schoolId") int schoolId,
            @Param("resTag") int resTag,
            @Param("shareToSchool") int shareToSchool,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("createTime") long createTime);

    List<Res> getSchoolResource(
            @Param("schoolId") int schoolId,
            @Param("resTag") int resTag,
            @Param("shareToSchool") int shareToSchool,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("createTime") long createTime,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getSchoolResourceForTeacherCount(
            @Param("teaId") int teaId,
            @Param("schoolId") int schoolId,
            @Param("resTag") int resTag,
            @Param("shareToSchool") int shareToSchool,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId);

    List<Res> getSchoolResourceForTeacher(
            @Param("teaId") int teaId,
            @Param("schoolId") int schoolId,
            @Param("resTag") int resTag,
            @Param("shareToSchool") int shareToSchool,
            @Param("subjectId") int subjectId,
            @Param("bookId") int bookId,
            @Param("chapterId") int chapterId,
            @Param("sectionId") int sectionId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    // 资源基本操作
    // --------------------------------------------------------------------------------
    Res getResourceInfo(int resId);

    void insertResourceInfo(Res res);

    void insertResourceEntity(Res res);

    void updateResourceInfo(Res res);

    // 资源文件
    // --------------------------------------------------------------------------------
    ResFile getResourceFile(int fileId);

    void insertResourceFile(ResFile resourceFile);

    void updateResourceFile(ResFile resourceFile);

    List<ResFile> getResourceFileByResourceId(int resId);


    // 资源数
    // --------------------------------------------------------------------------------
    int getResourceCountOfTeacher(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId);

    List<Res> getTeacherResourceListByPage(
            @Param("teaId") int teaId,
            @Param("subjectId") int subjectId,
            @Param("limitBegin") int limitBegin,
            @Param("pageSize") int pageSize);

    int getSystemResourceCount(
            @Param("resTag")int resTag, 
            @Param("subjectId")int subjectId,
            @Param("bookId")int bookId,
            @Param("chapterId")int chapterId,
            @Param("sectionId")int sectionId,
            @Param("updateTime")long updateTime);

    List<Res> getSystemResource(
            @Param("resTag")int resTag,
            @Param("subjectId")int subjectId,
            @Param("bookId")int bookId,
            @Param("chapterId")int chapterId,
            @Param("sectionId")int sectionId,
            @Param("updateTime")long updateTime,
            @Param("limitBegin")int limitBegin,
            @Param("pageSize")int pageSize);

    int getYjResUseCount(@Param("resId") int resId);

}
