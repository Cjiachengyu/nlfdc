package com.kdl.nlfdc.action.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Utils;

/**
 * Android和Windows调用的是这个URL
 */
@SessionScope
@UrlBinding("/AssignmentStatistics.action")
public class LegacyAsmStatistics extends AbstractActionBean
{
    private static final long serialVersionUID = 7413323347546161081L;

    @DefaultHandler
    public Resolution assignmentStatisticsOfTeacher()
    {
        logRequest();

        String teaId = getParam("t" + "eacherId");
        String asmId = getParam("a" + "ssignmentId");
        String clsId = getParam("c" + "lassId");
        String lang = getParam("l" + "ang");
        String tokenId = getParam("t" + "okenId", "");

        if (Utils.stringEmpty(tokenId))
        {
            return new RedirectResolution("/teacherassignmentstatisticsaction?assignmentStatistics"
                    + "&asmId=" + asmId
                    + "&teaId=" + teaId
                    + "&clsId=" + clsId
                    + "&lang=" + lang);
        }
        else
        {
            return new RedirectResolution("/teacherassignmentstatisticsaction?assignmentStatistics"
                    + "&asmId=" + asmId
                    + "&teaId=" + teaId
                    + "&clsId=" + clsId
                    + "&tokenId=" + tokenId
                    + "&lang=" + lang);
        }
    }

    public Resolution assignmentStatisticsOfStudent()
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
