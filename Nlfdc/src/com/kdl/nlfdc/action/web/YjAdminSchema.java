package com.kdl.nlfdc.action.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Scm;
import com.kdl.nlfdc.domain.Subject;

/**
 * 幼教管理员-》大纲管理
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月28日
 */
@SessionScope
@UrlBinding("/yjadminschemaaction")
public class YjAdminSchema extends AbstractActionBean
{
    private static final long serialVersionUID = 6199147209952652657L;

    private static final String ADMIN_SCHEMA = "/WEB-INF/jsp/yjadmin/YjAdminSchema.jsp";
    private static final String ADMIN_ADD_SCHEMA = "/WEB-INF/jsp/yjadmin/SchemaAddSchema.jsp";
    private static final String ADMIN_TEXTBOOK_IN_SCHEMA = "/WEB-INF/jsp/yjadmin/TextbooksInSchemaListView.jsp";
    private static final String ADMIN_TEXTBOOK_OUT_SCHEMA = "/WEB-INF/jsp/yjadmin/TextbooksOutSchemaListView.jsp";

    private List<Scm> schemaList = new ArrayList<Scm>();
    private Book textbook;
    private List<Subject> subjectList;
    private HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();

    // 选中的大纲Id，初始时都没选中
    private int selectedSchemaId = -1;
    private int selectedCategory = -1;
    private int selectedSubjectId = -1;
    private List<Book> textbookInSchemaList = new ArrayList<Book>();
    private List<Book> textbookOutSchemaList = new ArrayList<Book>();

    public int getSelectedCategory()
    {
        return selectedCategory;
    }

    public int getSelectedSubjectId()
    {
        return selectedSubjectId;
    }

    public int getSelectedSchemaId()
    {
        return selectedSchemaId;
    }

    public List<Book> getTextbookInSchemaList()
    {
        return textbookInSchemaList;
    }

    public List<Book> getTextbookOutSchemaList()
    {
        return textbookOutSchemaList;
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    public Book getTextbook()
    {
        return textbook;
    }

    public List<Scm> getSchemaList()
    {
        return schemaList;
    }

    @DefaultHandler
    @HandlesEvent("adminschema")
    public Resolution adminSchema()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.YJ_ADMIN_SCHEMA);

        subjectList = cmService.getAllSubject();
        InitSubjectMap(subjectList);
        schemaList = cmService.getAllSchema();
        refreshTextbookOutSchemaList();
        refreshTextbookInSchemaList();

        return new ForwardResolution(ADMIN_SCHEMA);
    }

    @HandlesEvent("getaddschemaview")
    public Resolution getAddSchemaView()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        return new ForwardResolution(ADMIN_ADD_SCHEMA);
    }

    @HandlesEvent("addoneschema")
    public Resolution addOneSchema()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String scmName = getParam("scmName", "");
        if (scmName.equals(""))
        {
            return getStringResolution("error");
        }
        if (scmName.length() > Constants.MaxLength.SCHEMA)
        {
            return getStringResolution("lengthException");
        }

        Scm schema = new Scm();
        schema.setScmName(scmName);
        try
        {
            cmService.insertSchema(schema);
            schemaList = cmService.getAllSchema();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("addtextbooktoschema")
    public Resolution addTextbookToSchema()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int bookId = getParamInt("bookId", -1);
        if (bookId == -1 || selectedSchemaId == -1)
        {
            return getStringResolution("error");
        }

        Book addTextbook = new Book();
        addTextbook.setScmId(selectedSchemaId);
        addTextbook.setBookId(bookId);
        try
        {
            cmService.insertSchemaTextbook(addTextbook);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("deletetextbookfromschema")
    public Resolution deleteTextbookFromSchema()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int bookId = getParamInt("bookId", -1);
        if (bookId == -1 || selectedSchemaId == -1)
        {
            return getStringResolution("error");
        }

        try
        {
            cmService.deleteSchemaTextbook(selectedSchemaId, bookId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("selectschema")
    public Resolution selectSchema()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        selectedSchemaId = getParamInt("selectedSchemaId", -1);

        refreshTextbookInSchemaList();

        return new ForwardResolution(ADMIN_TEXTBOOK_IN_SCHEMA);
    }

    @HandlesEvent("gettextbookoutlist")
    public Resolution getTextbookOutList()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        selectedCategory = getParamInt("selectedCategory", -1);
        selectedSubjectId = getParamInt("selectedSubjectId", -1);
        refreshTextbookOutSchemaList();
        return new ForwardResolution(ADMIN_TEXTBOOK_OUT_SCHEMA);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjAdmin();
    }

    // private
    // --------------------------------------------------------------------------------
    private void refreshTextbookInSchemaList()
    {
        textbookInSchemaList = cmService.getTextbookBySchemaId(selectedSchemaId);
        InitSubjectNameForTextBook(textbookInSchemaList);
    }

    private void InitSubjectNameForTextBook(List<Book> textbookInSchemaList2)
    {
        for (int i = 0; i < textbookInSchemaList2.size(); i++)
        {
            textbookInSchemaList2.get(i).setSubjectName(subjectMap.get(textbookInSchemaList2.get(i).getSubjectId()));
        }
    }

    private void InitSubjectMap(List<Subject> subjectList2)
    {
        for (Subject subj : subjectList2)
        {
            subjectMap.put(subj.getSubjectId(), subj.getSubjectName());
        }
    }

    private void refreshTextbookOutSchemaList()
    {
        textbookOutSchemaList = cmService.getTextbookOutOfSchema(selectedSchemaId, selectedCategory, selectedSubjectId);
        InitSubjectNameForTextBook(textbookOutSchemaList);
    }
}
