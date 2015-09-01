package com.kdl.nlfdc.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONObject;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class Utils
{
    // common
    // --------------------------------------------------------------------------------
    public static String getFullTimeWithSecondsString(int utfSeconds)
    {
        if (utfSeconds > 1)
        {
            return Constants.SDF_FULL_TIME_WITH_SECOND.format(Long.valueOf(utfSeconds) * 1000);
        }
        else
        {
            return "";
        }
    }

    public static String getFullTimeString(int utfSeconds)
    {
        if (utfSeconds > 1)
        {
            return Constants.SDF_FULL_TIME.format(Long.valueOf(utfSeconds) * 1000);
        }
        else
        {
            return "";
        }
    }

    public static String getSimpleTimeString(int utfSeconds)
    {
        if (utfSeconds > 1)
        {
            String timeString = Constants.SDF_FULL_TIME.format(Long.valueOf(utfSeconds) * 1000);
            String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            if (timeString.startsWith(thisYear))
            {
                return Constants.SDF_SIMPLE_TIME.format(Long.valueOf(utfSeconds) * 1000);
            }
            else 
            {
                return timeString;
            }
        }
        else
        {
            return "";
        }
    }

    public static String getFullDateString(int utfSeconds)
    {
        if (utfSeconds > 0)
        {
            return Constants.SDF_FULL_DATE.format(Long.valueOf(utfSeconds) * 1000);
        }
        else
        {
            return "";
        }
    }

    public static String getSimpleDateString(int utfSeconds)
    {
        if (utfSeconds > 0)
        {
            return Constants.SDF_SIMPLE_DATE.format(Long.valueOf(utfSeconds) * 1000);
        }
        else
        {
            return "";
        }
    }

    public static int currentSeconds()
    {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static String getStrFromJsonObj(JSONObject jo, String key, String defaultVal)
    {
        try
        {
            return jo.getString(key);
        }
        catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static int getIntFromJsonObj(JSONObject jo, String key, int defaultVal)
    {
        try
        {
            return jo.getInt(key);
        }
        catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static float getPercent(double d1, double d2, int precision)
    {
        double base = 1.0;
        for (int i = 0; i < precision; i++)
        {
            base *= 10.0;
        }

        int thousandTimes = (int) Math.rint(d1 * 100.0 * base / d2);
        return (float) (thousandTimes / base);
    }

    public static double roundDouble(double d, int precision)
    {
        double base = 1.0;
        for (int i = 0; i < precision; i++)
        {
            base *= 10.0;
        }

        int baseTimes = (int) Math.rint(d * base);
        return baseTimes / base;
    }

    // string
    // --------------------------------------------------------------------------------
    public static boolean stringEmpty(String s)
    {
        return s == null || s.trim().equals("");
    }

    public static boolean stringNotEmpty(String s)
    {
        return s != null && !s.trim().equals("");
    }

    public static String trimEnd(String src, int count)
    {
        if (src.length() > 0)
        {
            return src.substring(0, src.length() - count);
        }
        else
        {
            return src;
        }
    }

    public static String renameFile(String name, boolean numberBeforeSuffix)
    {
        if (stringEmpty(name))
        {
            return null;
        }

        String apd = "";

        if (numberBeforeSuffix)
        {
            int dotIndex = name.lastIndexOf(".");
            if (dotIndex != -1)
            {
                apd = name.substring(dotIndex);
                name = name.substring(0, dotIndex);
            }
        }

        if (name.endsWith(")"))
        {
            try
            {
                int beg = name.lastIndexOf("(");
                int end = name.lastIndexOf(")");

                String replace = name.substring(beg + 1, end);
                int newInt = Integer.parseInt(replace) + 1;

                return name.substring(0, beg) + "(" + newInt + ")" + apd;
            }
            catch (Exception e)
            {
            }
        }

        return name + "(1)" + apd;
    }

    public static String getFileSuffix(String fileName)
    {
        String fileSuffix = "";
        int indexDot = fileName.lastIndexOf(".");
        if (indexDot != -1)
        {
            fileSuffix = fileName.substring(indexDot + 1);
        }
        return fileSuffix.toLowerCase();
    }

    public static String getRandomString(int length)
    {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(charBase.length());
            sb.append(charBase.charAt(number));
        }
        return sb.toString();
    }

    public static List<Integer> splitIdList(String source, String sep)
    {
        List<Integer> sList = new ArrayList<Integer>();
        if (stringEmpty(source) || sep == null)
        {
            return sList;
        }

        String[] sArr = source.split(sep);
        for (String s : sArr)
        {
            if (stringNotEmpty(s))
            {
                sList.add(Integer.parseInt(s.trim()));
            }
        }
        return sList;
    }


    // file
    // --------------------------------------------------------------------------------
    public static boolean fileExist(String filePath)
    {
        return new File(filePath).exists();
    }

    public static File makeSureDirExists(String dirPath)
    {
        File dir = new File(dirPath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        return dir;
    }

    public static File makeSureFileExist(String filePath) throws IOException
    {
        File file = new File(filePath);
        if (file.exists())
        {
            return file;
        }

        makeSureDirExists(file.getParent());

        if (!file.exists())
        {
            file.createNewFile();
        }

        return file;
    }


    public static String getRequestParamsString(HttpServletRequest request)
    {

        String encryptVersion = request.getHeader("encryptVersion");

        StringBuilder sb = new StringBuilder();
        sb.append("Params -> ");
        Enumeration<String> pNames = request.getParameterNames();
        while (pNames.hasMoreElements())
        {
            String key = pNames.nextElement();
            String val = request.getParameter(key);

            sb.append(key + "-" + decryptParam(encryptVersion, val) + " | ");
        }

        sb.append("Headers -> ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String key = headerNames.nextElement();
            String val = request.getHeader(key);

            sb.append(key + "-" + decryptParam(encryptVersion, val) + " | ");
        }

        return sb.toString();
    }


    // encrypt
    // --------------------------------------------------------------------------------
    public static String decryptStringV1(String src)
    {

        char[] sChars = src.toCharArray();
        byte[] dBytes = new byte[sChars.length / 2];

        for (int i = 0; i < dBytes.length; i++)
        {
            byte b1 = (byte) hexToInt(sChars[i * 2]);
            byte b2 = (byte) hexToInt(sChars[i * 2 + 1]);

            byte b = (byte) (b1 * 16 + b2);
            dBytes[i] = b;
        }

        String ret = null;
        try
        {
            ret = new String(dBytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return ret;
    }


    // others
    // --------------------------------------------------------------------------------
    public static void safeClose(Closeable toClose)
    {
        if (toClose != null)
        {
            try
            {
                toClose.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void safeClose(ZipFile toClose)
    {
        if (toClose != null)
        {
            try
            {
                toClose.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static Properties readFromProperties(String filePath) throws IOException
    {
        Properties prop = new Properties();
        InputStream fin = null;
        InputStream in = null;
        try
        {
            fin = new FileInputStream(filePath);
            in = new BufferedInputStream(fin);
            prop.load(in);
        }
        finally
        {
            safeClose(fin);
            safeClose(in);
        }

        return prop;
    }

    public static String readFromFile(String filePath, String encoding) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = br.readLine()) != null)
        {
            buffer.append(line);
        }
        String content = buffer.toString();
        safeClose(br);
        return content;
    }

    public static void zipFile(String baseDir, String fileName) throws IOException
    {
        List<File> fileList = getAllContainedFiles(new File(baseDir));
        ZipOutputStream zos = null;
        try
        {
            zos = new ZipOutputStream(new FileOutputStream(fileName));
            for (File f : fileList)
            {
                if (f.exists())
                {
                    ZipEntry ze = new ZipEntry(getAbsFileName(baseDir, f));
                    ze.setSize(f.length());
                    ze.setTime(f.lastModified());
                    zos.putNextEntry(ze);
                    zos.setEncoding("GBK");

                    InputStream is = null;
                    try
                    {
                        is = new BufferedInputStream(new FileInputStream(f));
                        int readLen = 0;
                        byte[] buf = new byte[BUFFER];
                        while ((readLen = is.read(buf, 0, BUFFER)) != -1)
                        {
                            zos.write(buf, 0, readLen);
                        }
                    }
                    finally
                    {
                        safeClose(is); // 对于每一次for循环，内层的close是需要的
                    }
                }
            }
        }
        finally
        {
            safeClose(zos);
        }
    }

    public static void userErrorZipFile(List<File> fileList, String fileName) throws IOException
    {
        List<File> zipFileList = new ArrayList<File>();
        File parentFile = new File(fileName).getParentFile();
        if (!parentFile.exists())
        {
            parentFile.mkdirs();
        }
        for (File f : fileList)
        {
            if (f.isDirectory())
            {
                zipFileList.addAll(getAllContainedFiles(f));
            }
            else
            {
                zipFileList.add(f);
            }
        }
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName));
        ZipEntry ze = null;
        byte[] buf = new byte[BUFFER];
        int readLen = 0;
        for (File f : zipFileList)
        {
            String parentPath;
            if (!f.getAbsolutePath().endsWith(".kdl"))
            {
                parentPath = f.getParentFile().getParent();
            }
            else
            {
                parentPath = f.getParent();
            }
            ze = new ZipEntry(getAbsFileName(parentPath, f));
            ze.setSize(f.length());
            ze.setTime(f.lastModified());
            zos.putNextEntry(ze);
            InputStream is = new BufferedInputStream(new FileInputStream(f));
            while ((readLen = is.read(buf, 0, BUFFER)) != -1)
            {
                zos.write(buf, 0, readLen);
            }
            zos.setEncoding("GBK");
            safeClose(is);
        }
        safeClose(zos);
    }

    public static void unzipFile(String zipPath, String outputDir) throws IOException
    {
        makeSureDirExists(outputDir);

        ZipFile zipFile = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try
        {
            zipFile = new ZipFile(zipPath);
            Enumeration<?> entries = zipFile.getEntries();
            while (entries.hasMoreElements())
            {
                // 获取输入
                ZipEntry entry = (ZipEntry) entries.nextElement();
                bis = new BufferedInputStream(zipFile.getInputStream(entry));

                // 获取输出
                File file = makeSureFileExist(outputDir + entry.getName());
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, BUFFER);

                // 读取写入
                int count = -1;
                byte buf[] = new byte[BUFFER];
                while ((count = bis.read(buf, 0, BUFFER)) > -1)
                {
                    bos.write(buf, 0, count);
                }
                bos.flush();

                // 有必要每次while循环都关闭一次
                bis.close();
                bos.close();
                fos.close();
            }
        }
        finally
        {
            safeClose(zipFile);
            safeClose(bos);
            safeClose(fos);
            safeClose(bis);
        }
    }


    // implement
    // --------------------------------------------------------------------------------
    private static final int BUFFER = 1024;
    private static String charBase = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static String getAbsFileName(String baseDir, File realFileName)
    {
        File file = realFileName;
        File base = new File(baseDir);
        String ret = file.getName();
        while (true)
        {
            file = file.getParentFile();
            if (file == null)
                break;
            if (file.equals(base))
                break;
            else
                ret = file.getName() + File.separator + ret;
        }
        return ret;
    }

    private static List<File> getAllContainedFiles(File baseDir)
    {
        List<File> allFiles = new ArrayList<File>();
        File[] directFiles = baseDir.listFiles();
        for (int i = 0; i < directFiles.length; i++)
        {
            if (directFiles[i].isFile())
            {
                allFiles.add(directFiles[i]);
            }
            else if (directFiles[i].isDirectory())
            {
                allFiles.addAll(getAllContainedFiles(directFiles[i]));
            }
        }

        return allFiles;
    }

    private static int hexToInt(char hexChar)
    {
        if ((hexChar >= '0' && hexChar <= '9') || (hexChar >= 'A' && hexChar <= 'F'))
            return Integer.parseInt(hexChar + "", 16);
        else
            return 0;
    }

    private static String decryptParam(String encryptVersion, String val)
    {
        String ret = val;

        if (Utils.stringNotEmpty(encryptVersion))
        {
            switch (Integer.parseInt(encryptVersion))
            {
            case 1:
                // ret = Utils.decryptStringV1(val);
                break;
            case 2:
                // next version
                ret = val;
                break;
            }
        }
        return ret;
    }

}
