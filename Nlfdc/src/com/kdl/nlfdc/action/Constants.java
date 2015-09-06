package com.kdl.nlfdc.action;

import java.text.SimpleDateFormat;

public class Constants
{
    // 每个系统相同的常数
    // --------------------------------------------------------------------------------
    public static final int JS_CSS_IMG_VERSION = Utils.currentSeconds();

    public static final int IS_DELETED = 1;
    public static final int NOT_DELETED = 0;

    public static final String IMAGE_FOLDER = "newsAndRollingImage/";
    // 每个系统不同的常数
    // --------------------------------------------------------------------------------
    public static String PATH_FILE = "/var/webapp/file.war/nlfdc/";

    static
    {
        readSystemConfig();

        System.out.println("current file path: " + PATH_FILE);
    }
    // 其它常数
    // --------------------------------------------------------------------------------
    public static final String URL_FILE = "../file/nlfdc/";

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
        public static final int PASSWORD = 16;
        public static final int LOGIN_NAME = 16;
        public static final int USER_NAME = 16;
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

    public interface User
    {
        public static final String DEFAULT_PASSWORD = "123456";
    }

    public interface UserRole
    {
        public static final int SUPER_ADMIN = 0;
        public static final int COMMON_ADMIN = 1;
        
        String LOGIN_ID_PATTERN = "^[0-9]+[stpmraez]$";
        String CELLPHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    }

    public interface MainMenuOperation
    {
        public static final String NOTIFICATION_MANAGE = "01";
        public static final String ADMIN_MANAGE = "02";
        public static final String ADMIN_IMAGE_MANAGE = "03";
        
    }

    public interface ResourceType
    {
        int UNKNOWN = 0;
        int FILE = 4;

        String[] STRING_ZH = { "未知", "文件" };
    }

    private static void readSystemConfig()
    {
        try
        {
            if (!Utils.fileExist(PATH_FILE))
            {
                System.out.println("dir not exist");
                String[] otherPaths = {
                        "D:\\var\\webapp\\file.war\\youjiao\\"
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
            else
            {
                
                System.out.println("dir exist");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

}
