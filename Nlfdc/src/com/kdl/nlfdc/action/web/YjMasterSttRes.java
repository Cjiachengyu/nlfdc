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
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.User;

/**
 * 2015.1.7 校长端查看老师所有作业统计
 * 2015.1.7 校长端查看班级所有资源统计
 * 
 * @author cjia
 *
 */
@SessionScope
@UrlBinding("/yjmastercheckresourceaction")
public class YjMasterSttRes extends AbstractActionBean
{
    private static final long serialVersionUID = 2246250921864362858L;

    private static final String MASTER_RESOURCE_MAIN = "/WEB-INF/jsp/yjmaster/MasterStatisticsResource.jsp";
    private static final String MASTER_CHECK_RESOURCE_LISTVIEW = "/WEB-INF/jsp/yjmaster/StatisticsResourceListView.jsp";

    private int subjectId;
    private int teaId;

    private List<Res> resourceList;
    private List<Subject> subjects;

    // 用于显示老师姓名
    private User teacher;

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

    public List<Res> getResourceList()
    {
        return resourceList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("mastercheckresourceinfo")
    public Resolution masterCheckResourceInfo()
    {
        logRequest();
        validateSession();

        subjectId = getParamInt("subjectId", -1);
        teaId = getParamInt("teaId", -1);

        if (!sessionIsValid() || subjectId == -1 || teaId == -1)
        {
            return getYjLogoutResolution();
        }

        pageModule = new PageModule(12);
        // 查看老师资源统计
        teacher = cmService.getUser(teaId);
        subjects = cmService.getTeacherSubjects(teaId);

        refreshResourceList();
        return new ForwardResolution(MASTER_RESOURCE_MAIN);

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
        refreshResourceList();

        return new ForwardResolution(MASTER_CHECK_RESOURCE_LISTVIEW);
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

        refreshResourceList();

        return new ForwardResolution(MASTER_CHECK_RESOURCE_LISTVIEW);
    }

    @HandlesEvent("getresourcelistview")
    public Resolution getResourceListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(MASTER_CHECK_RESOURCE_LISTVIEW);
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
    private void refreshResourceList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        int resourceCount = 0;

        resourceCount = cmService.getResourceCountOfTeacher(teaId, subjectId);
        // 查询出老师的所有资源
        resourceList = cmService.getTeacherResourceListByPage(teaId, subjectId, limitBegin, pageSize);

        cmService.setResUseCount(resourceList);

        pageModule.changeItemsCount(resourceCount);
    }


}
