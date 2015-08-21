package com.kdl.nlfdc.action;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONObject;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.kdl.nlfdc.action.component.IRankable;

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

    public static List<String> splitStrList(String source, String sep)
    {
        List<String> sList = new ArrayList<String>();
        if (stringEmpty(source) || sep == null)
        {
            return sList;
        }

        String[] sArr = source.split(sep);
        for (String s : sArr)
        {
            if (stringNotEmpty(s))
            {
                sList.add(s.trim());
            }
        }
        return sList;
    }

    public static String getInForSql(Object... args)
    {
        List<Object> argList = new ArrayList<Object>();
        for (Object o : args)
        {
            argList.add(o);
        }

        return getInForSql(argList);
    }

    public static String getInForSql(List<Object> args)
    {
        if (args == null || args.size() == 0)
        {
            return "";
        }

        StringBuilder sb = new StringBuilder("(");

        int length = args.size();
        for (int i = 0; i < length; i++)
        {
            if (i != length - 1)
            {
                sb.append(args.get(i)).append(", ");
            }
            else
            {
                sb.append(args.get(i));
            }
        }
        sb.append(")");

        return sb.toString();
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

    public static byte[] readFileAllBytes(String filePath) throws IOException
    {
        InputStream in = null;

        try
        {
            File file = new File(filePath);
            in = new FileInputStream(file);

            int fileLen = (int) file.length();
            int leftToDo = fileLen;
            byte[] ret = new byte[fileLen];
            while (leftToDo > 0)
            {
                int oneRead = in.read(ret, fileLen - leftToDo, leftToDo);
                leftToDo -= oneRead;
            }

            return ret;
        }
        finally
        {
            safeClose(in);
        }
    }

    public static String readFileAllText(String filePath, String charsetName) throws IOException
    {
        byte[] data = readFileAllBytes(filePath);

        return new String(data, charsetName);
    }

    public static String readFileAllText(String filePath) throws IOException
    {
        return readFileAllText(filePath, "UTF-8");
    }

    public static String[] readFileAllLines(String filePath) throws IOException
    {
        String allStr = readFileAllText(filePath);
        allStr = allStr.replaceAll("\r\n", "\n");

        return allStr.split("[\r\n]");
    }

    public static void writeFileAllBytes(String filePath, byte[] data, int startPosition) throws IOException
    {
        File f = new File(filePath);
        if (!f.exists())
        {
            File dir = f.getParentFile();
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            f.createNewFile();
        }

        RandomAccessFile raf = null;
        try
        {
            raf = new RandomAccessFile(f, "rw");
            raf.seek(startPosition);
            raf.write(data);
        }
        finally
        {
            safeClose(raf);
        }
    }

    public static void writeFileAllBytes(String filePath, byte[] data) throws IOException
    {
        File f = new File(filePath);
        if (f.exists())
        {
            f.delete();
        }

        writeFileAllBytes(filePath, data, 0);
    }

    public static void writeFileAllText(String filePath, String content, String charsetName) throws IOException
    {
        writeFileAllBytes(filePath, content.getBytes(charsetName));
    }

    public static void writeFileAllText(String filePath, String content) throws IOException
    {
        writeFileAllBytes(filePath, content.getBytes("UTF-8"));
    }

    public static String getWebImageSuffix(String webImagePath)
    {
        String webImageType = getFileSuffix(webImagePath);

        List<String> imageTypes = new ArrayList<String>();
        imageTypes.add("bmp");
        imageTypes.add("png");
        imageTypes.add("gif");
        imageTypes.add("jpg");
        imageTypes.add("jpeg");
        imageTypes.add("pjpeg");

        if (!imageTypes.contains(webImageType))
        {
            webImageType = "img";
        }
        return webImageType;
    }

    public static boolean saveWebFileToLocal(String webImageUrl, String localImagePath)
    {
        InputStream is = null;
        OutputStream os = null;
        try
        {
            URL url = new URL(webImageUrl);
            URLConnection conn = url.openConnection();
            is = conn.getInputStream();
            os = new FileOutputStream(localImagePath);
            byte[] b = new byte[512];
            int len = 0;
            while ((len = is.read(b)) != -1)
            {
                os.write(b, 0, len);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            safeClose(is);
            safeClose(os);
        }
    }

    // md5
    // --------------------------------------------------------------------------------
    public static String getMd5(byte[] data)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        byte[] md5Bytes = md.digest(data);

        BigInteger bigInt = new BigInteger(1, md5Bytes);
        String hashResult = bigInt.toString(16);
        while (hashResult.length() < 32)
        {
            hashResult = "0" + hashResult;
        }

        return hashResult;
    }

    public static String getMd5(String filePath) throws IOException
    {
        byte[] fileData = readFileAllBytes(filePath);

        return getMd5(fileData);
    }

    // http
    // --------------------------------------------------------------------------------
    public static byte[] getPostedBytes(HttpServletRequest request) throws IOException
    {
        byte[] uploadedBytes = new byte[request.getContentLength()];
        ServletInputStream inputStream = request.getInputStream();
        int offset = 0;
        while (offset < uploadedBytes.length)
        {
            int oneRead = inputStream.read(uploadedBytes, offset, uploadedBytes.length - offset);
            offset += oneRead;
        }

        String postMd5 = request.getHeader("postMd5");
        if (postMd5 == null)
        {
            postMd5 = request.getParameter("postMd5");
        }

        if (stringNotEmpty(postMd5) && !postMd5.equals(getMd5(uploadedBytes)))
        {
            throw new IOException("post data md5 inconsist");
        }

        return uploadedBytes;
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

    // gzip
    // --------------------------------------------------------------------------------
    public static byte[] getGzippedBytes(byte[] rawBytes) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(rawBytes);
        gzip.finish();
        safeClose(gzip);
        byte[] bytesToWrite = bos.toByteArray();
        safeClose(bos);
        return bytesToWrite;
    }

    /**
     * 获取显示文件大小的字符串
     * 
     * @param bytesSize
     * @return
     */
    public static String getFileSize(long bytesSize)
    {
        String result = "";

        float kSize = 0;
        float mSize = 0;
        float gSize = 0;

        kSize = ((float) bytesSize / 1024);
        kSize = (float) (Math.round(kSize * 10) / 10.0);
        result = kSize + "K";

        if (kSize >= 1024)
        {
            mSize = kSize / 1024;
            mSize = (float) (Math.round(mSize * 10) / 10.0);
            result = mSize + "M";
        }

        if (mSize > 1024)
        {
            gSize = kSize / 1024;
            gSize = (float) (Math.round(gSize * 10) / 10.0);
            result = gSize + "G";
        }
        return result;
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

    // image
    // --------------------------------------------------------------------------------
    public static void resizeImage(String srcPath, String dstPath, int maxWidth, int maxHeight, String format)
            throws IOException
    {
        // 此函数等比变化图片大小，变化后的图片最大宽度为maxWidth，最大高度为maxHeight
        File srcImageFile = new File(srcPath);
        BufferedImage srcImage = ImageIO.read(srcImageFile);

        int width = srcImage.getWidth();
        int height = srcImage.getHeight();

        double ratio = getResizeRatio(maxWidth, maxHeight, width, height);
        BufferedImage dstImage = resizeImage(srcImage, ratio);

        File dstImageFile = new File(dstPath);
        ImageIO.write(dstImage, format, dstImageFile);
    }

    public static void resizeImage(String srcPath, String dstPath, int newWidth, String format) throws IOException
    {
        File srcImageFile = new File(srcPath);
        BufferedImage srcImage = ImageIO.read(srcImageFile);

        BufferedImage dstImage = resizeImage(srcImage, newWidth);

        File dstImageFile = new File(dstPath);
        ImageIO.write(dstImage, format, dstImageFile);
    }

    public static void resizeImage(String srcPath, String dstPath, double ratio, String format) throws IOException
    {
        File srcImageFile = new File(srcPath);
        BufferedImage srcImage = ImageIO.read(srcImageFile);

        BufferedImage dstImage = resizeImage(srcImage, ratio);

        File dstImageFile = new File(dstPath);
        ImageIO.write(dstImage, format, dstImageFile);
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, int newWidth)
    {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int newHeight = (int) (height * newWidth) / width;

        return resizeImage(srcImage, newWidth, newHeight);
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, double ratio)
    {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int newWidth = (int) (width * ratio);
        int newHeight = (int) (height * ratio);

        return resizeImage(srcImage, newWidth, newHeight);
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, int newWidth, int newHeight)
    {
        BufferedImage dstImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = dstImage.createGraphics();
        graphics.drawImage(srcImage, 0, 0, newWidth, newHeight, null);

        return dstImage;
    }

    public static void concatImage(List<String> srcFilePathList, String dstFilePath, int maxWidth, int maxHeight)
            throws IOException
    {
        // 拼接多张图片，并限制宽度和高度的上限
        List<BufferedImage> srcImageList = new ArrayList<BufferedImage>();
        for (String path : srcFilePathList)
        {
            File f = new File(path);
            BufferedImage srcImage = ImageIO.read(f);

            int width = srcImage.getWidth();
            int height = srcImage.getHeight();
            double ratio = getResizeRatio(maxWidth, maxHeight, width, height);
            srcImage = resizeImage(srcImage, (int) (width * ratio), (int) (height * ratio));

            srcImageList.add(srcImage);
        }

        BufferedImage newImage = concatImage(srcImageList);

        File outFile = new File(dstFilePath);
        ImageIO.write(newImage, "jpg", outFile);
    }

    public static BufferedImage concatImage(List<BufferedImage> images)
    {
        // 将多张图片以垂直方向拼接，图片水平居中，空白填充白色
        int widthNew = 0;
        int heightNew = 0;
        List<Integer> widths = new ArrayList<Integer>();
        List<Integer> heights = new ArrayList<Integer>();
        for (BufferedImage image : images)
        {
            widths.add(image.getWidth());
            heights.add(image.getHeight());
            if (widthNew < image.getWidth())
                widthNew = image.getWidth();
            heightNew += image.getHeight();
        }

        BufferedImage newImage = new BufferedImage(widthNew, heightNew,
                BufferedImage.TYPE_INT_RGB);

        Graphics g = newImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, widthNew, heightNew);
        int heightTemp = 0;
        for (int i = 0; i < images.size(); i++)
        {
            newImage.setRGB(
                    (widthNew - widths.get(i)) / 2,
                    heightTemp,
                    widths.get(i),
                    heights.get(i),
                    images.get(i).getRGB(0, 0, widths.get(i), heights.get(i),
                            new int[widths.get(i) * heights.get(i)], 0,
                            widths.get(i)), 0, widths.get(i));
            heightTemp += heights.get(i);
        }

        return newImage;
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

    public static void copyFile(String srcPath, String desPath) throws IOException
    {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;

        try
        {
            inStream = new FileInputStream(srcPath);
            outStream = new FileOutputStream(desPath);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        }
        finally
        {
            safeClose(inStream);
            safeClose(in);
            safeClose(outStream);
            safeClose(out);
        }
    }

    public static void copyDir(String sourceDir, String targetDir) throws IOException
    {
        File targetDirFile = makeSureDirExists(targetDir);
        File sourceDirFile = new File(sourceDir);

        File[] files = sourceDirFile.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File file = files[i];
            String sourceFile = sourceDirFile.getAbsolutePath() + File.separator + file.getName();
            String targetFile = targetDirFile.getAbsolutePath() + File.separator + file.getName();

            if (file.isFile())
            {
                copyFile(sourceFile, targetFile);
            }
            else if (file.isDirectory())
            {
                copyDir(sourceFile, targetFile);
            }
        }
    }

    public static void deleteFileOrDir(File file)
    {
        if (file.exists())
        {
            if (file.isFile())
            {
                file.delete();
            }
            else if (file.isDirectory())
            {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    deleteFileOrDir(files[i]);
                }
                file.delete();
            }
        }
    }

    public static String convertSecondsToString(int totalSeconds, String lang)
    {
        float time = (float) (totalSeconds / 60.0);
        return convertMinuteToString(time, lang);
    }

    public static String convertMinuteToString(float time, String lang)
    {
        String secondString = "''";
        String minuteString = "'";
        String hourString = ":";

        StringBuilder timeToString = new StringBuilder("");
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        minutes = (int) time;
        seconds = (int) ((time - minutes) * 60);
        if (minutes >= 60)
        {
            hours = minutes / 60;
            minutes = minutes - hours * 60;
        }
        if (minutes >= 60)
        {
            hours = minutes / 60;
            minutes = minutes - hours * 60;
        }
        if (hours != 0)
        {
            timeToString.append(hours + hourString);
        }
        if (minutes != 0)
        {
            timeToString.append(minutes + minuteString);
        }
        if (seconds != 0)
        {
            timeToString.append(seconds + secondString);
        }
        return timeToString.toString();
    }

    public static String convertChinese(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            int v = (int) ch;
            if (v >= 19968 && v <= 171941)
            {
                str = str.replaceAll(String.valueOf(ch), v / 10000 + "");
            }
        }
        return str;
    }

    public static StringBuffer getQuwei(String str)
    {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] b = null;
        try
        {
            b = str.getBytes("gb2312");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        int[] quwei = new int[b.length / 2];
        for (int i = 0, k = b.length / 2; i < k; i++)
        {
            quwei[i] = (((b[2 * i] - 0xA0) & 0xff) * 100) + ((b[2 * i + 1] - 0xA0) & 0xff);
        }
        for (int i : quwei)
        {
            stringBuffer.append(i);
        }
        return stringBuffer;
    }

    public static String getSimpleAnalyzedWords(String words)
    {
        String analyzedWords = "";
        int wordsLength = words.length();
        for (int i = 0; i < wordsLength;)
        {
            if (words.charAt(i) == ' ')
            {
                i++;
                continue;
            }
            String substring = words.substring(i, i + 1);
            int bLength = substring.getBytes().length;
            int substrLength = substring.length();
            if (Pattern.compile("[0-9]*").matcher(substring).matches())
            {
                int j = i + 1;
                for (; j < wordsLength;)
                {
                    if (words.charAt(j) == ' ')
                    {
                        break;
                    }
                    String substringNext = words.substring(j, j + 1);
                    if (Pattern.compile("[0-9]*").matcher(substringNext).matches())
                    {
                        substring += substringNext;
                        j++;
                    }
                    else
                    {
                        break;
                    }
                }
                i = j;
                analyzedWords = analyzedWords + substring + " ";
                continue;
            }
            else if (bLength == substrLength)
            {
                int j = i + 1;
                for (; j < wordsLength;)
                {
                    if (words.charAt(j) == ' ')
                    {
                        break;
                    }
                    String substringNext = words.substring(j, j + 1);
                    if (Pattern.compile("[0-9]*").matcher(substringNext).matches())
                    {
                        break;
                    }
                    else
                    {
                        int bLengthNext = substringNext.getBytes().length;
                        int substrLengthNext = substringNext.length();
                        if (bLengthNext == substrLengthNext)
                        {
                            substring += substringNext;
                            j++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                i = j;
                analyzedWords = analyzedWords + substring + " ";
                continue;
            }
            else
            {
                if (wordsLength == 1)
                {
                    analyzedWords = getQuwei(words).toString();
                }
                else if (i < wordsLength - 1)
                {
                    String substringNext = words.substring(i + 1, i + 2);
                    int bLengthNext = substringNext.getBytes().length;
                    int substrLengthNext = substringNext.length();
                    if (bLengthNext != substrLengthNext)
                    {
                        analyzedWords = analyzedWords + getQuwei(substring).toString()
                                + getQuwei(substringNext).toString()
                                + " ";
                    }
                }
            }
            i++;
        }
        return analyzedWords;
    }

    public static String getCharsetName(String filePath)
    {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        File file = new File(filePath);
        try
        {
            charset = detector.detectCodepage(new BufferedInputStream(new FileInputStream(file)), 100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String charsetName = "GBK";
        if (charset != null)
        {
            charsetName = charset.name();
        }
        return charsetName;
    }

    // common operation
    // --------------------------------------------------------------------------------
    public static void setListRanking(List<? extends IRankable> list)
    {
        Collections.sort(list, new Comparator<IRankable>()
        {
            @Override
            public int compare(IRankable r1, IRankable r2)
            {
                if (r1.isCountIn() && r2.isCountIn())
                {
                    if (r1.getRankValue() - r2.getRankValue() < 0)
                    {
                        return -1;
                    }
                    else if (r1.getRankValue() - r2.getRankValue() > 0)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else if (r1.isCountIn() && !r2.isCountIn())
                {
                    return -1;
                }
                else if (!r1.isCountIn() && r2.isCountIn())
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });

        int tempRanking = 1;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).isCountIn())
            {
                if (i > 0 && list.get(i).getRankValue() != list.get(i - 1).getRankValue())
                {
                    tempRanking = i + 1;
                }

                list.get(i).setRanking(tempRanking);
            }
        }
    }

    public static synchronized int getNextIntId() throws IOException
    {
        String genFilePath = Constants.PATH_FILE + "generate_id.txt";

        if (genId == -1)
        {
            if (new File(genFilePath).exists())
            {
                genId = Integer.parseInt(readFileAllText(genFilePath).trim()) + 100;
            }
            else
            {
                genId = 1000;
            }
        }

        genId++;
        writeFileAllText(genFilePath, "" + genId);

        return genId;
    }

    public static synchronized long getNextLongId()
    {
        return longId++;
    }

    /**
     * 将UserId分组，暂时设置为1000分三级
     */
    public static String groupUserId(int userId)
    {
        int a, b, c;
        int size = 1000;

        a = userId / (size * size);
        b = (userId / size) % size;
        c = (userId % size);
        return "user/" + a + "/" + b + "/" + c + "/" + userId;
    }

    // implement
    // --------------------------------------------------------------------------------
    private static volatile int genId = -1;
    private static volatile long longId = System.currentTimeMillis();
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

    private static double getResizeRatio(int maxWidth, int maxHeight, int width, int height)
    {
        int newWidth = width;
        int newHeight = height;
        if (newWidth > maxWidth)
        {
            newWidth = maxWidth;
            newHeight = (int) (height * newWidth) / width;
        }

        if (newHeight > maxHeight)
        {
            newHeight = maxHeight;
            newWidth = (int) (width * newHeight) / height;
        }

        return (newWidth * 1.0) / width;
    }

    public static void sendMail(String host, String pwd, String sourceAddr, String destAddr,
            String subject, String text) throws AddressException, MessagingException
    {
        String[] lines = text.split("\n");
        String bodyText = "";
        for (String l : lines)
        {
            if (stringEmpty(l))
            {
                bodyText += "<br>";
            }
            else
            {
                bodyText += "<p>" + l + "</p>";
            }
        }
        String html = "<!DOCTYPE html> <html> <head>"
                + "<meta charset=\"utf-8\" /> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>"
                + "<title>" + subject + "</title>"
                + "</head><body><div style='font-size:20px;'>" + bodyText + "</div></body></html>";

        Properties props = new Properties();
        props.put("mail.smtp.host", host); // 设置发送邮件的邮件服务器的属性
        props.put("mail.smtp.auth", "true"); // 需要经过授权验证
        Session session = Session.getDefaultInstance(props); // 构建一个session
        // session.setDebug(true); // console处显示过程信息

        MimeMessage message = new MimeMessage(session); // 用session为参数定义消息对象
        message.setFrom(new InternetAddress(sourceAddr)); // 加载发件人地址
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destAddr)); // 加载收件人地址
        message.setSubject(subject); // 加载标题
        message.setSentDate(new Date());// 设置邮件发送时期
        message.setContent(html, "text/html;charset=UTF-8"); // 将multipart对象放到message中
        message.saveChanges(); // 保存邮件
        Transport transport = session.getTransport("smtp"); // 发送邮件
        transport.connect(host, sourceAddr, pwd); // 连接服务器的邮箱
        transport.sendMessage(message, message.getAllRecipients()); // 发送邮件
        transport.close();
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
