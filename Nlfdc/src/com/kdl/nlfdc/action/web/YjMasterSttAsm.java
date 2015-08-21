package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.ClsAsm;
import com.kdl.nlfdc.domain.MasterStatisticsAssignment;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.User;

/**
 * 2015.1.6 校长端查看老师所有作业统计
 * 2015.1.6 校长端查看班级所有作业统计
 * 
 * @author cjia
 *
 */
@SessionScope
@UrlBinding("/yjmastercheckassignmentaction")
public class YjMasterSttAsm extends AbstractActionBean
{
    private static final long serialVersionUID = 2246250921864362858L;

    private static final String MASTER_ASSIGNMENT_MAIN = "/WEB-INF/jsp/yjmaster/MasterStatisticsAssignment.jsp";
    private static final String MASTER_CHECK_ASSIGNMENT_LISTVIEW = "/WEB-INF/jsp/yjmaster/StatisticsAssignmentListView.jsp";

    private static final int CLASS_ASM = 1;
    private static final int TEACHER_ASM = 2;

    // private state
    // --------------------------------------------------------------------------------
    private int teaId;
    private int clsId;

    // ui state
    // --------------------------------------------------------------------------------
    private int statisticsType;
    private int subjectId;
    private List<Subject> subjects;
    private User teacher; // 用于显示老师姓名
    private Cls classInfo; // 用于显示班级名称
    private List<MasterStatisticsAssignment> statisticsAssignmentList;


    public int getStatisticsType()
    {
        return statisticsType;
    }

    public Cls getClassInfo()
    {
        return classInfo;
    }

    public User getTeacher()
    {
        return teacher;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public List<Subject> getSubjects()
    {
        return subjects;
    }

    public List<MasterStatisticsAssignment> getStatisticsAssignmentList()
    {
        return statisticsAssignmentList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("masterteacherassignmentinfo")
    public Resolution masterTeacherAssignmentInfo()
    {
        logRequest();
        validateSession();

        // 查看老师的作业要限制subjectId，因为前面显示的作业数是受subject限制的，如果这边不受限制将不统一
        subjectId = getParamInt("subjectId", -1);
        teaId = getParamInt("teaId", -1);
        clsId = getParamInt("clsId", -1);

        if (!sessionIsValid() || subjectId == -1 || (teaId == -1 && clsId == -1))
        {
            return getYjLogoutResolution();
        }

        pageModule = new PageModule(12);
        if (clsId == -1)
        {
            // 查看老师作业统计
            statisticsType = TEACHER_ASM;
            teacher = cmService.getUser(teaId);
            subjects = cmService.getTeacherSubjects(teaId);
        }
        else
        {
            // 查办班级作业统计
            statisticsType = CLASS_ASM;
            classInfo = cmService.getClassInfo(clsId);
            subjects = cmService.getAllSubject();
        }

        refreshTeacherAssignmentList();
        return new ForwardResolution(MASTER_ASSIGNMENT_MAIN);
    }

    @HandlesEvent("selectsubject")
    public Resolution selectSubject()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        subjectId = getParamInt("subjectId", 0);
        pageModule.gotoPage(1);
        refreshTeacherAssignmentList();

        return new ForwardResolution(MASTER_CHECK_ASSIGNMENT_LISTVIEW);
    }

    @HandlesEvent("gotopage")
    public Resolution gotoPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int pageNum = getParamInt("pageNum", 1);
        pageModule.gotoPage(pageNum);

        refreshTeacherAssignmentList();

        return new ForwardResolution(MASTER_CHECK_ASSIGNMENT_LISTVIEW);
    }

    @HandlesEvent("getteacherassignmentlistview")
    public Resolution getTeacherAssignmentListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(MASTER_CHECK_ASSIGNMENT_LISTVIEW);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjMaster();
    }
    
    // private
    // --------------------------------------------------------------------------------
    private void refreshTeacherAssignmentList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        int statisticsAssignmentCount = 0;

        if (statisticsType == TEACHER_ASM)
        {
            statisticsAssignmentCount = cmService.getTeacherAssignmentCount(teaId, subjectId);
            // 查询出老师的所有作业
            statisticsAssignmentList = cmService.getTeacherAssignmentListByPage(teaId, subjectId,
                    limitBegin, pageSize);

        }
        else
        {
            statisticsAssignmentCount = cmService.getClassAssignmentCount(clsId, subjectId);
            // 查询出班级的所有作业
            statisticsAssignmentList = cmService.getClassAssignmentListByPage(clsId, subjectId, limitBegin,
                    pageSize);
        }

        // 把老师的每份作业设置classAssignment
        for (MasterStatisticsAssignment asm : statisticsAssignmentList)
        {
            // 对每份作业获取发布的班级列表
            List<ClsAsm> publishedClass = cmService.getClsAsmOfAnAsm(asm.getAsmId());
            List<ClsAsm> publishedClassToAdd = new ArrayList<ClsAsm>();

            for (ClsAsm clsAsm : publishedClass)
            {
                if (statisticsType == CLASS_ASM && clsAsm.getClsId() != clsId)
                {
                    continue;
                }
                publishedClassToAdd.add(clsAsm);
            }

            if (publishedClassToAdd.size() == 0)
            {
                ClsAsm clsAsm = new ClsAsm();
                clsAsm.setClsName("未发布");
                publishedClassToAdd.add(clsAsm);
            }

            asm.setPublishedClass(publishedClassToAdd);
        }

        pageModule.changeItemsCount(statisticsAssignmentCount);
    }
}
