package com.kdl.nlfdc.action.web;

import java.io.File;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Chapter;
import com.kdl.nlfdc.domain.Subject;

/**
 * 幼教管理员-》编辑课本
 * 
 * @author cjia
 *
 * @version 创建时间：2015年4月28日
 */
@SessionScope
@UrlBinding("/yjadmineditbookaction")
public class YjAdminEditBook extends AbstractActionBean
{
    private static final long serialVersionUID = 3926902149645545785L;

    private static final String ADMIN_EDIT_TEXTBOOK = "/WEB-INF/jsp/yjadmin/YjAdminEditTextbook.jsp";
    private static final String ADMIN_ADD_TEXTBOOK = "/WEB-INF/jsp/yjadmin/YjAdminAddTextbook.jsp";

    private static final String OPERATE_TYPE_ADD = "add";
    private static final String OPERATE_TYPE_EDIT = "edit";

    private Book editingTextbook;
    private List<Subject> subjectList;
    private FileBean bookCoverFile;
    private String bookCoverName; // 编辑和添加课本公用
    private String addedBookCoverUrl = ""; // 只有添加课本的时候用到，用于在添加课本时显示上传的课本封面，因为当时书本还没有创建，不好通过
                                           // book.getCoverUrl获得

    public void setBookCover(FileBean bookCover)
    {
        this.bookCoverFile = bookCover;
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    public Book getEditingTextbook()
    {
        return editingTextbook;
    }

    public String getAddedBookCoverUrl()
    {
        return addedBookCoverUrl;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("turntoedittextbookpage")
    public Resolution turnToEditTextbookPage()
    {
        logRequest();
        validateSession();

        int textbookId = getParamInt("textbookId", -1);
        editingTextbook = cmService.getTextbook(textbookId);

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        if (editingTextbook == null)
        {
            return new RedirectResolution(YjAdminBookList.class);
        }
        else
        {
            subjectList = cmService.getAllSubject();

            return new ForwardResolution(ADMIN_EDIT_TEXTBOOK);
        }
    }

    @HandlesEvent("updatebookcover")
    public Resolution updateBookCover()
    {
        logRequest();
        if (!sessionIsValid() || editingTextbook == null || bookCoverName == null)
        {
            return getStringTimeoutResolution();
        }

        editingTextbook.setCover(bookCoverName);
        try
        {
            cmService.updateTextbook(editingTextbook);
            return getStringResolution(editingTextbook.getCoverUrl());
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("doedittextbook")
    public Resolution doEditTextbook()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String bookName = getParam("bookName", "");
        String publisher = getParam("publisher", "");
        int subjectId = getParamInt("subject", -1);

        if (!paramsIsValid(bookName, publisher, subjectId))
        {
            return getStringResolution("dataError");
        }

        setParams(bookName, publisher, subjectId, editingTextbook, OPERATE_TYPE_EDIT);

        try
        {
            cmService.updateTextbook(editingTextbook);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("turntoaddtextbookpage")
    public Resolution turnToAddTextbookPage()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        subjectList = cmService.getAllSubject();

        emptyBookCover();
        return new ForwardResolution(ADMIN_ADD_TEXTBOOK);
    }

    @HandlesEvent("doaddtextbook")
    public Resolution doAddTextbook()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String bookName = getParam("bookName", "");
        String publisher = getParam("publisher", "");
        int subjectId = getParamInt("subject", -1);
        String content = getParam("content", "");

        if (!paramsIsValid(bookName, publisher, subjectId)
                || !chapterSectionLinesIsValid(content))
        {
            return getStringResolution("dataError");
        }

        // 先添加一条textbook记录
        Book book = new Book();
        setParams(bookName, publisher, subjectId, book, OPERATE_TYPE_ADD);

        try
        {
            cmService.insertTextbook(book); // 新生成的bookId
            int bookId = book.getBookId();

            String[] contents = content.split("\n");

            for (int i = 0; i < contents.length; i++)
            {
                contents[i] = contents[i].trim();
                Chapter chapter = new Chapter();
                chapter.setChapterName(contents[i].substring(1));
                chapter.setBookId(bookId);
                cmService.insertChapter(chapter);
            }
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("getbookcoverurl")
    public Resolution getBookCoverUrl()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        return getStringResolution(addedBookCoverUrl);
    }

    @HandlesEvent("uploadbookcover")
    public Resolution uploadBookCover()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }
        if (bookCoverFile == null)
        {
            return getStringResolution("error");
        }

        bookCoverName = Utils.getNextLongId() + "." + Utils.getFileSuffix(bookCoverFile.getFileName()).toLowerCase();
        String saveDir = Constants.PATH_FILE + Constants.Book.BOOK_COVER_FOLDER;
        Utils.makeSureDirExists(saveDir);

        try
        {
            bookCoverFile.save(new File(saveDir + bookCoverName));
            addedBookCoverUrl = Constants.URL_FILE + Constants.Book.BOOK_COVER_FOLDER + bookCoverName;

            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    // override
    // --------------------------------------------------------------------------------
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && (makeSureYjAdmin() || makeSureYjEditor());
    }

    // private
    // --------------------------------------------------------------------------------
    private void emptyBookCover()
    {
        bookCoverName = null;
        addedBookCoverUrl = "";
    }

    private boolean chapterSectionLinesIsValid(String content)
    {
        String[] lines = content.split("\n");
        for (String line : lines)
        {
            if (line.trim().length() <= 1 || line.length() > Constants.MaxLength.MAX_LENGTH_OF_CHAPTER_SECTION_NAME ||
                    !line.trim().startsWith("#"))
            {
                return false;
            }
        }

        return true;
    }

    private boolean paramsIsValid(String bookName, String publisher, int subjectId)
    {
        return Utils.stringNotEmpty(bookName)
                && bookName.length() <= Constants.MaxLength.TEXTBOOK_NAME
                && publisher.length() <= Constants.MaxLength.PUBLISHER
                && subjectId != -1;
    }

    private void setParams(String bookName, String publisher, int subjectId, Book book, String operateType)
    {
        book.setBookName(bookName);
        book.setPublisher(publisher);
        book.setSubjectId(subjectId);
        if (operateType.equals(OPERATE_TYPE_ADD) && bookCoverName != null)
        {
            book.setCover(bookCoverName);
        }
    }

}
