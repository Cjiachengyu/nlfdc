package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

/**
 * 老师和学生管理自己课本的公共操作
 * 
 * 1. 返回用户书架jsp
 * 2. 返回用户添加课本jsp
 * 3. 添加课本
 * 4. 删除课本
 *
 */
@SessionScope
public abstract class AbstractUserSettingManageBook extends AbstractUserSetting
{
    private static final long serialVersionUID = -2753060724778416102L;

    protected static final String USER_BOOK_LIST = "/WEB-INF/jsp/component/UserBookList.jsp";
    protected static final String USER_BOOK_LIST_TO_ADD = "/WEB-INF/jsp/component/UserBookListToAdd.jsp";

    protected List<Book> userBookList;      // 用户已经收藏的课本
    protected List<Book> userBookListToAdd;    // 用户可以选择收藏的课本

    public List<Book> getUserBookList()
    {
        return userBookList;
    }

    public List<Book> getUserBookListToAdd()
    {
        return userBookListToAdd;
    }

    // resolution
    // --------------------------------------------------------------------------------

    @HandlesEvent("getuserbooklistview")
    public Resolution getUserBookListView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(USER_BOOK_LIST);
    }

    @HandlesEvent("getuserbooklisttoaddview")
    public Resolution getUserBookListToAddView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        refreshAddBookList();

        return new ForwardResolution(USER_BOOK_LIST_TO_ADD);
    }

    @HandlesEvent("adduserbooklist")
    public Resolution addUserBookList()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        try
        {
            List<Integer> textbookIds = Utils.splitIdList(getParam("textbookIds"), "-");

            doAddUserBookList(textbookIds);

            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    @HandlesEvent("deleteuserbook")
    public Resolution deleteUserBook()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        try
        {
            int bookId = getParamInt("bookId");
            doDeleteUserBook(bookId);

            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }

    // abstract
    // --------------------------------------------------------------------------------
    protected abstract void refreshBookList();

    protected abstract void refreshAddBookList();

    protected abstract void doAddUserBookList(List<Integer> bookIds) throws SqlAffectedCountException;

    protected abstract void doDeleteUserBook(int bookId) throws SqlAffectedCountException;
}
