package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.domain.Subject;

/**
 * 校长端班级管理和老师管理之间subject统一类
 * 
 * 校长在老师管理中选择了某个subject，跳转到班级的管理的时候初始化的subject要跟老师管理中相同，这边是通过Session来保存subject的
 */
@SessionScope
public abstract class AbstractMasterSelectSubject extends AbstractActionBean
{
    private static final long serialVersionUID = 5020983935112599284L;

    // page state
    // --------------------------------------------------------------------------------
    protected List<Subject> subjects;
    protected int subjectId = 0;

    public int getSubjectId()
    {
        return subjectId;
    }

    public List<Subject> getSubjects()
    {
        return subjects;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @HandlesEvent("selectsubject")
    public Resolution selectSubject()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        subjectId = getParamInt("subjectId", 0);
        getCurrentSession().setAttribute("current_subject", subjectId);

        return selectSubjectReturnList();
    }

    // protected
    // --------------------------------------------------------------------------------
    protected void initMasterSubject()
    {
        Object obj = getCurrentSession().getAttribute("current_subjects");
        if (obj == null)
        {
            // 第一次
            subjects = cmService.getSchoolSubjects(getCurrentUser().getSchoolId());
            Subject subjectAll = new Subject();
            subjectAll.setSubjectId(0);
            subjectAll.setSubjectName("所有科目");
            subjects.add(0, subjectAll);

            getCurrentSession().setAttribute("current_subjects", subjects);
        }
        else
        {
            subjects = (List<Subject>) obj;
        }

        obj = getCurrentSession().getAttribute("current_subject");
        if (obj == null)
        {
                subjectId = 0;

            getCurrentSession().setAttribute("current_subject", subjectId);
        }
        else
        {
            subjectId = (Integer) obj;
        }
    }

    // abstract
    // --------------------------------------------------------------------------------
    protected abstract Resolution selectSubjectReturnList();

}
