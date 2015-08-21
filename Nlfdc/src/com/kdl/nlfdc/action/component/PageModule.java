package com.kdl.nlfdc.action.component;

import java.io.Serializable;

import net.sourceforge.stripes.validation.Validate;

public class PageModule implements Serializable
{
    private static final long serialVersionUID = -4738597663290673302L;

    @Validate(mask = "^[0-9]*[1-9][0-9]*$")
    private int pageSize = 1;
    @Validate(mask = "^[0-9]*[1-9][0-9]*$")
    private int pageCount = 1;
    @Validate(mask = "^[0-9]*[1-9][0-9]*$")
    private int currentPage = 1;

    public int getPageSize()
    {
        return this.pageSize;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public int getLimitBegin()
    {
        return (currentPage - 1) * pageSize;
    }

    // methods
    // --------------------------------------------------------------------------------
    public PageModule(int pageSize)
    {
        this.pageSize = pageSize;
        gotoPage(1);
    }

    public void gotoPage(int pageNum)
    {
        if (pageNum > pageCount)
        {
            pageNum = pageCount;
        }

        currentPage = pageNum;
    }

    public void changeItemsCount(int itemsCount)
    {
        if (itemsCount <= 0)
        {
            pageCount = 1;
            return;
        }

        pageCount = ((itemsCount - 1) / pageSize) + 1;
    }
}
