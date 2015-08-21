package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.TeaBook;
import com.kdl.nlfdc.domain.TeaSubject;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

@SessionScope
@UrlBinding("/yjteachersettingaction")
public class YjTeaSetting extends AbstractUserSettingManageBook
{
    private static final long serialVersionUID = 4327400800449313038L;

    private static final String EDIT_INFO = "/WEB-INF/jsp/yjteacher/YjTeacherEditInfo.jsp";
    private static final String EDIT_PWD = "/WEB-INF/jsp/yjteacher/YjTeacherEditPwd.jsp";

    private static final int SUB_MENU_INFO = 1;
    private static final int SUB_MENU_PWD = 2;

    private int subMenuType;

    private List<Subject> subjectList;

    public List<Subject> getSubjectList()
    {
        return subjectList;
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

        getCurrentSession().setAttribute("currentMemuOperation", "");

        tsCatalogueModule = initTsCatalogue();

        subMenuType = getParamInt("subMenuType", SUB_MENU_INFO);
        switch (subMenuType)
        {
        case SUB_MENU_INFO:
            refreshTeaSubjectList();
            refreshBookList();
            return new ForwardResolution(EDIT_INFO);

        case SUB_MENU_PWD:
            return new ForwardResolution(EDIT_PWD);

        default:
            return getYjLogoutResolution();
        }
    }

    @HandlesEvent("changesubject")
    public Resolution changeSubject()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int subjectId = getParamInt("subjectId", 0);
        int checked = getParamInt("checked", -1);

        if (subjectId == 0 || checked == -1)
        {
            return getStringResolution("error");
        }

        // checked 有两个值，1表示选中，0表示取消
        if (checked == 1)
        {
            TeaSubject userSubject = new TeaSubject();
            userSubject.setTeaId(getCurrentUserId());
            userSubject.setSubjectId(subjectId);

            try
            {
                cmService.insertUserSubject(userSubject);
            }
            catch (Exception e)
            {
                return getStringResolution("error");
            }
            return getStringResolution("ok");
        }

        if (checked == 0)
        {
            try
            {
                cmService.deleteUserSubject(getCurrentUserId(), subjectId);
                // 取消科目的时候，要删除该老师已经收藏的该科目的课本
                cmService.deleteTeacherTextbookBySubjectId(subjectId, getCurrentUserId());

                clearUserBookCache();
                return getStringResolution("ok");
            }
            catch (Exception e)
            {
                return getStringResolution("error");
            }
        }
        return getStringResolution("error");
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    protected void refreshBookList()
    {
        userBookList = cmService.getTextbookForTeacher(getCurrentUserId());
    }

    @Override
    protected void refreshAddBookList()
    {
        // 查出该学校该老师所任科目的未收藏课本
        userBookListToAdd = cmService.getTextbookForTeacherExceptFav(
                getCurrentUser().getSchoolId(), getCurrentUserId());
    }

    @Override
    protected void doAddUserBookList(List<Integer> bookIds) throws SqlAffectedCountException
    {
        for (int bookId : bookIds)
        {
            TeaBook tt = new TeaBook();
            tt.setTeaId(getCurrentUserId());
            tt.setBookId(bookId);

            // 判断是否已存在
            for (Book textbook : userBookList)
            {
                if (textbook.getBookId() == bookId)
                {
                    continue;
                }
            }
            cmService.insertTeacherTextbook(tt);
        }
    }

    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    @Override
    protected void doDeleteUserBook(int bookId) throws SqlAffectedCountException
    {
        cmService.deleteTeacherTextbook(getCurrentUserId(), bookId);
        refreshBookList();

        clearUserBookCache();
    }
    
    // private
    // --------------------------------------------------------------------------------
    private void clearUserBookCache() throws SqlAffectedCountException
    {
        int cacheBookId = getCurrentUser().getCacheBookId();
        if (cacheBookId != 0)
        {
            List<TeaBook> teacherTextbooks = cmService.getTeacherTextbook(
                    cacheBookId, getCurrentUserId());
            if (teacherTextbooks.size() == 0)
            {
                // 课本已经被删除，需要清空userCache
                tsCatalogueModule.selectTextbook(0);
                updateUserTsCache(tsCatalogueModule);
            }
        }
    }
    
    private void refreshTeaSubjectList()
    {
        subjectList = cmService.getAllSubject();

        List<Subject> userSubjectList = cmService.getTeacherSubjects(getCurrentUserId());

        for (Subject subj : subjectList)
        {
            for (Subject userSubj : userSubjectList)
            {
                if (userSubj.getSubjectId() == subj.getSubjectId())
                {
                    subj.setIsSelected(true);
                    break;
                }
            }
        }
    }
    
}
