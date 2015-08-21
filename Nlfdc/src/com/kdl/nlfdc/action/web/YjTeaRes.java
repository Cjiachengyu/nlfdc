package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.component.IUploadResource;
import com.kdl.nlfdc.action.component.PageModule;

/**
 * 幼教老师：
 * 我的资源
 * 
 * @author cjia
 *
 * @version 创建时间：2015年3月27日
 */
@SessionScope
@UrlBinding("/yjteares")
public class YjTeaRes extends AbstractResList implements IUploadResource
{
    private static final long serialVersionUID = 6280328177763003413L;

    private static final String RES_MANAGE = "/WEB-INF/jsp/yjteacher/YjTeacherRes.jsp";
    
    public int getResFromType()
    {
        return -1;
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

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_TEA_RESOURCE);

        tsCatalogueModule = initTsCatalogue();
        pageModule = new PageModule(ITEM_PAGESIZE);

        refreshResourceList();

        return new ForwardResolution(RES_MANAGE);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    @Override
    public int getShowType()
    {
        return 1; // 决定资源列表后面显示哪种操作， 1 对应管理资源（重新分类、删除）
    }
    
    @Override
    @HandlesEvent("getuploadresourcepage")
    public Resolution getUploadResourcePage()
    {
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        tsClassifyModule = initTsClassifyModule();
        cmService.selectCache(tsClassifyModule, getCurrentRealUser());

        return new ForwardResolution(UPLOAD_RESOURCE);
    }

    @Override
    protected void refreshResourceList()
    {
        int subjectId = tsCatalogueModule.getCurrentSubject().getSubjectId();
        int bookId = tsCatalogueModule.getCurrentTextbook().getBookId();
        int chapterId = tsCatalogueModule.getCurrentChapter().getChapterId();
//        int sectionId = tsCatalogueModule.getCurrentSection().getSectionId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        log("yj teacher refresh system resource list: " + getCurrentUserId() +
                ", " + subjectId + ", " + bookId + ", " + chapterId +
                ", " + pageSize + ", " + limitBegin + ", " + currentResourceTag);

        int resourceCount = cmService.getTeacherResourceCount(getCurrentUserId(), currentResourceTag,
                subjectId, bookId, chapterId, 0);
        resourceList = cmService.getTeacherResource(getCurrentUserId(), currentResourceTag,
                subjectId, bookId, chapterId, 0, limitBegin, pageSize);

        pageModule.changeItemsCount(resourceCount);
    }

}
