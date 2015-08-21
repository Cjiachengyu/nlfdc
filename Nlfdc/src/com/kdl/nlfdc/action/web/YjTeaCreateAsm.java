package com.kdl.nlfdc.action.web;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.tomcat.util.json.JSONArray;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.action.component.IUploadResource;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.domain.Asm;
import com.kdl.nlfdc.domain.AsmQue;
import com.kdl.nlfdc.domain.Cls;
import com.kdl.nlfdc.domain.Res;
import com.kdl.nlfdc.domain.ResFile;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.domain.YjAsmResNum;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

/**
 * 幼教老师：
 * 创建作业
 *
 * @author cjia
 * @version 创建时间：2015年3月27日
 */
@SessionScope
@UrlBinding("/yjteacreateasmaction")
public class YjTeaCreateAsm extends AbstractResList implements IUploadResource
{
    private static final long serialVersionUID = 6280328177763003413L;

    private String CREATE_ASM_1 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm1.jsp";
    private String CREATE_ASM_2 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm2.jsp";
    private String CREATE_ASM_3 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm3.jsp";
    private String CREATE_ASM_MSG = "/WEB-INF/jsp/yjteacher/MobileYjTeacherCreateMsg.jsp";

    private String MOBILE_UPLOAD_RES_PAGE = "/WEB-INF/jsp/yjteacher/MobileYjTeacherUploadRes.jsp";

    private String YJ_ASM_RESOURCE_LIST_VIEW = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsmResListView.jsp";

    private static final int DEFAULT_YJ_ASM_SCORE = 5;
    private static final String YJ_ASM_RESOURCE_FOLDER_NAME = "resource";

    private int RESOURCE_FROM_TYPE = Constants.ResourceFromType.FROM_COMPANY;   // 初始化为系统资源
    private List<YjAsmResNum> createAsmResNums = new ArrayList<YjAsmResNum>();  // 保存添加到作业的resId和resNum
    private List<Res> createAsmRes = new ArrayList<Res>();                      // 保存添加到任务中的资源，在创建任务的第二步显示

    private List<Cls> classesOfTeacher;                                         // 添加资源的时候用到（共享到班级），最后创建作业的时候用到（发布作业到班级）

    private String createAsmSuccessOrFailMsg;

    private int isCreated;                                                      // 防止同一份任务多次创建
    private String createAsmToken = Utils.getRandomString(32);                  // 防止同一份任务多次创建

    public String getCreateAsmToken()
    {
        return createAsmToken;
    }

    public String getCreateAsmSuccessOrFailMsg()
    {
        return createAsmSuccessOrFailMsg;
    }

    public List<Cls> getClassesOfTeacher()
    {
        return classesOfTeacher;
    }

    public int getResFromType()
    {
        return RESOURCE_FROM_TYPE;
    }

    public List<Res> getCreateAsmRes()
    {
        return createAsmRes;
    }

    @Override
    public int getShowType()
    {
        return 0; // 决定资源列表后面显示哪种操作， 0 对应创建作业（加入作业、移出作业）
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

        resetCreatableLabel();

        classesOfTeacher = cmService.getClassListOfAUser(getCurrentUserId());

        tsClassifyModule = initTsClassifyModule();
        tsCatalogueModule = initTsCatalogue();

        pageModule = new PageModule(ITEM_PAGESIZE);

        String tokenId = getParam("tokenId", null);
        if (tokenId != null)
        {
            CREATE_ASM_1 = "/WEB-INF/jsp/yjteacher/MobileYjTeacherCreateAsm1.jsp";
            CREATE_ASM_2 = "/WEB-INF/jsp/yjteacher/MobileYjTeacherCreateAsm2.jsp";
            CREATE_ASM_3 = "/WEB-INF/jsp/yjteacher/MobileYjTeacherCreateAsm3.jsp";
            RESOURCE_LIST_VIEW = "/WEB-INF/jsp/yjteacher/MobileResourceListView.jsp";
        }
        else
        {
            CREATE_ASM_1 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm1.jsp";
            CREATE_ASM_2 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm2.jsp";
            CREATE_ASM_3 = "/WEB-INF/jsp/yjteacher/YjTeacherCreateAsm3.jsp";
            RESOURCE_LIST_VIEW = "/WEB-INF/jsp/component/ResourceListView.jsp";
        }

        refreshResourceList();

        return new ForwardResolution(CREATE_ASM_1);
    }

    @HandlesEvent("mobileselecttext")
    public Resolution mobileSelectText() throws SqlAffectedCountException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int bookId = getParamInt("bookId");
        tsCatalogueModule.selectTextbook(bookId);
        updateUserTsCache(tsCatalogueModule);
        pageModule.gotoPage(1);

        refreshResourceList();

        return new ForwardResolution(CREATE_ASM_1);
    }

    @HandlesEvent("mobileselectchapter")
    public Resolution mobileSelectChapter() throws SqlAffectedCountException
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int chapterId = getParamInt("chapterId");
        tsCatalogueModule.selectChapter(chapterId);
        updateUserTsCache(tsCatalogueModule);
        pageModule.gotoPage(1);

        refreshResourceList();

        return new ForwardResolution(CREATE_ASM_1);
    }

    @HandlesEvent("switchresfromtype")
    public Resolution switchResFromType()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int resFromTypeParam = getParamInt("resfromtype", Constants.ResourceFromType.FROM_COMPANY);

        if (resFromTypeParam >= Constants.ResourceFromType.FROM_PERSONAL &&
                resFromTypeParam <= Constants.ResourceFromType.FROM_COMPANY)
        {
            RESOURCE_FROM_TYPE = resFromTypeParam;
        }
        pageModule.gotoPage(1);
        refreshResourceList();

        return new ForwardResolution(CREATE_ASM_1);
    }

    @HandlesEvent("addrestoasm")
    public Resolution addResToAsm()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId", -1);
        if (resId == -1)
        {
            return getStringResolution("error");
        }

        if (!createAsmResNumsExist(resId))
        {
            YjAsmResNum resNum = new YjAsmResNum();
            resNum.setResNum(createAsmResNums.size() + 1);
            resNum.setResId(resId);

            createAsmResNums.add(resNum);
            updateEachResStatus();
        }

        return getStringResolution("ok");
//        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("removeresfromasm")
    public Resolution removeResFromAsm()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId", -1);
        int step = getParamInt("step");
        if (!resIsSelected(resId))
        {
            return getStringResolution("error");
        }

        Iterator<YjAsmResNum> it = createAsmResNums.iterator();
        while (it.hasNext())
        {
            YjAsmResNum resNum = it.next();
            if (resId == resNum.getResId())
            {
                it.remove();
                // 删除一个资源后，对剩下的资源重新设置序号
                refreshCreateAsmResNums();
                break;
            }
        }

        updateEachResStatus();
        refreshCreateAsmRes();
        switch (step)
        {
        case 1:
            return getStringResolution("ok");
//            return new ForwardResolution(RESOURCE_LIST_VIEW);
        case 2:
            return new ForwardResolution(YJ_ASM_RESOURCE_LIST_VIEW);
        default:
            return getStringResolution("error");
        }
    }

    @HandlesEvent("moveup")
    public Resolution moveUp()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId");

        // resId在createAsmResNums中第一个 或者 不在createAsmResNums中的不能上移
        if (createAsmResNums.get(0).getResId() == resId || !resIsSelected(resId))
        {
            return getStringResolution("error");
        }

        for (int i = 0; i < createAsmResNums.size(); i++)
        {
            YjAsmResNum resNum = createAsmResNums.get(i);
            if (resNum.getResId() == resId)
            {
                YjAsmResNum beforeResNum = createAsmResNums.get(i - 1);
                createAsmResNums.set(i - 1, resNum);
                createAsmResNums.set(i, beforeResNum);
                break;
            }
        }

        refreshCreateAsmResNums();
        refreshCreateAsmRes();

        return new ForwardResolution(YJ_ASM_RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("movedown")
    public Resolution moveDown()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int resId = getParamInt("resId");

        // resId在createAsmResNums中最后一个 或者 不在createAsmResNums中的不能下移
        if (createAsmResNums.get(createAsmResNums.size() - 1).getResId() == resId || !resIsSelected(resId))
        {
            return getStringResolution("error");
        }

        for (int i = 0; i < createAsmResNums.size(); i++)
        {
            YjAsmResNum resNum = createAsmResNums.get(i);
            if (resNum.getResId() == resId)
            {
                YjAsmResNum afterResNum = createAsmResNums.get(i + 1);
                createAsmResNums.set(i + 1, resNum);
                createAsmResNums.set(i, afterResNum);
                break;
            }
        }

        refreshCreateAsmResNums();
        refreshCreateAsmRes();

        return new ForwardResolution(YJ_ASM_RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("clearchoosedreslist")
    public Resolution clearChoosedResList()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        clearResList();
        updateEachResStatus();

        return new ForwardResolution(RESOURCE_LIST_VIEW);
    }

    @HandlesEvent("getcreateasmmsgpage")
    public Resolution getCreateAsmMsgPage()
    {
        return new ForwardResolution(CREATE_ASM_MSG);
    }


    @HandlesEvent("gotostep")
    public Resolution gotoStep()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        int step = getParamInt("step");

        switch (step)
        {
        case 2:
            // 生成 createAsmRes
            refreshCreateAsmRes();
            return new ForwardResolution(CREATE_ASM_2);
        case 3:
            // 可不选资源，直接创建资料或任务
            cmService.selectCache(tsClassifyModule, getCurrentUser());
            return new ForwardResolution(CREATE_ASM_3);
        default:
            return new ForwardResolution(CREATE_ASM_1);
        }
    }

    @HandlesEvent("docreateyjassignment")
    public Resolution doCreateYjAssignment()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        // 校验数据合法性
        String asmName = getParam("asmName", "");
        String asmQueContent = getParam("asmQueContent", "");
        String createAsmTokenParam = getParam("createAsmToken");

        if (isCreated == 1 || !createAsmTokenParam.equals(createAsmToken))
        {
            createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2><h3>发布失败！不能重复发布任务。</h3>";
            return getStringResolution("already_created");
        }
        if (Utils.stringEmpty(asmName))
        {
            createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2><h3>发布失败！任务名称不能为空。</h3>";
            return getStringResolution("asmName_empty");
        }
        else if (asmName.length() > Constants.MaxLength.ASSIGNMENT_NAME)
        {
            createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2><h3>发布失败！任务名称长度超出限制。</h3>";
            return getStringResolution("asmName_too_long");
        }
        else if (Utils.stringEmpty(asmQueContent) && createAsmResNums.size() == 0)
        {
            // 不允许任务说明和资源同时为空
            createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2><h3>发布失败！任务说明和任务资源不能同时为空。</h3>";
            return getStringResolution("asmQueContent_and_resList_empty");
        }

        String startTimeStr = getParam("startTime", "");
        String finishTimeStr = getParam("finishTime", "");

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if ("".equals(startTimeStr))
            {
                startTimeStr = Utils.getFullTimeString(Utils.currentSeconds());
            }
            else
            {
                startTimeStr = Utils.getFullTimeString((int) (sdf.parse(startTimeStr).getTime() / 1000L));
            }

            finishTimeStr = Utils.getFullTimeString((int) (sdf.parse(finishTimeStr).getTime() / 1000L));

            processTsClassifyModule();

            int currentSeconds = Utils.currentSeconds();

            // 创建Assignment对象
            Asm asm = createAssignmentObject(currentSeconds, startTimeStr, finishTimeStr);
            log("asm created");

            // 创建Que和AsmQue对象(AsmQue继承自Que)
            AsmQue asmQue = createAsmQue(asmQueContent, asm.getAsmId(), currentSeconds);
            log("asm que created");

            // 插入asm_res记录
            insertAsmRes(asm.getAsmId(), asmQue.getAsmQueId(), createAsmResNums);
            log("asm res inserted");

            // 创建zip包
            createAsmZipFile(asm, asmQue);
            log("zip created");

            // 更新数据库
            deliverAsmToClass(asm);
            log("asm sent to class");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2><h3>发布失败！服务器出现异常。</h3>";
            return getStringResolution("exception");
        }

        clearResList();
        isCreated = 1;
        createAsmSuccessOrFailMsg = "<h2>" + asmName + "</h2> <h5>开始时间：" + startTimeStr +
                "</h5> <h5>结束时间：" + finishTimeStr + "</h5> <h3>发布成功！</h3>";

        return getStringResolution("ok");
    }


    // override
    // --------------------------------------------------------------------------------
    @Override
    @HandlesEvent("getuploadresourcepage")
    public Resolution getUploadResourcePage()
    {
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        cmService.selectCache(tsClassifyModule, getCurrentUser());

        return new ForwardResolution(UPLOAD_RESOURCE);
    }

    @HandlesEvent("getmobileuploadresourcepage")
    public Resolution getMobileUploadResourcePage()
    {
        cmService.selectCache(tsClassifyModule, getCurrentUser());

        return new ForwardResolution(MOBILE_UPLOAD_RES_PAGE);
    }

    @Override
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid() && makeSureYjTea();
    }

    @Override
    protected void refreshResourceList()
    {
        int subjectId = tsCatalogueModule.getCurrentSubject().getSubjectId();
        int bookId = tsCatalogueModule.getCurrentTextbook().getBookId();
        int chapterId = tsCatalogueModule.getCurrentChapter().getChapterId();
//        int sectionId = tsCatalogueModule.getCurrentSection().getSectionId();
        int pageSize = pageModule.getPageSize();
        int limitBegin = pageModule.getLimitBegin();

        log("yj teacher refresh system resource list: " + getCurrentUserId() +
                ", " + subjectId + ", " + bookId + ", " + chapterId + ", "  +
                ", " + pageSize + ", " + limitBegin + ", " + currentResourceTag);

        int resourceCount = 0;
        switch (RESOURCE_FROM_TYPE)
        {
        case Constants.ResourceFromType.FROM_COMPANY:
            resourceCount = cmService.getSystemResourceForTeacherCount(getCurrentUserId(), currentResourceTag,
                    subjectId, bookId, chapterId, 0);
            resourceList = cmService.getSystemResourceForTeacher(getCurrentUserId(), currentResourceTag,
                    subjectId, bookId, chapterId, 0, limitBegin, pageSize);
            break;
        case Constants.ResourceFromType.FROM_SCHOOL:
            resourceCount = cmService.getSchoolResourceForTeacherCount(
                    getCurrentUserId(), getCurrentUser().getSchoolId(), currentResourceTag, 1,
                    subjectId, bookId, chapterId, 0);
            resourceList = cmService.getSchoolResourceForTeacher(
                    getCurrentUserId(), getCurrentUser().getSchoolId(), currentResourceTag, 1,
                    subjectId, bookId, chapterId, 0, limitBegin, pageSize);
            break;

        case Constants.ResourceFromType.FROM_PERSONAL:
            resourceCount = cmService.getTeacherResourceCount(getCurrentUserId(), currentResourceTag,
                    subjectId, bookId, chapterId, 0);
            resourceList = cmService.getTeacherResource(getCurrentUserId(), currentResourceTag,
                    subjectId, bookId, chapterId, 0, limitBegin, pageSize);
            break;
        }

        cmService.setResUseCount(resourceList);

        updateEachResStatus();

        pageModule.changeItemsCount(resourceCount);
    }

    // private
    // --------------------------------------------------------------------------------
    private Asm createAssignmentObject(int currentSeconds, String startTimeStr, String finishTimeStr)
            throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int startTime = Utils.stringEmpty(startTimeStr) ? 0 : (int) (sdf.parse(startTimeStr).getTime() / 1000L);
        int finishTime = Utils.stringEmpty(finishTimeStr) ? 0 : (int) (sdf.parse(finishTimeStr).getTime() / 1000L);

        Asm asm = new Asm();

        asm.setAsmName(getParam("asmName"));
        asm.setCreatorId(getCurrentUserId());
        asm.setAsmType(getParamInt("asmType"));
        asm.setAsmScore(DEFAULT_YJ_ASM_SCORE);
        asm.setCreateTime(currentSeconds);
        asm.setStartTime(startTime);
        asm.setFinishTime(finishTime);
        // asm.setLimitTime(getParamInt("limitTime", 0));
        asm.setIsMultiMedia(1);

        asm.setSubjectId(tsClassifyModule.getCurrentTextbook().getSubjectId());
        asm.setBookId(tsClassifyModule.getCurrentTextbook().getBookId());
        asm.setChapterId(tsClassifyModule.getCurrentChapter().getChapterId());
        // asm.setSectionId(tsClassifyModule.getCurrentSection().getSectionId());

        cmService.insertAsm(asm); // 生成asmId
        return asm;
    }

    private AsmQue createAsmQue(String asmQueContent, int asmId, int currentSeconds) throws SqlAffectedCountException
    {
        User currentUser = getCurrentUser();

        AsmQue asmQue = new AsmQue();

        // asmQue
        asmQue.setAsmId(asmId);
        asmQue.setQueNum(1);
        asmQue.setQueScore(DEFAULT_YJ_ASM_SCORE);

        // que
        asmQue.setContentHtml(asmQueContent);
        asmQue.setCreatorId(currentUser.getUserId());
        asmQue.setSchoolId(currentUser.getSchoolId());
        asmQue.setCreateTime(currentSeconds);
        asmQue.setSubjectId(tsClassifyModule.getCurrentTextbook().getSubjectId());
        asmQue.setBookId(tsClassifyModule.getCurrentTextbook().getBookId());
        asmQue.setChapterId(tsClassifyModule.getCurrentChapter().getChapterId());
//        asmQue.setSectionId(tsClassifyModule.getCurrentSection().getSectionId());

        cmService.insertQuestionBank(asmQue);
        cmService.insertAsmQue(asmQue);

        return asmQue;
    }

    private void deliverAsmToClass(Asm asm) throws Exception
    {
        int userId = getCurrentUserId();

        // 获取要发布班级的id拼接字符串
        String classIdsStr = getParam("classIdsStr", "");
        if (!classIdsStr.equals(""))
        {
            // 除去最后一个多余的","
            classIdsStr = classIdsStr.substring(0, classIdsStr.length() - 1);

            String[] eachClassIdStrs = classIdsStr.split(",");
            List<String> idList = Arrays.asList(eachClassIdStrs);
            for (Cls cls : classesOfTeacher)
            {
                if (idList.contains(cls.getClsId() + ""))
                {
                    cmService.sendAssignmentToClass(userId, asm.getAsmId(), cls.getClsId());
                }
            }
        }
    }

    private void insertAsmRes(int asmId, int asmQueId, List<YjAsmResNum> createAsmResNums2)
    {
        for (YjAsmResNum resNum : createAsmResNums2)
        {
            cmService.insertAsmRes(asmId, asmQueId, resNum.getResId(), resNum.getResNum());
        }
    }

    private void createAsmZipFile(Asm asm, AsmQue asmQue) throws Exception
    {
        writeQuestionFile(asmQue);

        writeResourceFile(asm.getAsmId(), createAsmRes);

        String basePath = Constants.PATH_FILE + Utils.groupUserId(getCurrentUserId()) + File.separator;
        String dirPath = basePath + asm.getAsmId() + File.separator;

        String json = createJsonObject(asm, asmQue, createAsmRes).toString();

        Utils.writeFileAllText(dirPath + (asm.getAsmId() + ".json"), json);

        Utils.zipFile(dirPath, basePath + (asm.getAsmId() + ".zip"));
    }

    private void writeQuestionFile(AsmQue asmQue) throws Exception
    {
        String queDirPath = Constants.PATH_FILE + Utils.groupUserId(getCurrentUserId()) + File.separator
                + asmQue.getAsmId() + File.separator + asmQue.getAsmQueId() + File.separator;
        Utils.makeSureDirExists(queDirPath);

        String contentHtml = asmQue.getContentHtml() == null ? "" : asmQue.getContentHtml();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head>")
                .append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />")
                .append("<style>.que_content{margin: 10px;}</style></head>")
                .append("<body><div class='que_content'>")
                .append(contentHtml)
                .append("</div></body></html>");

        String htmlToWrite = stringBuilder.toString();

        Utils.writeFileAllText(queDirPath + asmQue.getAsmQueId() + ".html", htmlToWrite);
    }

    private void writeResourceFile(int asmId, List<Res> createAsmRes2) throws Exception
    {
        String baseResDir = Constants.PATH_FILE + Utils.groupUserId(getCurrentUserId()) + File.separator
                + asmId + File.separator + YJ_ASM_RESOURCE_FOLDER_NAME + File.separator;
        Utils.makeSureDirExists(baseResDir);

        for (Res res : createAsmRes2)
        {
            int resId = res.getResId();

            String resourceDir = baseResDir + resId + File.separator;
            Utils.makeSureDirExists(resourceDir);

            // 针对每一个resId获取到对应的Resource File, 幼教老师这边不存在板书，一个资源只能有一个文件
            List<ResFile> resFileList = cmService.getResourceFileByResourceId(resId);

            ResFile resFile = resFileList.get(0);
            String resFilePath = resFile.getFilePath();
            Utils.makeSureFileExist(resFilePath);

            String newResFileName = Utils.getMd5(resFilePath) + "-" + resFile.getFileSize();

            Utils.copyFile(resFilePath, resourceDir + newResFileName);

            res.setResFilePath(YJ_ASM_RESOURCE_FOLDER_NAME + File.separator + resId + File.separator
                    + newResFileName);
            res.setResFileSuffix(resFile.getFileSuffix());
        }
    }

    private JSONObject createJsonObject(Asm asm, AsmQue asmQue, List<Res> resList) throws JSONException
    {
        JSONObject asmJO = new JSONObject();

        asmJO.put("a" + "ssignmentId", String.valueOf(asm.getAsmId()));

        JSONArray asmQueListJA = new JSONArray();

        // 幼教的作业只有一题
        JSONObject asmQueJO = new JSONObject();
        // question表里的数据
        asmQueJO.put("q" + "uestionId", String.valueOf(asmQue.getAsmQueId()));
        asmQueJO.put("q" + "uestionType", "0");
        asmQueJO.put("q" + "uestionNum", String.valueOf(asmQue.getQueNum()));
        asmQueJO.put("s" + "core", String.valueOf(asmQue.getQueScore()));
        asmQueJO.put("s" + "ubScore", String.valueOf(asmQue.getSubScore()));

        asmQueJO.put("o" + "ptionsCount", "0"); // 新
        asmQueJO.put("s" + "ubQueCount", "0"); // 兼容
        asmQueJO.put("a" + "nswer", "0");
        asmQueListJA.put(asmQueJO);
        asmJO.put("q" + "uestionList", asmQueListJA);

        JSONArray asmResListJA = new JSONArray();
        int index = 1;
        for (Res asmRes : resList)
        {
            JSONObject asmResJO = new JSONObject();

            asmResJO.put("r" + "esourceId", asmRes.getResId());
            asmResJO.put("r" + "esourceName", asmRes.getResName());
            asmResJO.put("r" + "esourceNum", String.valueOf(index++));
            asmResJO.put("q" + "uestionId", String.valueOf(asmQue.getAsmQueId()));
            asmResJO.put("f" + "ilePath", asmRes.getResFilePath());
            asmResJO.put("f" + "ileSuffix", asmRes.getResFileSuffix());

            asmResListJA.put(asmResJO);
        }
        asmJO.put("r" + "esourceList", asmResListJA);

        return asmJO;
    }

    private void clearResList()
    {
        createAsmResNums.clear();
    }

    private void updateEachResStatus()
    {
        for (Res resource : resourceList)
        {
            resource.setIsAddedToAsm(resIsSelected(resource.getResId()));
        }
    }

    private boolean resIsSelected(int resId)
    {
        for (YjAsmResNum resNum : createAsmResNums)
        {
            if (resId == resNum.getResId())
            {
                return true;
            }
        }
        return false;
    }

    private void refreshCreateAsmResNums()
    {
        for (int i = 0; i < createAsmResNums.size(); i++)
        {
            createAsmResNums.get(i).setResNum(i + 1);
        }
    }

    private void refreshCreateAsmRes()
    {
        createAsmRes.clear();

        for (YjAsmResNum resNum : createAsmResNums)
        {
            Res res = cmService.getResourceInfo(resNum.getResId());
            createAsmRes.add(res);
        }
    }

    // 创建任务第三部取消分类选择，所以在创建任务前要确保tsCatalogueModule的bookId不为空
    private void processTsClassifyModule()
    {
        if (tsClassifyModule.getBookId() != 0)
        {
            return;
        }
        else
        {
            if (tsCatalogueModule.getCurrentTextbook().getBookId() != 0)
            {
                tsClassifyModule.selectTextbook(tsCatalogueModule.getCurrentTextbook().getBookId());
                tsClassifyModule.selectChapter(tsCatalogueModule.getCurrentChapter().getChapterId());
                return;
            }

            for (Res res : createAsmRes)
            {
                if (res.getSubjectId() != 0 && res.getBookId() != 0)
                {
                    tsClassifyModule.selectTextbook(res.getBookId());
                    tsClassifyModule.selectChapter(res.getChapterId());
                    return;
                }
            }

            // 如果从加入任务的资源中都不能找出一个bookId不为0的，那就把tsClassifyModule中bookList的第一个设置为当前bookId
            if (tsClassifyModule.getBookId() == 0)
            {
                // 创建任务的时候不一定要求要设置课本
                if (tsCatalogueModule.getAllTextbookList() == null || tsCatalogueModule.getAllTextbookList().size() == 0
                        || tsCatalogueModule.getAllTextbookList().get(0) == null)
                {
                    tsClassifyModule.selectTextbook(0);
                }
                else
                {
                    tsClassifyModule.selectTextbook(tsCatalogueModule.getAllTextbookList().get(0).getBookId());
                }
            }
        }
    }

    // 添加资源时检查该资源是否已经存在， 避免重复添加
    private boolean createAsmResNumsExist(int resId)
    {
        for (YjAsmResNum yjAsmResNum : createAsmResNums)
        {
            if (yjAsmResNum.getResId() == resId)
            {
                return true;
            }
        }
        return false;
    }

    // 设置防止重复创建任务的标记值为"可以创建"
    private void resetCreatableLabel()
    {
        isCreated = 0;
        createAsmToken = Utils.getRandomString(32);
    }

}
