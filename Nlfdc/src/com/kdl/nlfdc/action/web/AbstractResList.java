package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import org.apache.tomcat.util.json.JSONException;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

/**
 * 资源管理的公共操作
 * 
 * 1. 选择subject、textbook、chapter、section时重新过滤资源
 * 2. 分页的跳转
 * 3. 返回资源列表jsp
 * 4. 资源重新分类
 * 5. 删除资源
 */

@SessionScope
public abstract class AbstractResList extends AbstractActionBean
{
    private static final long serialVersionUID = -594837369858752714L;

    protected String CHAPTER_AND_SECTION_VIEW = "/WEB-INF/jsp/component/ChapterAndSection.jsp";
    protected String RESOURCE_LIST_VIEW = "/WEB-INF/jsp/component/ResourceListView.jsp";
    protected static final String EDIT_RES_NAME_PAGE = "/WEB-INF/jsp/component/EditResName.jsp";
    
    public static final int SHOW_VIEW = 1;              // 学校资源
    public static final int SHOW_TEACHER_MANAGE = 2;    // 老师资源
    public static final int SHOW_TEACHER_SYSTEM = 3;    // 系统资源

    public static final int SHOW_EDITOR_SYSTEM = 4;     // 编辑查看所有系统资源
    public static final int SHOW_YJ_EDITOR_SYSTEM = 5;  // 幼教编辑和管理员查看幼教系统资源 
    public static final int SHOW_YJ_EDITOR_SCHOOL = 6;  // 幼教编辑和管理员查看幼教学校资源 
    

    protected static int ITEM_PAGESIZE = 10;
    
    protected Res editingRes;
    protected List<Res> resourceListPlus;       // 用于资源的上下移动
    protected List<Res> resourceList;
    protected int currentResourceTag = Constants.ResourceTag.ALL;

    public int getCurrentResourceTag()
    {
        return currentResourceTag;
    }

    public List<Res> getResourceList()
    {
        return resourceList;
    }
    
    public Res getEditingRes()
    {
        return editingRes;
    }
    
    public abstract int getShowType();

    // resolution
    // --------------------------------------------------------------------------------
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

        refreshResourceList();

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

        refreshResourceList();

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

        refreshResourceList();

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("geteditresnameview")
    public Resolution getEditResNameView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId", -1);

        editingRes = cmService.getResourceInfo(resId);
        // 设置的时候不显示文件后缀名
        editingRes.setResName(editingRes.getResName().replace("." + editingRes.getResFileSuffix(), ""));

        return new ForwardResolution(EDIT_RES_NAME_PAGE);
    }

    @HandlesEvent("dochangeresname")
    public Resolution doChangeResName()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String newResName = getParam("newResName", null);

        if (newResName == null)
        {
            return getStringResolution("error");
        }
        else if (newResName.length() > Constants.MaxLength.RES_NAME)
        {
            newResName = newResName.substring(0, Constants.MaxLength.RES_NAME);
        }

        editingRes.setResName(newResName + "." + editingRes.getResFileSuffix());
        editingRes.setUpdatorId(getCurrentUserId());
        editingRes.setUpdateTime(Utils.currentSeconds());
        cmService.updateResourceInfo(editingRes);

        return getStringResolution("ok");
    }
    
    @HandlesEvent("yjdownloadresfile")
    public Resolution yjDownloadResourceFile()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int resId = getParamInt("resId");
        Res resource = getResourceFromExistList(resId);
        if (resource == null)
        {
            return null;
        }

        List<ResFile> resourceFileList = cmService.getResourceFileByResourceId(resId);

        ResFile resourceFile = resourceFileList.get(0);
        String resName = resource.getResName();

        if (resName.contains("."))
        {
            resName = resName.substring(0, resName.lastIndexOf("."));
        }
        resName = resName + "." + resourceFile.getFileSuffix();

        return getFileResolution(resourceFile.getFilePath(), resName);
    }
    
    @HandlesEvent("gotopage")
    public Resolution gotoPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        pageModule.gotoPage(getParamInt("pageNum", 1));

        refreshResourceList();

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("getresourcelistview")
    public Resolution getResourceListView()
    {
        logRequest();

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @Override
    @HandlesEvent("dotextbookclassify")
    public Resolution doTextbookClassify()
    {
        logRequest();

        if (tsClassifyModule != null && tsClassifyModule.getCurrentTextbook().getBookId() == 0)
        {
            return getStringAtLeastTextbookResolution();
        }

        try
        {
            cmService.doTextbookClassify(tsClassifyModule, classifySchemaItemId, resourceList, getCurrentUserId());
            refreshResourceList();
            return new ForwardResolution(RESOURCE_LIST_VIEW);
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }
    }

    @Override
    @HandlesEvent("gettextbookselectorclassifyview")
    public Resolution getTextbookSelectorClassifyView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        classifySchemaItemId = getParam("classifySchemaItemId", "null_id");
        tsClassifyModule = initTsClassifyModule();

        try
        {
            cmService.setTsClassifyModule(tsClassifyModule, classifySchemaItemId, resourceList);
            return new ForwardResolution(CLASSIFY_SCHEMA_VIEW);
        }
        catch (NullPointerException e)
        {
            return getStringResolution("wrong_id");
        }
    }

    @HandlesEvent("deleteresource")
    public Resolution deleteResource()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId");

        try
        {
            cmService.deleteResource(resId, resourceList, getCurrentUserId());
            refreshResourceList();
            return new ForwardResolution(RESOURCE_LIST_VIEW);
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }
    }
   
    @HandlesEvent("moveup")
    public Resolution moveUp()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int index = getParamInt("index", -1);
        if (index < 0 || index > (resourceListPlus.size() - 1))
        {
            return getStringResolution("error");
        }

        // 判断是否处在第一个不能向前移动
        if (resourceListPlus.get(0).getResId() == resourceList.get(0).getResId()
                && index == 0)
        {
            return getStringResolution("frist_cannot_moveup");
        }

        Res res1, res2;

        if (resourceListPlus.get(0).getResId() == resourceList.get(0).getResId())
        {
            res1 = resourceListPlus.get(index - 1);
            res2 = resourceListPlus.get(index);
        }
        else
        {
            res1 = resourceListPlus.get(index);
            res2 = resourceListPlus.get(index + 1);
        }

        int res1Order = res1.getResOrder();
        int res2Order = res2.getResOrder();
        if (res1Order == res2Order)
        {
            // 批量上传的资源resOrder值可能相同，这种情况下相互交换值起不到移动的效果
            res1Order++;
        }
        res1.setResOrder(res2Order);
        res2.setResOrder(res1Order);
        try
        {
            cmService.updateResourceInfo(res1);
            cmService.updateResourceInfo(res2);

            refreshResourceList();
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("movedown")
    public Resolution moveDown()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int index = getParamInt("index", -1);
        if (index < 0 || index > (resourceList.size() - 1))
        {
            return getStringResolution("error");
        }
        // 判断是否处在最后一个不能向后移动
        if (resourceListPlus.get(resourceListPlus.size() - 1).getResId() == resourceList.get(
                resourceList.size() - 1).getResId()
                && index == (resourceList.size() - 1))
        {
            return getStringResolution("last_cannot_movedown");
        }

        Res res1, res2;

        if (index == (resourceList.size() - 1))
        {
            res1 = resourceList.get(resourceList.size() - 1);
            res2 = resourceListPlus.get(resourceListPlus.size() - 1);
        }
        else
        {
            res1 = resourceList.get(index);
            res2 = resourceList.get(index + 1);
        }

        int res1Order = res1.getResOrder();
        int res2Order = res2.getResOrder();
        if (res1Order == res2Order)
        {
            // 批量上传的资源resOrder值可能相同，这种情况下相互交换值起不到移动的效果
            res1Order--;
        }
        res1.setResOrder(res2Order);
        res2.setResOrder(res1Order);
        try
        {
            cmService.updateResourceInfo(res1);
            cmService.updateResourceInfo(res2);

            refreshResourceList();
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }
    
    // private
    // --------------------------------------------------------------------------------
    protected abstract void refreshResourceList();

    protected void setResCanMoveUpDownAttribute()
    {
        for (int resIndex = 0; resIndex < resourceList.size(); resIndex++)
        {
            Res res = resourceList.get(resIndex);
            if (resIndex == 0 && resourceListPlus.get(0).getResId() == resourceList.get(0).getResId())
            {
                res.setCanMoveUp(false);
            }

            if (resIndex == (resourceList.size() - 1)
                    && resourceListPlus.get(resourceListPlus.size() - 1).getResId() == resourceList.get(
                            resourceList.size() - 1)
                            .getResId())
            {
                res.setCanMoveDown(false);
            }
        }
    }

    
    /**
     * 从已存在的资源列表中查找资源，防止用户任意传resId过来
     * 
     * @param resId
     * @return
     */
    private Res getResourceFromExistList(int resId)
    {
        for (Res res : resourceList)
        {
            if (res.getResId() == resId)
            {
                return res;
            }
        }

        return null;
    }
}
