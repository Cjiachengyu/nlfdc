package com.kdl.nlfdc.action;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;

import com.kdl.nlfdc.action.component.MenuSelector;
import com.kdl.nlfdc.action.component.PageModule;
import com.kdl.nlfdc.action.component.TextbookSelectorBase;
import com.kdl.nlfdc.action.component.TextbookSelectorCatalogue;
import com.kdl.nlfdc.action.component.TextbookSelectorClassify;
import com.kdl.nlfdc.action.web.AdminManage;
import com.kdl.nlfdc.action.web.Index;
import com.kdl.nlfdc.action.web.Login;
import com.kdl.nlfdc.action.web.YjTeaAsm;
import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.exception.AccountInvalidException;
import com.kdl.nlfdc.exception.SqlAffectedCountException;
import com.kdl.nlfdc.service.CmService;

@SessionScope
public abstract class AbstractActionBean implements ActionBean, Serializable
{
    private static final long serialVersionUID = -1767714708233127983L;
    protected static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    protected static final String CLASSIFY_SCHEMA_VIEW = "/WEB-INF/jsp/component/SchemaClassify.jsp";
    protected static String REDIRECT_PAGE = "/WEB-INF/jsp/component/Redirect.jsp";

    
    protected MenuSelector menuSelector;
    
    
    public MenuSelector getMenuSelector()
    {
        return menuSelector;
    }

    // member
    // --------------------------------------------------------------------------------
    @SpringBean
    public transient CmService cmService;

    protected transient ActionBeanContext context;

    public ActionBeanContext getContext()
    {
        return context;
    }

    public void setContext(ActionBeanContext context)
    {
        this.context = context;
    }

    // 系统参数
    // --------------------------------------------------------------------------------
    public int getJsCssImgVersion()
    {
        return Constants.JS_CSS_IMG_VERSION;
    }


    // HTTP管理
    // --------------------------------------------------------------------------------
    public HttpServletRequest getCurrentRequest()
    {
        return context.getRequest();
    }

    public HttpSession getCurrentSession()
    {
        return context.getRequest().getSession();
    }

    public String getCurrentLang()
    {
        Object langObj = getCurrentSession().getAttribute("lang");

        if (langObj instanceof String)
        {
            String lang = (String) langObj;

            if (!Utils.stringEmpty(lang) && lang.trim().equals("en"))
            {
                return "en";
            }
        }

        return "zh";
    }

    public String getCurrentUserIp()
    {
        HttpServletRequest request = getCurrentRequest();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null)
        {
            ip = request.getRemoteAddr();
        }
        else
        {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip;
    }

    // session管理
    // --------------------------------------------------------------------------------
    private boolean sessionValid = false;

    protected boolean commonSessionIsValid()
    {


        // return sessionValid && getCurrentUser() != null;
        // stripes在session判断的时候有问题，导致sessionValid不准确，暂时先不用这种方式
        return getCurrentUser() != null;
    }

    /**
     * call in default handler
     */
    protected void validateSession()
    {
        // to-be-deleted
        sessionValid = true;
    }

    /**
     * to be overridden
     */
    protected boolean sessionIsValid()
    {
        return commonSessionIsValid();
    }


    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    // 重定向操作
    // --------------------------------------------------------------------------------
    private String redirectUrl;

    public Resolution redirectToUrl(String url)
    {
        this.redirectUrl = url;
        return new ForwardResolution(REDIRECT_PAGE);
    }

    // 用户管理
    // --------------------------------------------------------------------------------
    public User getCurrentLoginUser()
    {
        return (User) getCurrentSession().getAttribute("loginUser");
    }

    public User getCurrentRealUser()
    {
        return (User) getCurrentSession().getAttribute("realUser");
    }

    public User getCurrentUser()
    {
        return (User) getCurrentSession().getAttribute("user");
    }

    public Admin getCurrentAdmin()
    {
        return (Admin) getCurrentSession().getAttribute("admin");
    }
    
    public int getCurrentAdminId()
    {
        if (getCurrentAdmin() != null)
        {
            return getCurrentAdmin().getAdminId();
        }
        else
        {
            return 0;
        }
    }
    
    public int getCurrentUserId()
    {
        if (getCurrentUser() != null)
        {
            return getCurrentUser().getUserId();
        }
        else
        {
            return 0;
        }
    }

    public ArrayDeque<User> getUserStack()
    {
        @SuppressWarnings("unchecked")
        ArrayDeque<User> userStack = (ArrayDeque<User>) getCurrentSession().getAttribute("userStack");
        return userStack;
    }

    public void setGoBackUser(ArrayDeque<User> userStack)
    {
        if (userStack.size() == 0)
        {
            setSessionAttr("goBackUser", null);
        }
        else
        {
            setSessionAttr("goBackUser", userStack.getFirst());
        }
    }


    public void loginSuccessInitSession(Admin admin)
            throws AccountInvalidException
    {

        getCurrentSession().invalidate();
        getCurrentSession().setAttribute("lang", "zh"); // 暂时全部设为中文

        setSessionAttr("admin", admin);
    }

    public boolean makeSureYjTea()
    {
        return makeSureUserRole(Constants.UserRole.YJ_TEACHER);
    }
 
    public boolean makeSureYjEditor()
    {
        return makeSureUserRole(Constants.UserRole.YJ_EDITOR);
    }

    public boolean makeSureYjMaster()
    {
        return makeSureUserRole(Constants.UserRole.YJ_MASTER);
    }

    public boolean makeSureYjAdmin()
    {
        return makeSureUserRole(Constants.UserRole.YJ_ADMIN);
    }
    
    public boolean makeSureSuperAdmin()
    {
        return makeSureUserRole(Constants.UserRole.SUPER_ADMIN);
    }

    public boolean makeSureCommonAdmin()
    {
        return makeSureUserRole(Constants.UserRole.COMMON_ADMIN);
    }
    
    @HandlesEvent("gotousermainpage")
    public Resolution gotoUserMainPage()
    {
        logRequest();

        if (getCurrentUser() == null)
        {
            return getYjLogoutResolution();
        }

        return getUserMainPage(getCurrentRealUser(), true);
    }
    
    @HandlesEvent("gotoadminmainpage")
    public Resolution gotoAdminMainPage()
    {
        logRequest();

        if (getCurrentAdmin() == null)
        {
            return getYjLogoutResolution();
        }

        return new RedirectResolution(AdminManage.class);
    }

    // 获取参数
    // --------------------------------------------------------------------------------
    public String getParam(String key, String defaultValue)
    {
        try
        {
            String ret = getParam(key);
            if (ret.equals(""))
            {
                return defaultValue;
            }
            else
            {
                return ret;
            }
        }
        catch (Exception e)
        {
            log("maybe get an invalid key: " + String.valueOf(key));
            return defaultValue;
        }
    }

    public String getParam(String key)
    {
        String val = doGetParam(key);
        if (Utils.stringEmpty(val))
        {
            val = doGetParam(key.toLowerCase());
        }

        return val;
    }

    public int getParamInt(String key, int defaultValue)
    {
        try
        {
            return getParamInt(key);
        }
        catch (Exception e)
        {
            log("invalid key: " + (key == null ? "null" : key));
            return defaultValue;
        }
    }

    public int getParamInt(String key)
    {
        return Integer.parseInt(getParam(key));
    }

    public long getParamLong(String key, long defaultValue)
    {
        try
        {
            return getParamLong(key);
        }
        catch (Exception e)
        {
            log("invalid key: " + (key == null ? "null" : key));
            return defaultValue;
        }
    }

    public long getParamLong(String key)
    {
        return Long.parseLong(getParam(key));
    }

    public float getParamFloat(String key, float defaultValue)
    {
        try
        {
            return getParamFloat(key);
        }
        catch (Exception e)
        {
            log("invalid key: " + (key == null ? "null" : key));
            return defaultValue;
        }
    }

    public float getParamFloat(String key)
    {
        return Float.parseFloat(getParam(key));
    }

    public double getParamDouble(String key, double defaultValue)
    {
        try
        {
            return getParamDouble(key);
        }
        catch (Exception e)
        {
            log("invalid key: " + (key == null ? "null" : key));
            return defaultValue;
        }
    }

    public double getParamDouble(String key)
    {
        return Double.parseDouble(getParam(key));
    }

    public String getSessionString(String key)
    {
        Object ret = getCurrentSession().getAttribute(key);

        try
        {
            return (String) ret;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public void setSessionAttr(String key, Object value)
    {
        getCurrentSession().setAttribute(key, value);
    }


    // 记录日志
    // --------------------------------------------------------------------------------
    public void logRequest()
    {
        logRequest("");
    }

    public void logRequest(String tag)
    {
        try
        {
            String userInfo = getUserInfoString();
            String params = Utils.getRequestParamsString(getCurrentRequest());
            String url = getCurrentRequest().getRequestURI();
            if (url.contains("/"))
            {
                url = url.substring(url.lastIndexOf("/") + 1);
            }

            log(new StringBuffer(tag + userInfo).append(" => ").append(url).append(", ").append(params)
                    .append(", ===LOG_REQUEST_LINE==="));
        }
        catch (Exception e)
        {
        }
    }

    public void log(Object logText, String logFilePath)
    {
        synchronized (AbstractActionBean.class)
        {
            FileWriter filerWriter = null;
            BufferedWriter bufWriter = null;
            try
            {
                String toLog = String.valueOf(logText).replaceAll("[\r\n]", " ");

                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append(logDateTimeFormat.format(new Date()));
                logBuffer.append(" ");
                logBuffer.append(new DecimalFormat("000000").format(Thread.currentThread().getId()));
                logBuffer.append(" ");
                logBuffer.append(getCurrentUserIp());
                logBuffer.append(" -> ");
                logBuffer.append(toLog);
                logBuffer.append("\n");

                Utils.makeSureFileExist(logFilePath);

                File file = new File(logFilePath);
                filerWriter = new FileWriter(file, true);
                bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write(logBuffer.toString());
            }
            catch (Exception e)
            {
            }
            finally
            {
                Utils.safeClose(bufWriter);
                Utils.safeClose(filerWriter);
            }
        }
    }

    public void log(Object logText)
    {
        log(logText, getLogFilePath());
    }

    protected String getLogFilePath()
    {
        String logFileName = Utils.getFullDateString(Utils.currentSeconds()) + ".web.log";
        String logFilePath = Constants.PATH_FILE + "log/" + logFileName;
        return logFilePath;
    }


    // 获取常见返回值
    // --------------------------------------------------------------------------------
    protected Resolution getStringResolution(String text)
    {
        return new StreamingResolution("text/html;charset=UTF-8", new StringReader(String.valueOf(text)));
    }

    protected Resolution getJsonStringResolution(String text)
    {
        return new StreamingResolution("text/json;charset=UTF-8", new StringReader(String.valueOf(text)));
    }

    protected Resolution getStringAtLeastTextbookResolution()
    {
        return new StreamingResolution("text/html;charset=UTF-8", new StringReader("atLeastTextbook"));
    }

    protected Resolution getStringTimeoutResolution()
    {
        return new StreamingResolution("text/html;charset=UTF-8", new StringReader("timeOut"));
    }

    protected Resolution getJsonTimeoutResolution()
    {
        JSONObject jsonObj = new JSONObject();
        try
        {
            jsonObj.put("result", "timeOut");
        }
        catch (JSONException e)
        {
        }
        return getJsonStringResolution(jsonObj.toString());
    }

    protected Resolution getIndexResolution()
    {
        return new RedirectResolution(Index.class);
    }

    protected Resolution getYjLogoutResolution()
    {
        return new RedirectResolution(Login.class, "yjsignout");
    }

    protected Resolution getUserMainPage(User user, boolean checkSimplePwd)
    {
//        if (checkSimplePwd)
//        {
//            if (user.getPassword().length() < 6)
//            {
//                log("should change pwd");
//                return new RedirectResolution(UserChangePwd.class);
//            }
//        }

        Resolution ret = getYjLogoutResolution();
        switch (user.getUserRole())
        {
        case Constants.UserRole.SUPER_ADMIN:
        case Constants.UserRole.COMMON_ADMIN:
            ret = new RedirectResolution(YjTeaAsm.class);
            break;
        }
        return ret;
    }
    
    // download file
    protected Resolution getFileResolution(final String filePath, final String downloadFileName)
    {
        return new StreamingResolution("")
        {
            @Override
            protected void stream(HttpServletResponse response) throws Exception
            {
                log("downloading file: " + filePath);
                if (getCurrentRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
                {
                    response.addHeader("Content-Disposition",
                            "attachment; filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
                }
                else
                {
                    response.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1"));
                }

                File file = new File(filePath);
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");

                OutputStream toStream = null;
                FileInputStream fromStream = null;
                try
                {
                    toStream = new BufferedOutputStream(response.getOutputStream());
                    fromStream = new FileInputStream(file);

                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;

                    while ((length = fromStream.read(buffer)) != -1)
                    {
                        toStream.write(buffer, 0, length);
                    }
                    toStream.flush();
                }
                catch (Exception e)
                {
                    log("WARN: download file: " + filePath + "failed, error: " + e.getMessage());
                }
                finally
                {
                    Utils.safeClose(toStream);
                    Utils.safeClose(fromStream);
                }
            }
        };
    }

    protected Resolution getExcelResolution(final InputStream is, final String downloadFileName)
    {
        return new StreamingResolution("")
        {
            @Override
            protected void stream(HttpServletResponse response) throws Exception
            {
                if (getCurrentRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
                {
                    response.addHeader("Content-Disposition",
                            "attachment; filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
                }
                else
                {
                    response.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1"));
                }

                response.addHeader("Content-Length", "" + is.available());
                response.setContentType("application/octet-stream");

                OutputStream toStream = null;
                try
                {
                    toStream = new BufferedOutputStream(response.getOutputStream());

                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;

                    while ((length = is.read(buffer)) != -1)
                    {
                        toStream.write(buffer, 0, length);
                    }
                    toStream.flush();
                }
                catch (Exception e)
                {
                    log("WARN: download file: " + downloadFileName + "failed, error: " + e.getMessage());
                }
                finally
                {
                    Utils.safeClose(toStream);
                    Utils.safeClose(is);
                }
            }
        };
    }

    // 分页
    // --------------------------------------------------------------------------------
    protected PageModule pageModule;

    public PageModule getPageModule()
    {
        return pageModule;
    }


    // 课本选择
    // --------------------------------------------------------------------------------
    protected TextbookSelectorBase tsCatalogueModule;

    public TextbookSelectorBase getTsCatalogueModule()
    {
        return tsCatalogueModule;
    }

    public TextbookSelectorCatalogue initTsCatalogue()
    {
        User user = getCurrentUser();
        String lang = getCurrentLang();

        TextbookSelectorCatalogue tsCatalogue = new TextbookSelectorCatalogue(cmService, user, lang);

        if (user.getUserRole() != Constants.UserRole.YJ_MASTER && user.getUserRole() != Constants.UserRole.YJ_ADMIN)
        {
            initSchemaModuleByCache(user, tsCatalogue);
        }

        return tsCatalogue;
    }
    
    public MenuSelector initMenuSelector()
    {
        Admin admin = getCurrentAdmin();
        MenuSelector menuSelector = new MenuSelector(cmService, admin);
        
        return menuSelector;
    }
    
    public void updateUserTsCache(TextbookSelectorBase textbookSelector) throws SqlAffectedCountException
    {
        User user = getCurrentUser();
        // TODO 异常处理，不能直接抛出去
        updateUserTsCache(textbookSelector, user);
    }

    public void updateUserTsCache(TextbookSelectorBase textbookSelector, User user)
    {
        if (user == null)
        {
            return;
        }

        user.setCacheSubjectId(textbookSelector.getCurrentSubject().getSubjectId());
        user.setCacheBookId(textbookSelector.getCurrentTextbook().getBookId());
        user.setCacheChapterId(textbookSelector.getCurrentChapter().getChapterId());
//        user.setCacheSectionId(textbookSelector.getCurrentSection().getSectionId());
        cmService.updateSchemaCache(user);
    }


    // 重新分类
    // --------------------------------------------------------------------------------
    protected String classifySchemaItemId;
    protected TextbookSelectorClassify tsClassifyModule;

    public TextbookSelectorClassify getTsClassifyModule()
    {
        return tsClassifyModule;
    }

    public TextbookSelectorClassify initTsClassifyModule()
    {
        return new TextbookSelectorClassify(cmService, getCurrentUser(), getCurrentLang());
    }

    @HandlesEvent("classifyselectsubject")
    public Resolution classifySelectSubject() throws JSONException
    {
        //System.out.println("----------call classifyselectsubject");
        logRequest();

        if (tsClassifyModule == null)
        {
            return getJsonTimeoutResolution();
        }

        tsClassifyModule.selectSubject(getParamInt("subjectId"));
//        System.out.println("----------subjectId: " + getParamInt("subjectId"));

        JSONObject ret = new JSONObject();
        ret.put("textArray", tsClassifyModule.getTextbookJsonArray());
        ret.put("chapterArray", tsClassifyModule.getChapterJsonArray());
        //ret.put("sectionArray", tsClassifyModule.getSectionJsonArray());
        
        return getJsonStringResolution(ret.toString());
    }

    @HandlesEvent("classifyselecttextbook")
    public Resolution classifySelectTextbook() throws JSONException
    {
        logRequest();

        if (tsClassifyModule == null)
        {
            return getJsonTimeoutResolution();
        }

        tsClassifyModule.selectTextbook(getParamInt("bookId"));

        JSONObject ret = new JSONObject();
        ret.put("chapterArray", tsClassifyModule.getChapterJsonArray());
        //ret.put("sectionArray", tsClassifyModule.getSectionJsonArray());
        return getJsonStringResolution(ret.toString());
    }

    @HandlesEvent("classifyselectchapter")
    public Resolution classifySelectChapter() throws JSONException
    {
        logRequest();

        if (tsClassifyModule == null)
        {
            return getJsonTimeoutResolution();
        }

        tsClassifyModule.selectChapter(getParamInt("chapterId"));

        JSONObject ret = new JSONObject();
        //ret.put("sectionArray", tsClassifyModule.getSectionJsonArray());
        return getJsonStringResolution(ret.toString());
    }

    @HandlesEvent("classifyselectsection")
    public Resolution classifySelectSection() throws JSONException
    {
        logRequest();

        if (tsClassifyModule == null)
        {
            return getStringTimeoutResolution();
        }

//        tsClassifyModule.selectSection(getParamInt("sectionId"));

        return getStringResolution("ok");
    }

    // 子类需要重写这个方法
    @HandlesEvent("gettextbookselectorclassifyview")
    public Resolution getTextbookSelectorClassifyView()
    {
        return getStringTimeoutResolution();
    }

    // 子类需要重写这个方法
    @HandlesEvent("dotextbookclassify")
    public Resolution doTextbookClassify()
    {
        return getStringTimeoutResolution();
    }


    // private
    // --------------------------------------------------------------------------------
    private String getUserInfoString()
    {
        String userInfo = "null user";
        User user = getCurrentUser();
        if (user != null)
        {
            userInfo = String.valueOf(user.getUserName()) + "-" + user.getUserId();
        }
        return userInfo;
    }

    private void initSchemaModuleByCache(User user, TextbookSelectorBase tbSelector)
    {
        tbSelector.selectSubject(user.getCacheSubjectId());
        tbSelector.selectTextbook(user.getCacheBookId());
        tbSelector.selectChapter(user.getCacheChapterId());
//        tbSelector.selectSection(user.getCacheSectionId());
    }

    private static final SimpleDateFormat logDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private boolean makeSureUserRole(int adminRole)
    {
        if (getCurrentAdmin() == null)
        {
            log("current user is null");
            return false;
        }

        if (getCurrentAdmin().getAdminRole() != adminRole)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private String doGetParam(String key)
    {
        String val = context.getRequest().getParameter(key);
        if (Utils.stringNotEmpty(val))
        {
            return val.trim();
        }

        val = context.getRequest().getHeader(key);
        if (Utils.stringNotEmpty(val))
        {
            String encryptVersion = context.getRequest().getHeader("encryptVersion");
            if (Utils.stringEmpty(encryptVersion))
            {
                return val;
            }
            else
            {
                switch (Integer.parseInt(encryptVersion))
                {
                case 1:
                    return Utils.decryptStringV1(val);
                case 2:
                    // next version
                    break;
                }
            }
        }

        return "";
    }

    public boolean notYjWebLoginAbleUser(User user)
    {
        if (user == null)
        {
            return true;
        }

        int userRole = user.getUserRole();

        return !(userRole == Constants.UserRole.YJ_TEACHER
                || userRole == Constants.UserRole.YJ_EDITOR
                || userRole == Constants.UserRole.YJ_ADMIN
                || userRole == Constants.UserRole.YJ_MASTER);
    }
    public boolean isYjAdmin(User user)
    {
        if (user == null)
        {
            return false;
        }

        return user.getUserRole() == Constants.UserRole.YJ_ADMIN;
    }

    public boolean isYjMaster(User user)
    {
        if (user == null)
        {
            return false;
        }

        return user.getUserRole() == Constants.UserRole.YJ_MASTER;
    }
}
