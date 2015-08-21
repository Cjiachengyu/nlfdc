package com.kdl.nlfdc.action.component;

import java.io.Serializable;

import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.service.CmService;

public class TextbookSelectorCatalogue extends TextbookSelectorBase implements Serializable
{
    private static final long serialVersionUID = -6917949662620803038L;

    public TextbookSelectorCatalogue(CmService basicService, User user, String lang)
    {
        super(basicService, user, lang);
    }

    // override
    // --------------------------------------------------------------------------------
    @Override
    public void selectSubject(int subjectId)
    {
        return;
    }

    @Override
    public void selectTextbook(int bookId)
    {
        super.selectTextbook(bookId);

        if (currentTextbook.getBookId() != TB_ITEM_ALL)
        {
            chapterList.add(0, chapterAll);
        }
    }

//    @Override
//    public void selectSection(int sectionId)
//    {
//        super.selectSection(sectionId);
//
//        if (currentSection.getSectionId() != TB_ITEM_ALL)
//        {
//            for (Chapter c : chapterList)
//            {
//                c.setIsSelected(false); // 否则章和节会同时高亮
//            }
//        }
//    }

}
