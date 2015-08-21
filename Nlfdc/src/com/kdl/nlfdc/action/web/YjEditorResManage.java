package com.kdl.nlfdc.action.web;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.component.IUploadResource;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.School;


/**
 * 幼教系统编辑查看或上传系统资源
 * 幼教管理员也可以调用这个ActionBean，身份判断的时候让管理员也通过判断，这样幼教管理员就具有幼教编辑的权限
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月20日
 */

@SessionScope
@UrlBinding("/yjeditorresourcemanageaction")
public class YjEditorResManage extends AbstractResList implements IUploadResource
{
    private static final long serialVersionUID = -517588057389868342L;

    private static final String MAIN_PAGE = "/WEB-INF/jsp/yjeditor/YjEditorResourceManage.jsp";

    private List<School> allYjSchoolList;
    private School currentYjSchool;
    
    private int showType = SHOW_YJ_EDITOR_SYSTEM;
    

    public List<School> getAllYjSchoolList()
    {
        return allYjSchoolList;
    }

    public School getCurrentYjSchool()
    {
        return currentYjSchool;
    }

    @Override
    public int getShowType()
    {
        return showType;
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

        if (makeSureYjEditor())
        {
            getCurrentSession().setAttribute("currentMemuOperation",
                    Constants.MainMenuOperation.YJ_EDITOR_RESOURCE_MANAGE);
        }
        else if (makeSureYjAdmin())
        {
            getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_ADMIN_RES_MANAGE);
        }

        tsCatalogueModule = initTsCatalogue();
        pageModule = new PageModule(ITEM_PAGESIZE);

        refreshResourceList();

        return new ForwardResolution(MAIN_PAGE);
    }

    @HandlesEvent("switchshowtype")
    public Resolution switchShowType()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int showTypeParam = getParamInt("showType", 0);

        if (showTypeParam != SHOW_YJ_EDITOR_SYSTEM && showTypeParam != SHOW_YJ_EDITOR_SCHOOL)
        {
            showTypeParam = SHOW_YJ_EDITOR_SYSTEM;
        }

        showType = showTypeParam;
        
        if (showType == SHOW_YJ_EDITOR_SCHOOL && allYjSchoolList == null)
        {
            allYjSchoolList = cmService.getCategoryedSchoolInRegion(0, Constants.Category.YOU_JIAO);
            
            Collections.sort(allYjSchoolList, comparatorBySchoolName);
            
            if (allYjSchoolList.size() != 0)
            {
                currentYjSchool = allYjSchoolList.get(0);
            }
        }
        
        refreshResourceList();

        return new ForwardResolution(MAIN_PAGE);
    }

    @HandlesEvent("switchschool")
    public Resolution switchSchool()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        
        int schoolId = getParamInt("schoolId", 0);
        
        for (School school : allYjSchoolList)
        {
            if (school.getSchoolId() == schoolId)
            {
                currentYjSchool = school;
                break;
            }
        }
        
        refreshResourceList();
        
        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }
    
    // override
    // --------------------------------------------------------------------------------
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
    protected boolean sessionIsValid()
    {
        // 幼教管理员公用这边的功能
        return (commonSessionIsValid() && makeSureYjEditor()) || (commonSessionIsValid() && makeSureYjAdmin());
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

        log("system yj_editor refresh resource list: " + getCurrentUserId() +
                ", " + subjectId + ", " + bookId + ", " + chapterId + ", " +
                ", " + pageSize + ", " + limitBegin);

        int resourceCount = 0, pageBeginBefore, limitChange;

        if (showType == SHOW_YJ_EDITOR_SYSTEM)
        {

            resourceCount = cmService.getSystemResourceCount(currentResourceTag, subjectId, bookId, chapterId, 0, 0);

            resourceList = cmService.getSystemResource(currentResourceTag, subjectId, bookId, chapterId, 0, 0, 
                    limitBegin, pageSize);

            pageBeginBefore = limitBegin > 0 ? (limitBegin - 1) : limitBegin;
            limitChange = limitBegin > 0 ? 2 : 1;
            resourceListPlus = cmService.getSystemResource(currentResourceTag, subjectId, bookId, chapterId, 0, 0,
                    pageBeginBefore, pageSize + limitChange);
        }
        else
        {
            if (currentYjSchool != null)
            {
                resourceCount = cmService.getSchoolResourceCount(currentYjSchool.getSchoolId(), currentResourceTag, 1,
                        subjectId, bookId, chapterId, 0, 0);
                resourceList = cmService.getSchoolResource(currentYjSchool.getSchoolId(), currentResourceTag, 1,
                        subjectId, bookId, chapterId, 0, 0, limitBegin, pageSize);
                
                pageBeginBefore = limitBegin > 0 ? (limitBegin - 1) : limitBegin;
                limitChange = limitBegin > 0 ? 2 : 1;
                resourceListPlus = cmService.getSchoolResource(currentYjSchool.getSchoolId(), currentResourceTag, 1,
                        subjectId, bookId, chapterId, 0, 0, pageBeginBefore, pageSize + limitChange);
            }
            else
            {
                resourceList.clear();
                resourceListPlus.clear();
            }
        }
        
        cmService.setResUseCount(resourceList);
        
        setResCanMoveUpDownAttribute();

        pageModule.changeItemsCount(resourceCount);

        log("resource result: " + resourceCount);
    }

    // 中文排序
    private static Comparator<School> comparatorBySchoolName = new Comparator<School>() {

        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

        @Override
        public int compare(School o1, School o2) {
            return cmp.compare(o1.getSchoolName(), o2.getSchoolName());
        }
    };
}
