package com.kdl.nlfdc.action.web;

import java.util.List;

import org.apache.tomcat.util.json.JSONException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Asm;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

/**
 * 幼教老师：
 * 我的作业
 * 
 * @author cjia
 *
 * @version 创建时间：2015年3月27日
 */
@SessionScope
@UrlBinding("/yjteaasmaction")
public class YjTeaAsm extends AbstractActionBean
{
    private static final long serialVersionUID = -6514492299464235151L;

    private static final String TEACHER_ASSIGNMENT_MAIN = "/WEB-INF/jsp/yjteacher/YjTeacherAssignmentMain.jsp";
    private static final String TEACHER_ASM_LIST_VIEW = "/WEB-INF/jsp/yjteacher/AsmListView.jsp";

    private static final String CHAPTER_AND_SECTION_VIEW = "/WEB-INF/jsp/component/ChapterAndSection.jsp";

    private static final int TEACHER_ASSIGN_PAGESIZE = 10;

    private List<Asm> assignmentList;

    public int getIsDeleted()
    {
        return 0;
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

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_TEA_ASSIGNMENT);

        tsCatalogueModule = initTsCatalogue();
        pageModule = new PageModule(TEACHER_ASSIGN_PAGESIZE);

        log("ts: " + tsCatalogueModule.getTextbookList().size());

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASSIGNMENT_MAIN);
    }


    @HandlesEvent("selectsubject")
    public Resolution selectSubject() throws JSONException, SqlAffectedCountException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getJsonTimeoutResolution();
        }

        int subjectId = getParamInt("subjectId");
        tsCatalogueModule.selectSubject(subjectId);
        updateUserTsCache(tsCatalogueModule);
        pageModule.gotoPage(1);

        refreshAssignmentList();

        String textListJson = tsCatalogueModule.getTextbookJsonArray().toString();
        return getJsonStringResolution(textListJson);
    }

    @HandlesEvent("selecttext")
    public Resolution selectText() throws SqlAffectedCountException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int bookId = getParamInt("bookId");
        tsCatalogueModule.selectTextbook(bookId);
        updateUserTsCache(tsCatalogueModule);
        pageModule.gotoPage(1);

        refreshAssignmentList();

        return new ForwardResolution(CHAPTER_AND_SECTION_VIEW);
    }

    @HandlesEvent("selectchapter")
    public Resolution selectChapter() throws SqlAffectedCountException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int chapterId = getParamInt("chapterId");
        tsCatalogueModule.selectChapter(chapterId);
        updateUserTsCache(tsCatalogueModule);
        pageModule.gotoPage(1);

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASM_LIST_VIEW);
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

    @HandlesEvent("deleteassignment")
    public Resolution deleteAssignment()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int asmId = getParamInt("asmId");
        log("asmId: " + asmId);
        for (Asm asm : assignmentList)
        {
            if (asm.getAsmId() == asmId)
            {
                asm.setIsDeleted(1);
                cmService.updateAsm(asm);
            }
        }

        refreshAssignmentList();

        return new ForwardResolution(TEACHER_ASM_LIST_VIEW);
    }

    // override
    // --------------------------------------------------------------------------------
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
        int bookId = tsCatalogueModule.getBookId();
        int chapterId = tsCatalogueModule.getChapterId();
        int sectionId = 0;
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        log("yj_teacher_refresh_asm_list: " + getCurrentUserId() + ", "
                + bookId + ", " + chapterId + ", " + pageSize + ", " + limitBegin);

        int assignmentCount = cmService.getAsmListForYjTeaCount(
                getCurrentUserId(), Constants.NOT_DELETED, bookId, chapterId, sectionId);

        assignmentList = cmService.getAsmListForYjTea(
                getCurrentUserId(), Constants.NOT_DELETED, bookId, chapterId, sectionId, limitBegin, pageSize);

        int currentSeconds = Utils.currentSeconds();

        for (Asm asm : assignmentList)
        {
            asm.setIsPreReleased(asm.getStartTime() > currentSeconds);
            asm.setHasPublishedAny(cmService.getClsAsmOfAnAsm(asm.getAsmId()).size() > 0);
            asm.setNeedCorrectedcount(cmService.getAsmNeedCorrectedCountInAllClass(asm.getAsmId()));
        }

        pageModule.changeItemsCount(assignmentCount);
        log("asm_result: " + assignmentCount);
    }

}
