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
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Scm;
import com.kdl.nlfdc.domain.Subject;

/**
 * 幼教管理员-》书本管理
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月28日
 */
@SessionScope
@UrlBinding("/yjadmintextbookaction")
public class YjAdminBookList extends AbstractActionBean
{
    private static final long serialVersionUID = -6774975373629334750L;

    private static final String MANAGE_TEXTBOOK_LIST = "/WEB-INF/jsp/yjadmin/YjAdminManageTextbook.jsp";
    private static final String TEXTBOOK_LIST_VIEW = "/WEB-INF/jsp/yjadmin/YjTextbookListView.jsp";

    private List<Book> bookList;
    private List<Scm> schemaList = new ArrayList<Scm>();
    private List<Subject> subjectList;

    private int selectedSchemaId = 0;
    private int selectedSubjectId = 0;

    public List<Scm> getSchemaList()
    {
        return schemaList;
    }

    public int getSelectedSchemaId()
    {
        return selectedSchemaId;
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    public int getSelectedSubjectId()
    {
        return selectedSubjectId;
    }

    public List<Book> getBookList()
    {
        return bookList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("managetextbook")
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
            getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_EDITOR_TEXTBOOK_MANAGE);
        }
        else if (makeSureYjAdmin())
        {
            getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_ADMIN_TEXTBOOK_MANAGE);
        }

        schemaList = cmService.getAllSchema();
        subjectList = cmService.getAllSubject();

        pageModule = new PageModule(30);
        refreshBookList();

        return new ForwardResolution(MANAGE_TEXTBOOK_LIST);
    }

    @HandlesEvent("reselect")
    public Resolution reSelect()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int schemaId = getParamInt("schemaId", -1);
        int subjectId = getParamInt("subjectId", -1);

        if (schemaId != -1)
        {
            selectedSchemaId = schemaId;
        }

        if (subjectId != -1)
        {
            selectedSubjectId = subjectId;
        }

        refreshBookList();

        return getStringResolution("ok");
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

        refreshBookList();

        return new ForwardResolution(TEXTBOOK_LIST_VIEW);
    }
    
    
    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && (makeSureYjAdmin() || makeSureYjEditor());
    }
    
    
    // private
    // --------------------------------------------------------------------------------
    private void refreshBookList()
    {
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        int bookCount = cmService.getTextbookCountForAdmin(selectedSchemaId, selectedSubjectId, 0, 0);

        bookList = cmService.getTextbookForAdmin(selectedSchemaId, selectedSubjectId, 0, 0, limitBegin, pageSize);

        pageModule.changeItemsCount(bookCount);
    }

    
}
