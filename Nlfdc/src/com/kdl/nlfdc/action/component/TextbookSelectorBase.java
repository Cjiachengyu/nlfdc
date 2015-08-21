package com.kdl.nlfdc.action.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.json.JSONArray;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.domain.Chapter;
import com.kdl.nlfdc.domain.Section;
import com.kdl.nlfdc.domain.Subject;
import com.kdl.nlfdc.domain.Book;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.service.CmService;

/**
 * 保证每一时刻currentSubject, currentTextbook, currentChapter, currentSection,
 * subjectList, textbookList, chapterList, currentChapter.getSectionList()
 * 是不为null且可以访问的。当currentXXX表示“所有XXX”时，其id为0。
 */
public abstract class TextbookSelectorBase implements Serializable
{
    private static final long serialVersionUID = -3943243429724571259L;

    protected static final int TB_ITEM_ALL = 0;

    // property
    // --------------------------------------------------------------------------------
    Subject currentSubject;
    Book currentTextbook;
    Chapter currentChapter;

    List<Subject> subjectList;
    List<Book> textbookList;
    List<Chapter> chapterList;

    public Subject getCurrentSubject()
    {
        return currentSubject;
    }

    public Book getCurrentTextbook()
    {
        return this.currentTextbook;
    }

    public Chapter getCurrentChapter()
    {
        return this.currentChapter;
    }

    public int getSubjectId()
    {
        return getCurrentSubject().getSubjectId();
    }

    public int getBookId()
    {
        return getCurrentTextbook().getBookId();
    }

    public int getChapterId()
    {
        return getCurrentChapter().getChapterId();
    }

    public List<Book> getAllTextbookList()
    {
        return allTextbookList;
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    public List<Book> getTextbookList()
    {
        return textbookList;
    }

    public List<Chapter> getChapterList()
    {
        return this.chapterList;
    }

    // public
    // --------------------------------------------------------------------------------
    public TextbookSelectorBase(CmService basicService, User user, String lang)
    {
        this.basicService = basicService;
        this.user = user;
        setLang(lang);

        switch (user.getUserRole())
        {
        case Constants.UserRole.YJ_TEACHER:
            allTextbookList = basicService.getTextbookForTeacher(user.getUserId());
            break;

        case Constants.UserRole.YJ_EDITOR:
        case Constants.UserRole.YJ_ADMIN:
        case Constants.UserRole.YJ_MASTER:
            allTextbookList = basicService.getAllTextbook();
            break;
        }

        initSubjectListByAllBookList();
        textbookList = allTextbookList;
        chapterList = new ArrayList<Chapter>();

        currentSubject = subjectAll;
        currentTextbook = textbookAll;
        currentChapter = chapterAll;
        
        setSelected();
    }

    public void setLang(String lang)
    {
        this.lang = lang;

        subjectAll.setSubjectName(lang.equals("en") ? "All Outlines" : "所有科目");
        textbookAll.setBookName(lang.equals("en") ? "All Textbooks" : "所有课本");
        chapterAll.setChapterName(lang.equals("en") ? "All Chapters" : "所有章");
    }

    public void selectSubject(int subjectId)
    {
        if (subjectId == TB_ITEM_ALL)
        {
            currentSubject = subjectAll;
        }
        else
        {
            for (Subject s : subjectList)
            {
                if (s.getSubjectId() == subjectId)
                {
                    currentSubject = s;
                    break;
                }
            }
        }

        if (currentSubject.getSubjectId() == TB_ITEM_ALL)
        {
            textbookList = allTextbookList;
        }
        else
        {
            textbookList = new ArrayList<Book>();
            for (Book t : allTextbookList)
            {
                if (t.getSubjectId() == currentSubject.getSubjectId())
                {
                    textbookList.add(t);
                }
            }
        }
        currentTextbook = textbookAll;

        chapterList = new ArrayList<Chapter>();
        currentChapter = chapterAll;

        setSelected();
    }

    public void selectTextbook(int bookId)
    {
        if (bookId == TB_ITEM_ALL)
        {
            currentTextbook = textbookAll;
        }
        else
        {
            for (Book t : allTextbookList)
            {
                if (t.getBookId() == bookId)
                {
                    currentTextbook = t;
                    break;
                }
            }
        }

        if (currentTextbook.getBookId() == TB_ITEM_ALL)
        {
            chapterList = new ArrayList<Chapter>();
        }
        else
        {
            chapterList = basicService.getChapterByTextId(currentTextbook.getBookId());
//            for (Chapter c : chapterList)
//            {
//                c.setSectionList(basicService.getSectionByChapterId(c.getChapterId()));
//            }
        }
        currentChapter = chapterAll;

        setSelected();
    }

    public void selectChapter(int chapterId)
    {
        if (chapterId == TB_ITEM_ALL)
        {
            currentChapter = chapterAll;
        }
        else
        {
            for (Chapter c : chapterList)
            {
                if (c.getChapterId() == chapterId)
                {
                    currentChapter = c;
                    break;
                }
            }
        }

        setSelected();
    }

    public JSONArray getTextbookJsonArray() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();

        for (Book text : textbookList)
        {
            JSONObject json = new JSONObject();
            json.put("bookId", text.getBookId());
            json.put("bookName", text.getBookName());
            jsonArray.put(json);
        }

        return jsonArray;
    }

    public JSONArray getChapterJsonArray() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();

        for (Chapter chapter : chapterList)
        {
            JSONObject json = new JSONObject();
            json.put("chapterId", chapter.getChapterId());
            json.put("chapterName", chapter.getChapterName());
            jsonArray.put(json);
        }

        return jsonArray;
    }

    public JSONArray getSectionJsonArray() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();

        for (Section section : currentChapter.getSectionList())
        {
            JSONObject json = new JSONObject();
            json.put("sectionId", section.getSectionId());
            json.put("sectionName", section.getSectionName());
            jsonArray.put(json);
        }

        return jsonArray;
    }

    // protected
    // --------------------------------------------------------------------------------
    protected CmService basicService;
    protected User user;
    protected String lang;

    protected Subject subjectAll;
    protected Book textbookAll;
    protected Chapter chapterAll;

    protected List<Book> allTextbookList;

    // private
    // --------------------------------------------------------------------------------
    private void setSelected()
    {
        for (Subject s : subjectList)
        {
            s.setIsSelected(false);
        }

        for (Book t : textbookList)
        {
            t.setIsSelected(false);
        }

        for (Chapter c : chapterList)
        {
            c.setIsSelected(false);
        }

        currentSubject.setIsSelected(true);
        currentTextbook.setIsSelected(true);
        currentChapter.setIsSelected(true);
    }

    private void initSubjectListByAllBookList()
    {
        subjectList = new ArrayList<Subject>();
        List<Subject> allSubjectList = basicService.getAllSubject();
        for (Subject s : allSubjectList)
        {
            boolean keepSubject = false;
            for (Book t : allTextbookList)
            {
                if (t.getSubjectId() == s.getSubjectId())
                {
                    keepSubject = true;
                    break;
                }
            }

            if (keepSubject)
            {
                this.subjectList.add(s);
            }
        }

        for (Subject s : subjectList)
        {
            List<Book> bookList = new ArrayList<Book>();

            for (Book b : allTextbookList)
            {
                if (b.getSubjectId() == s.getSubjectId())
                {
                    bookList.add(b);
                }
            }

            s.setBookList(bookList);
        }

        subjectAll.setBookList(allTextbookList);
    }

    // create
    // --------------------------------------------------------------------------------
    {
        subjectAll = new Subject();
        subjectAll.setSubjectId(TB_ITEM_ALL);

        textbookAll = new Book();
        textbookAll.setBookId(TB_ITEM_ALL);

        chapterAll = new Chapter();
        chapterAll.setSectionList(new ArrayList<Section>());
        chapterAll.setChapterId(TB_ITEM_ALL);
    }

}
