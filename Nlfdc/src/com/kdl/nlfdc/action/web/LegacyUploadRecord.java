package com.kdl.nlfdc.action.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletInputStream;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.codec.binary.Base64;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;

@SessionScope
@UrlBinding("/uploadrecordaction")
public class LegacyUploadRecord extends AbstractActionBean
{
    private static final long serialVersionUID = 24157991742562472L;

    @DefaultHandler
    @HandlesEvent("saverecordforflash")
    public Resolution saveRecordForFlash() throws IOException
    {
        String savePath = Constants.PATH_FILE + "q" + "uestion/record/";
        // String savePath = "E:\\var\\audio\\";
        File saveFolder = new File(savePath);
        if (!saveFolder.exists())
        {
            saveFolder.mkdirs();
        }

        long timeStamp = System.currentTimeMillis();
        String wordName = "record_" + timeStamp + ".mp3";
        String realPath = savePath + wordName;

        String fileString = getParam("file");
        byte[] data = Base64.decodeBase64(fileString.getBytes());
        File mp3File = new File(realPath);
        FileOutputStream fileOutStream = new FileOutputStream(mp3File, true);
        fileOutStream.write(data);
        fileOutStream.close();
        return getStringResolution("ok");
    }

    @HandlesEvent("saverecordforhtml5")
    public Resolution saveRecordForHtml5() throws IOException
    {
        String savePath = Constants.PATH_FILE + "q" + "uestion/record/";
        // String savePath = "E:\\var\\audio\\";
        File saveFolder = new File(savePath);
        if (!saveFolder.exists())
        {
            saveFolder.mkdirs();
        }

        long timeStamp = System.currentTimeMillis();
        String wordName = "record_" + timeStamp + ".wav";
        String realPath = savePath + wordName;

        ServletInputStream in = context.getRequest().getInputStream();
        OutputStream outStream = new FileOutputStream(realPath);
        int b = 0;
        while (b != -1)
        {
            in.available();
            b = in.read();
            if (b != -1)
            {
                outStream.write(b);
            }
        }
        outStream.close();
        return getStringResolution("ok");
    }

}
