package com.kdl.nlfdc.action;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Constants
{
    // 每个系统相同的常数
    // --------------------------------------------------------------------------------
    public static final int JS_CSS_IMG_VERSION = Utils.currentSeconds();

    public static final int IS_DELETED = 1;
    public static final int NOT_DELETED = 0;

    // 每个系统不同的常数
    // --------------------------------------------------------------------------------
    public static String PATH_FILE = "/var/webapp/file.war/nlfdc/";
    public static String URL_DOMAIN_IP = "..";
    public static String URL_DOMAIN_NAME = "..";

    // 其它常数
    // --------------------------------------------------------------------------------
    public static final String URL_FILE = URL_DOMAIN_IP + "/file/nlfdc/";

    public static SimpleDateFormat SDF_FULL_TIME_WITH_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SDF_FULL_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat SDF_SIMPLE_TIME = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat SDF_FULL_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat SDF_SIMPLE_DATE = new SimpleDateFormat("MM月dd日");    // 添加“月日”让人一看就知道是日期

    public interface Email
    {
        public static final String MAIL_HOST = "smtp.exmail.qq.com";
        public static final String MAIL_ACCOUNT = "reg@eclassmate.cn";
        public static final String MAIL_PASSWORD = "newpass123";
        public static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[_-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    }

    public interface MaxLength
    {
        public static final int PASSWORD = 32;
        public static final int LOGIN_NAME = 32;
        public static final int USER_NAME = 32;
        public static final int EMAIL = 32;

        public static final int CLASS_NAME = 16;
        public static final int BASE_CLASSNAME = 10;

        public static final int SCHOOL_NAME = 30;
        public static final int SCHOOL_MOTTO = 32;

        public static final int SCHEMA = 24;
        public static final int TEXTBOOK_NAME = 64;
        public static final int MAX_LENGTH_OF_CHAPTER_SECTION_NAME = 128;
        public static final int PUBLISHER = 64;

        public static final int ASSIGNMENT_NAME = 48;

        public static final int RES_NAME = 80;
        
        public static final int NOTIFICATION_TITLE = 50;
    }

    public interface Category
    {
        public static final int ALL = 0;
        public static final int PRIMARY_SCHOOL = 1;
        public static final int MIDDLE_SCHOOL = 2;
        public static final int HIGH_SCHOOL = 3;
        public static final int COLLEGE = 4;
        public static final int OTHER = 5;
        public static final int YOU_JIAO = 6;

        public static final String[] NAMES = { "全阶段", "小学", "初中", "高中", "大学", "其它", "幼教", };
    }

    public interface Gender
    {
        public static final String MALE = "0";
        public static final String FEMALE = "1";
    }

    public interface User
    {
        public static final String DEFAULT_PASSWORD = "123456";
    }

    public interface UserRole
    {
        public static final int SUPER_ADMIN = 0;
        public static final int COMMON_ADMIN = 1;
        
        public static final int YJ_PARENT = 8;
        public static final int YJ_TEACHER = 9;
        public static final int YJ_MASTER = 10;
        public static final int YJ_EDITOR = 11;
        public static final int YJ_ADMIN = 12;

        String LOGIN_ID_PATTERN = "^[0-9]+[stpmraez]$";
        String CELLPHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    }

    public interface MainMenuOperation
    {
        public static final String NOTIFICATION_MANAGE = "01";
        public static final String ADMIN_MANAGE = "02";
        

        public static final String YJ_PARENT = "81";

        public static final String YJ_TEA_ASSIGNMENT = "91";
        public static final String YJ_TEA_NOTIFICATION = "92";
        public static final String YJ_TEA_RESOURCE = "93";

        public static final String YJ_MASTER_CLASS_INFO = "101";
        public static final String YJ_MASTER_TEACHER_INFO = "102";
        public static final String YJ_MASTER_SCHOOL_RES = "103";
        public static final String YJ_MASTER_NOTIFICATION = "104";
        public static final String YJ_MASTER_COMMON_MANAGE = "105";

        public static final String YJ_EDITOR_RESOURCE_MANAGE = "111";
        public static final String YJ_EDITOR_TEXTBOOK_MANAGE = "112";

        public static final String YJ_ADMIN_COMMON_MANAGE = "121";
        public static final String YJ_ADMIN_SCHOOL = "122";
        public static final String YJ_ADMIN_SCHEMA = "123";
        public static final String YJ_ADMIN_TEXTBOOK_MANAGE = "124";
        public static final String YJ_ADMIN_RES_MANAGE = "125";
    }

    public interface QueEditOperationType
    {
        public static final int CREATE = 1;
        public static final int EDIT = 2;
    }

    public interface AnswerItemType
    {
        public static final int TEXT = 1;
        public static final int IMAGE = 2;
        public static final int AUDIO = 3;
        public static final int VIDEO = 4;
    }

    public interface AsmType
    {
        int ASSIGNMENT = 1;
        int EXAM = 2;
        int EVALUATE = 3;

        int YJ_WITHOUT_CORRECT = 4; // 资料（无需批改）--> 改为“亲子阅读”
        int YJ_ALONE_FINISH = 5;    // 独立完成
        int YJ_PARENT_ASSIST = 6;   // 家长协助

        String[] asmTypeStringArray = { "未知", "作业", "考试", "评测", "亲子阅读", "独立完成", "家长协助" };
    }

    public interface AsmStatus
    {
        public static final int NEW_ASSIGNED = 0;       // 新发布
        public static final int NEW_RECEIVED = 1;       // 新接收
        public static final int ANSWERING = 2;          // 答题中
        public static final int ANSWERED = 3;           // 做完(仅客户端)
        public static final int ANSWER_SUBMITTED = 4;   // 已提交
        public static final int ANSWER_RECEIVED = 5;    // 已收回
        public static final int CORRECTING = 6;         // 批改中(仅客户端)
        public static final int CORRECTED = 7;          // 批改完成(仅客户端)
        public static final int SENDBACK = 8;           // 发回批改
        public static final int SENDBACK_RECEIVED = 9;  // 收到批改（待订正）
        public static final int FINISHED = 10;          // 完成

        String[] STU_STRING_ZH = {
                "新作业", // 0
                "新作业", // 1
                "答题中", // 2
                "答题中", // 3 仅客户端
                "已提交", // 4
                "批改中", // 5
                "批改中", // 6 仅客户端
                "批改完成", // 7 仅客户端
                "发回批改", // 8
                "待订正", // 9
                "完成" // 10
        };
    }

    public interface AsmStatusType
    {
        public static final int ALL = 0;        // 所有状态
        public static final int FINISHED = 1;   // 已完成
        public static final int NEWTASK = 2;    // 新作业
        public static final int PROCESSING = 3; // 进行中
    }

    public interface ErrorCode
    {
        String NoError = "0";
        String ServerException = "10001";
        String InvalidParameter = "10002";
        String AccountNotExit = "10003";
        String PasswordNotMatch = "10004";
        String TokenInvalid = "10005";
        String PermissionDenied = "10006";
        String LicenseCodeNotExist = "10007";
        String LicenseCodeUsed = "10008";
        String DataInconsistent = "10009";  // 比如上传的zip包里的数据自相矛盾
    }

    public interface ResourceType
    {
        int UNKNOWN = 0;
        int FILE = 4;

        String[] STRING_ZH = { "未知", "文件" };
    }

    public interface ResourceTag
    {
        // to_be_deleted
        int ALL = 0;
        int OTHER = 1;
        int KEJIAN = 2;
        int JIAOAN = 3;

        String[] STRING_ZH = { "所有分类", "其它", "课件", "教案" };
    }

    public interface ResourceFileType
    {
        String[] ALL_VIDEO_TYPE_STRS = {"mp4", "flv", "mov", "3gp", "avi", "mkv", "mov", "mpg", "rmvb", "vob", "wmv", "ogv", "webm" };
        List<String> ALL_VIDEO_TYPE_LIST = Arrays.asList(ALL_VIDEO_TYPE_STRS);
        
        String[] ALL_AUDIO_TYPE_STRS = {"mp3", "wav", "ogg", "flac", "mmf", "ape", "mp2", "aac", "wma", "m4r", "amr", "wv", "mid", "m4a", "xmf" };
        List<String> ALL_AUDIO_TYPE_LIST = Arrays.asList(ALL_AUDIO_TYPE_STRS);

        String[] PLAYABLE_VIDEO_TYPE_STRS = { "mp4", "ogv", "webm" };
        List<String> PLAYABLE_VIDEO_TYPE_LIST = Arrays.asList(PLAYABLE_VIDEO_TYPE_STRS);

        String[] PLAYABLE_AUDIO_TYPE_STRS = { "mp3", "wav", "ogg" };
        List<String> PLAYABLE_AUDIO_TYPE_LIST = Arrays.asList(PLAYABLE_AUDIO_TYPE_STRS);

        String[] IMG_TYPE_STRS = { "jpg", "png", "ico", "bmp", "gif", "tif", "pcx", "tga" };
        List<String> IMG_TYPE_LIST = Arrays.asList(IMG_TYPE_STRS);

        String[] WORD_TYPE_STRS = { "txt", "pdf" };
        List<String> WORD_TYPE_LIST = Arrays.asList(WORD_TYPE_STRS);

        String VIDEO_TYPE = "video";
        String IMAGE_TYPE = "image";
        String AUDIO_TYPE = "audio";
        String OTHER_TYPE = "other";
    }

    public interface ResourceFromType
    {
        public static final int FROM_PERSONAL = 1;
        public static final int FROM_SCHOOL = 2;
        public static final int FROM_COMPANY = 3;
    }

    public interface IsFromCode
    {
        public static final String IsFrom_WeiXin = "weixin";
    }

    public interface Book
    {
        public static final float MIN_PRICE = 0.0f;
        public static final float MAX_PRICE = 999.9f;

        public static final String BOOK_COVER_FOLDER = "textbookCover/";
    }

    public interface Weixin
    {
        public static final String RESP_MESSAGE_TYPE_TEXT       = "text";
        public static final String RESP_MESSAGE_TYPE_MUSIC      = "music";
        public static final String RESP_MESSAGE_TYPE_NEWS       = "news";

        public static final String REQ_MESSAGE_TYPE_TEXT        = "text";
        public static final String REQ_MESSAGE_TYPE_IMAGE       = "image";
        public static final String REQ_MESSAGE_TYPE_LINK        = "link";
        public static final String REQ_MESSAGE_TYPE_LOCATION    = "location";
        public static final String REQ_MESSAGE_TYPE_VOICE       = "voice";
        public static final String REQ_MESSAGE_TYPE_EVENT       = "event";

        public static final String EVENT_TYPE_SUBSCRIBE         = "subscribe";
        public static final String EVENT_TYPE_UNSUBSCRIBE       = "unsubscribe";
        public static final String EVENT_TYPE_CLICK             = "click";
    }
    
    public interface NotificationTargetType
    {
        // 数据库中可能存这三种值或者直接存clsId拼接的字符串表示发布到了哪些班级
        
        public static final String ALL_USER     = "1";
        public static final String ALL_TEACHER  = "2";
        public static final String ALL_PARENT   = "3";
        public static final String SOME_CLASS   = "4";
        
        public static final String ALL_USER_STRING      = "全员通知";
        public static final String ALL_TEACHER_STRING   = "全体老师";
        public static final String ALL_PARENT_STRING    = "全体家长";
        public static final String SOME_CLASS_STRING    = "部分班级";
    }

    public interface RongCloudChat
    {
        public static final String APP_KEY      = "mgb7ka1nb57sg";
        public static final String APP_SECRET   = "Z7rXfF5kc3Qu";
    }
    
    private static void readSystemConfig()
    {
        try
        {
            if (!Utils.fileExist(PATH_FILE))
            {
                String[] otherPaths = {
                        "D:\\Software\\Tomcat\\host_root\\",
                        "D:\\var\\webapp\\file.war\\nlfdc\\"
                };

                for (String p : otherPaths)
                {
                    if (Utils.fileExist(p))
                    {
                        PATH_FILE = p;
                        break;
                    }
                }
            }

            Properties prop = null;
            String defaultConfigPath = Constants.PATH_FILE + "systemConfig/system.properties";
            if (Utils.fileExist(defaultConfigPath))
            {
                prop = Utils.readFromProperties(defaultConfigPath);
            }

            if (prop != null)
            {
                String urlDomain = prop.getProperty("website");
                if (urlDomain != null)
                {
                    URL_DOMAIN_IP = urlDomain.trim();
                }

                String urlDomainName = prop.getProperty("domainurl");
                if (urlDomainName != null)
                {
                    URL_DOMAIN_NAME = urlDomainName.trim();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    static
    {
        readSystemConfig();

        System.out.println("current file path: " + PATH_FILE);
        System.out.println("current domain ip: " + URL_DOMAIN_IP);
        System.out.println("current domain url: " + URL_DOMAIN_NAME);
    }
}
