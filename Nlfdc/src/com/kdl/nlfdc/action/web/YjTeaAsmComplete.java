package com.kdl.nlfdc.action.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Asm;
import com.kdl.nlfdc.domain.AsmQue;
import com.kdl.nlfdc.domain.AsmRes;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.ClsAsm;
import com.kdl.nlfdc.domain.ClsAsmComplete;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.StuAnswerItem;
import com.kdl.nlfdc.domain.StuAsm;

@SessionScope
@UrlBinding("/yjteacherassignmentinfoaction")
public class YjTeaAsmComplete extends AbstractActionBean
{
    private static final long serialVersionUID = 3070828202984463916L;

    private static final int MAX_LENGTH_OF_FEEDBACK = 512;

    private static final String MAIN_ASSIGNMENT_COMPLETION = "/WEB-INF/jsp/yjteacher/YjTeacherAsmCompletion.jsp";
    
    private static final String CORRECT_BATCH_STU_ASM = "/WEB-INF/jsp/yjteacher/YjTeacherCorrectBatchStuAsm.jsp";
    private static final String CORRECT_ONE_STU_ASM = "/WEB-INF/jsp/yjteacher/YjTeacherCorrectOneStuAsm.jsp";
    private static final String CORRECTED_STU_ASM = "/WEB-INF/jsp/yjteacher/YjTeacherCorrectedStuAsmView.jsp";
    private static final String MAIN_VIDEO_VIEW = "/WEB-INF/jsp/user/UserResourceVideoViewNew.jsp";

    private Asm currentAsm;
    private AsmQue asmQue;
    private List<Res> asmContainedResList;       // 一份作业中包含的资源
    private List<ClsAsmComplete> assignedClassAssignmentList;
    private List<ClsAsmComplete> unassignClassAssignmentList;
    private ClsAsmComplete currentClassAssignment;

    private List<StuAnswerItem> stuAnswerItemList;
    private int correctingStuId;

    private StuAsm correctedStuAsm;
    private StuAsm correctingStuAsm;          // 用于老师批改时显示学生的姓名和提交作答的时间
    private String fileUrl;         // 查看学生作答中的视频


    public StuAsm getCorrectingStuAsm()
    {
        return correctingStuAsm;
    }

    public int getIsMobile()
    {
        // 视频预览页面在其他地方用到时要区分移动端和网页端
        return 0;
    }

    public List<Res> getAsmContainedResList()
    {
        return asmContainedResList;
    }

    public String getFileType()
    {
        // 现在幼教学生端上传的视频只有MP4格式
        return "mp4";
    }

    public String getFileUrl()
    {
        return fileUrl;
    }

    public StuAsm getCorrectedStuAsm()
    {
        return correctedStuAsm;
    }

    public Asm getCurrentAsm()
    {
        return currentAsm;
    }

    public AsmQue getAsmQue()
    {
        return asmQue;
    }

    public List<ClsAsmComplete> getAssignedClassAssignmentList()
    {
        return assignedClassAssignmentList;
    }

    public List<ClsAsmComplete> getUnassignClassAssignmentList()
    {
        return unassignClassAssignmentList;
    }

    public ClsAsmComplete getCurrentClassAssignment()
    {
        return currentClassAssignment;
    }

    public List<StuAnswerItem> getStuAnswerItemList()
    {
        return stuAnswerItemList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("assignmentinfo")
    public Resolution assignmentInfo()
    {
        logRequest();
        validateSession();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int asmId = getParamInt("asmId");

        refreshClassAsmList(asmId);

        initAsmContent(asmId);

        return new ForwardResolution(MAIN_ASSIGNMENT_COMPLETION);
    }

    @HandlesEvent("sendassignment")
    public Resolution sendAssignment()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int clsId = getParamInt("clsId");

        try
        {
            cmService.sendAssignmentToClass(getCurrentUserId(), currentAsm.getAsmId(), clsId);
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }
    }

    @HandlesEvent("selectclass")
    public Resolution selectClass()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int clsId = getParamInt("clsId");

        if (currentClassAssignment.getClsId() == clsId)
        {
            return getStringResolution("no_refresh");
        }
        else
        {
            for (ClsAsmComplete tca : assignedClassAssignmentList)
            {
                if (tca.getClsId() == clsId)
                {
                    currentClassAssignment = tca;
                    break;
                }
            }
            return getStringResolution("refresh");
        }
    }

    @HandlesEvent("getcorrectbatchstuasmview")
    public Resolution getCorrectBatchStuAsmView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        return new ForwardResolution(CORRECT_BATCH_STU_ASM);
    }

    @HandlesEvent("docorrectbatchstuasm")
    public Resolution doCorrectBatchStuAsm()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String feedback = getParam("feedback");
        int star = getParamInt("star", 0); // star: 1-5, 表示得分
        String correctedStuIds = getParam("correctedStuIds", "");

        if (feedback.length() > MAX_LENGTH_OF_FEEDBACK)
        {
            return getStringResolution("long_param");
        }

        // correctedStuIds示例："1,2,4,5," 如果一个都没有选，则传'no_checked'
        if (!correctedStuIds.equals("no_checked"))
        {
            int asmId = currentClassAssignment.getAsmId();
            int clsId = currentClassAssignment.getClsId();
            try
            {
                for (int stuId : Utils.splitIdList(correctedStuIds, ","))
                {
                    StuAsm stuAsm = cmService.getStuAsm(asmId, stuId, clsId);
                    if (stuAsm.getIsCorrected() != 1)
                    {
                        cmService.correctYjAsm(asmId, clsId, stuId, star, feedback);
                    }
                }
            }
            catch (Exception e)
            {
                return getStringResolution("exception");
            }
        }

        return getStringResolution("ok");
    }

    @HandlesEvent("getcorrectonestuasmview")
    public Resolution getCorrectOneStuAsmView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        correctingStuId = getParamInt("stuId", -1);

        if (correctingStuId == -1)
        {
            // 自动批改下一题的时候没有传stuId过来，直接从需要批改的
            refreshClassAsmList(currentAsm.getAsmId());
            if (currentClassAssignment.getUncorrectedList() != null
                    && currentClassAssignment.getUncorrectedList().size() != 0)
            {
                correctingStuId = currentClassAssignment.getUncorrectedList().get(0).getStuId();
            }
            else
            {
                return getStringResolution("no_next");
            }
        }

        int asmId = currentClassAssignment.getAsmId();
        int clsId = currentClassAssignment.getClsId();

        stuAnswerItemList = cmService.getStuAnswerItemList(asmId, clsId, correctingStuId);
        correctingStuAsm = cmService.getStuAsm(asmId, correctingStuId, clsId);
        String stuName = cmService.getUser(correctingStuId).getUserName();
        correctingStuAsm.setStudentName(stuName);

        return new ForwardResolution(CORRECT_ONE_STU_ASM);
    }

    @HandlesEvent("getcorrectedstuasmview")
    public Resolution getCorrectedStuAsmView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int stuId = getParamInt("stuId", -1);
        int getNext = getParamInt("getNext", -1);

        if (getNext == 1)
        {
            // 传参数标记获取下一个
            List<StuAsm> finishedStuAsmList = currentClassAssignment.getFinishedList();

            for (int index = 0; index < finishedStuAsmList.size(); index++)
            {
                if (finishedStuAsmList.get(index).getStuId() == stuId)
                {
                    if (index == (finishedStuAsmList.size() - 1))
                    {
                        // stuId = finishedStuAsmList.get(0).getStuId();
                        // break;
                        return getStringResolution("no_next");
                    }
                    else
                    {
                        stuId = finishedStuAsmList.get(index + 1).getStuId();
                        break;
                    }
                }
            }
        }

        int asmId = currentClassAssignment.getAsmId();
        int clsId = currentClassAssignment.getClsId();

        stuAnswerItemList = cmService.getStuAnswerItemList(asmId, clsId, stuId);

        correctedStuAsm = cmService.getStuAsm(asmId, stuId, clsId);
        String stuName = cmService.getUser(stuId).getUserName();
        correctedStuAsm.setStudentName(stuName);

        return new ForwardResolution(CORRECTED_STU_ASM);
    }

    @HandlesEvent("docorrectonestuasm")
    public Resolution doCorrectOneStuAsm()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String feedback = getParam("feedback");
        int star = getParamInt("star", 0);
        if (feedback.length() > MAX_LENGTH_OF_FEEDBACK)
        {
            return getStringResolution("long_param");
        }

        try
        {
            StuAsm stuAsm = cmService.getStuAsm(currentClassAssignment.getAsmId(), correctingStuId,
                    currentClassAssignment.getClsId());
            if (stuAsm.getIsCorrected() != 1)
            {
                cmService.correctYjAsm(currentClassAssignment.getAsmId(), currentClassAssignment.getClsId(),
                        correctingStuId, star, feedback);
            }
        }
        catch (Exception e)
        {
            return getStringResolution("exception");
        }

        return getStringResolution("ok");
    }

    @HandlesEvent("checkansweritemvideo")
    public Resolution checkAnswerItemVideo()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        fileUrl = getParam("fileUrl");

        return new ForwardResolution(MAIN_VIDEO_VIEW);
    }


    // override
    // --------------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    // private
    // --------------------------------------------------------------------------------
    private void refreshClassAsmList(int asmId)
    {
        currentAsm = cmService.getAsm(asmId);

        assignedClassAssignmentList = new ArrayList<ClsAsmComplete>();
        unassignClassAssignmentList = new ArrayList<ClsAsmComplete>();
        List<Cls> classList = cmService.getClassListOfAUser(getCurrentUserId());
        for (Cls cls : classList)
        {
            refreshStuAsmList(asmId, cls);
        }

        if (assignedClassAssignmentList.size() > 0)
        {
            if (currentClassAssignment == null)
            {
                currentClassAssignment = assignedClassAssignmentList.get(0);
            }
            else
            {
                // 防止有多个班级时每次刷新都切换到列表中第一个班级
                for (ClsAsmComplete clsAsmComplete : assignedClassAssignmentList)
                {
                    if (currentClassAssignment.getClsId() == clsAsmComplete.getClsId())
                    {
                        currentClassAssignment = clsAsmComplete;
                        return;
                    }
                }
                currentClassAssignment = assignedClassAssignmentList.get(0);
            }
        }
    }

    private void refreshStuAsmList(int asmId, Cls cls)
    {
        int clsId = cls.getClsId();
        ClsAsmComplete clsAsmComplete = new ClsAsmComplete();
        clsAsmComplete.setAsmId(asmId);
        clsAsmComplete.setAsmName(currentAsm.getAsmName());
        clsAsmComplete.setClsId(clsId);
        clsAsmComplete.setClsName(cls.getClsName());

        ClsAsm clsAsm = cmService.getClsAsm(asmId, clsId);
        if (clsAsm == null)
        {
            unassignClassAssignmentList.add(clsAsmComplete);
        }
        else
        {
            SimpleDateFormat ssdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            clsAsmComplete.setAssignTimeString(ssdf.format(Long.valueOf(clsAsm.getAssignTime()) * 1000));
            clsAsmComplete.setStatusId(clsAsm.getStatusId());

            List<StuAsm> stuAsmList = cmService.getStuAsmOfAClass(asmId, clsId);
            initDiffStatusAssignList(clsAsmComplete, stuAsmList);
            assignedClassAssignmentList.add(clsAsmComplete);
        }
    }

    private void initDiffStatusAssignList(ClsAsmComplete clsAsmComplete, List<StuAsm> stuAsmList)
    {
        List<StuAsm> finishedList = new ArrayList<StuAsm>();
        List<StuAsm> undownloadList = new ArrayList<StuAsm>();
        List<StuAsm> uncorrectedList = new ArrayList<StuAsm>();

        findDiffStatusAssignList(stuAsmList, finishedList, uncorrectedList, undownloadList);

        Collections.sort(finishedList, comparatorByStuAsmSubmitTime);
        Collections.sort(undownloadList, comparatorByStuAsmSubmitTime);
        Collections.sort(uncorrectedList, comparatorByStuAsmSubmitTime);

        clsAsmComplete.setFinishedList(finishedList);
        clsAsmComplete.setUncorrectedList(uncorrectedList);
        clsAsmComplete.setUndownloadedList(undownloadList);
    }

    private void findDiffStatusAssignList(List<StuAsm> stuAsmList, List<StuAsm> finishedList,
            List<StuAsm> uncorrectedList, List<StuAsm> undownloadList)
    {
        String lang = (String) getCurrentSession().getAttribute("lang");
        for (StuAsm stuAsm : stuAsmList)
        {
            stuAsm.setLang(lang);
            int statusId = stuAsm.getStatusId();

            switch (statusId)
            {
            case Constants.AsmStatus.FINISHED:
            case Constants.AsmStatus.SENDBACK:
                finishedList.add(stuAsm);
                break;

            case Constants.AsmStatus.NEW_ASSIGNED:
            case Constants.AsmStatus.NEW_RECEIVED:
            case Constants.AsmStatus.ANSWERING:
            case Constants.AsmStatus.ANSWERED:
                undownloadList.add(stuAsm);
                break;

            case Constants.AsmStatus.ANSWER_SUBMITTED:
            case Constants.AsmStatus.ANSWER_RECEIVED:
            case Constants.AsmStatus.CORRECTING:
            case Constants.AsmStatus.CORRECTED:
                uncorrectedList.add(stuAsm);
                break;
            }
        }
    }

    // 获取作业所包含的资源
    private void initAsmContent(int asmId)
    {
        // 获取Question中的content（作业描述）
        asmQue = cmService.getAsmQueOfAnAsm(asmId);

        asmContainedResList = new ArrayList<Res>();
        List<AsmRes> asmResList = cmService.getAsmResList(asmId);
        for (AsmRes asmRes : asmResList)
        {
            Res res = cmService.getResourceInfo(asmRes.getResId());
            res.setResFileType(getResFileType(res.getResFileSuffix()));
            asmContainedResList.add(res);
        }
    }

    /**
     * 根据后缀名来获取资源文件的分类，（视频、图片、音频、其他）
     * 
     * @param resFileSuffix
     * @return
     */
    private String getResFileType(String resFileSuffix)
    {
        String resFileType = Constants.ResourceFileType.OTHER_TYPE;

        if (Constants.ResourceFileType.ALL_VIDEO_TYPE_LIST.contains(resFileSuffix))
        {
            resFileType = Constants.ResourceFileType.VIDEO_TYPE;
        }
        else if (Constants.ResourceFileType.IMG_TYPE_LIST.contains(resFileSuffix))
        {
            resFileType = Constants.ResourceFileType.IMAGE_TYPE;
        }
        else if (Constants.ResourceFileType.ALL_AUDIO_TYPE_LIST.contains(resFileSuffix))
        {
            resFileType = Constants.ResourceFileType.AUDIO_TYPE;
        }

        return resFileType;
    }


    private static Comparator<StuAsm> comparatorByStuAsmSubmitTime = new Comparator<StuAsm>()
    {
        @Override
        public int compare(StuAsm o1, StuAsm o2)
        {
            // 可能需要改动（升序或降序）
            return o1.getBeginSubmitTime() - o2.getBeginSubmitTime();
        }
    };
}
