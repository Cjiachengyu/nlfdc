package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.User;

@SessionScope
@UrlBinding("/yjmasterresourceaction")
public class YjMasterResList extends AbstractResList
{
    private static final long serialVersionUID = 3339859300873476879L;
    private static final String MASTER_RESOURCE_MAIN = "/WEB-INF/jsp/yjmaster/MasterResource.jsp";

    private int createTime = Utils.currentSeconds();
    private String dateString = Utils.getFullDateString(Utils.currentSeconds());

    public String getDateString()
    {
        return dateString;
    }

    @Override
    public int getShowType()
    {
        return SHOW_VIEW;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("masterresourceall")
    public Resolution masterResourceAll()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_MASTER_SCHOOL_RES);

        tsCatalogueModule = initTsCatalogue();
        pageModule = new PageModule(10);

        refreshResourceList();

        return new ForwardResolution(MASTER_RESOURCE_MAIN);
    }

    @HandlesEvent("selectdate")
    public Resolution selectDate()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        try
        {
            log(getParam("dateString"));
            createTime = (int) (Constants.SDF_FULL_TIME.parse(getParam("dateString") + " 23:59:59").getTime() / 1000);
        }
        catch (Exception e)
        {
            createTime = Utils.currentSeconds();
        }

        refreshResourceList();

        return new ForwardResolution(RESOURCE_LIST_VIEW);
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
    protected void refreshResourceList()
    {
        int subjectId = tsCatalogueModule.getCurrentSubject().getSubjectId();
        int bookId = tsCatalogueModule.getCurrentTextbook().getBookId();
        int chapterId = tsCatalogueModule.getCurrentChapter().getChapterId();
        // int sectionId = tsCatalogueModule.getCurrentSection().getSectionId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        // TODO 直接获取schoolId
        User currentUser = getCurrentUser();
        log("master refresh resource list: " + currentUser.getSchoolId()
                + ", " + subjectId + ", " + bookId + ", " + chapterId + ", " + pageSize + ", " + limitBegin + ", "
                + createTime);

        int resourceCount = cmService.getSchoolResourceCount(currentUser.getSchoolId(), 0, 1,
                subjectId, bookId, chapterId, 0, createTime);

        resourceList = cmService.getSchoolResource(currentUser.getSchoolId(), 0, 1,
                subjectId, bookId, chapterId, 0, createTime, limitBegin, pageSize);

        int pageBeginBefore = limitBegin > 0 ? (limitBegin - 1) : limitBegin;
        int limitChange = limitBegin > 0 ? 2 : 1;
        resourceListPlus = cmService.getSchoolResource(currentUser.getSchoolId(), 0, 1,
                subjectId, bookId, chapterId, 0, createTime, pageBeginBefore, pageSize + limitChange);
        
        pageModule.changeItemsCount(resourceCount);
        
        cmService.setResUseCount(resourceList);
        setResCanMoveUpDownAttribute();
        
        log("resource result: " + resourceCount + ", " + resourceList.size());
    }

}
