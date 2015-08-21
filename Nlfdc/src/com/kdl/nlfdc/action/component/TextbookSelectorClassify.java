package com.kdl.nlfdc.action.component;

import java.io.Serializable;

import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.service.CmService;

public class TextbookSelectorClassify extends TextbookSelectorBase implements Serializable
{
    private static final long serialVersionUID = -7628693491600188261L;

    public TextbookSelectorClassify(CmService basicService, User user, String lang)
    {
        super(basicService, user, lang);

        subjectList.add(0, subjectAll);
        textbookList.add(0, textbookAll);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    public void selectSubject(int subjectId)
    {
        // 分类对话框不可能选择科目
        super.selectSubject(subjectId);
    }

    @Override
    public void selectTextbook(int bookId)
    {
        super.selectTextbook(bookId);

        // 先清空节
        // currentChapter.getSectionList().clear();

        // 再设置章列表：如果选择的是"所有课本"，则清空章列表；否则设为章列表，并加入"所有章"
        if (currentTextbook.getBookId() == TB_ITEM_ALL)
        {
            chapterList.clear();
        }
        else if (chapterList.size() > 0 && chapterList.get(0).getChapterId() != TB_ITEM_ALL)
        {
            chapterList.add(0, chapterAll);
        }
    }

    @Override
    public void selectChapter(int chapterId)
    {
        super.selectChapter(chapterId);

        // 设置节列表：如果选择的是"所有章"，则清空节列表；否则设为节列表，并加入"所有节"
//        if (currentChapter.getChapterId() == TB_ITEM_ALL)
//        {
//            currentChapter.getSectionList().clear();
//        }
//        else if (currentChapter.getSectionList().size() > 0
//                && currentChapter.getSectionList().get(0).getSectionId() != TB_ITEM_ALL)
//        {
//            currentChapter.getSectionList().add(0, sectionAll);
//        }
    }
}
