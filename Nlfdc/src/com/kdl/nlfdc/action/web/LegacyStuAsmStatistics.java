package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Utils;

/**
 * IOS 调用的是这个URL
 */
@SessionScope
@UrlBinding("/StudentAssignmentStatistics.action")
public class LegacyStuAsmStatistics extends AbstractActionBean
{
    private static final long serialVersionUID = 7413323347546161081L;

    @DefaultHandler
    public Resolution defaultHandler()
    {
        logRequest();

        String stuId = getParam("s" + "tudentId");
        String asmId = getParam("a" + "ssignmentId");
        String clsId = getParam("c" + "lassId");
        String lang = getParam("l" + "ang");

        String tokenId = getParam("t" + "okenId", "");

        if (Utils.stringEmpty(tokenId))
        {
            return new RedirectResolution("/studentassignmentstatisticsaction?assignmentStatistics"
                    + "&asmId=" + asmId
                    + "&stuId=" + stuId
                    + "&clsId=" + clsId
                    + "&lang=" + lang);
        }
        else
        {
            return new RedirectResolution("/studentassignmentstatisticsaction?assignmentStatistics"
                    + "&asmId=" + asmId
                    + "&stuId=" + stuId
                    + "&clsId=" + clsId
                    + "&tokenId=" + tokenId
                    + "&lang=" + lang);
        }
    }
}
