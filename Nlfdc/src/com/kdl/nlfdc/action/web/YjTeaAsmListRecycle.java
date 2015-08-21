package com.kdl.nlfdc.action.web;

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
import com.kdl.nlfdc.domain.Asm;

@SessionScope
@UrlBinding("/yjteacherassignmentrecycleaction")
public class YjTeaAsmListRecycle extends AbstractActionBean
{
    private static final long serialVersionUID = -6278562165699185622L;

    private static final String TEACHER_ASSIGNMENT_RECYCLE = "/WEB-INF/jsp/yjteacher/YjTeacherAsmRecycle.jsp";
    private static final String TEACHER_ASM_LIST_VIEW = "/WEB-INF/jsp/yjteacher/AsmListView.jsp";

    private static final int TEACHER_ASSIGN_PAGESIZE = 10;

    private PageModule pageModule;
    private List<Asm> assignmentList;

    public int getIsDeleted()
    {
        return 1;
    }

    @Override
    public PageModule getPageModule()
    {
        return pageModule;
    }

    public List<Asm> getAssignmentList()
    {
        return assignmentList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("defaulthandler")
    public Resolution defaultHandler()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        pageModule = new PageModule(TEACHER_ASSIGN_PAGESIZE);

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASSIGNMENT_RECYCLE);
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

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASM_LIST_VIEW);
    }

    @HandlesEvent("getassignmentlistview")
    public Resolution getAssignmentListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(TEACHER_ASM_LIST_VIEW);
    }

    @HandlesEvent("restoreassignment")
    public Resolution restoreAssignment()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int asmId = getParamInt("asmId");
        for (Asm asm : assignmentList)
        {
            if (asm.getAsmId() == asmId)
            {
                asm.setIsDeleted(0);
                cmService.updateAsm(asm);
            }
        }

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASM_LIST_VIEW);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    // private
    // --------------------------------------------------------------------------------
    private void refreshAssignmentList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        int assignmentCount = cmService.getAsmListForYjTeaCount(getCurrentUserId(), Constants.IS_DELETED, 0, 0, 0);
        assignmentList = cmService.getAsmListForYjTea(getCurrentUserId(), Constants.IS_DELETED, 0, 0, 0, limitBegin,
                pageSize);

        pageModule.changeItemsCount(assignmentCount);
        log("recyle_asm_result: " + assignmentCount);
    }


}
