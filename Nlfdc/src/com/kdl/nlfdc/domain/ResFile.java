package com.kdl.nlfdc.domain;

import java.io.Serializable;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;

public class ResFile implements Serializable
{
    private static final long serialVersionUID = -7251833970861062839L;

    private int fileId;
    private int resId;
    private String filePath;
    private String fileSuffix;
    private int fileSize;
    private String fileMd5;
    private int isDeleted;

    // ���Ӵ�ȡ��
    // --------------------------------------------------------------------------------
    public String getFileUrl()
    {
        try
        {
            if (Utils.fileExist(filePath))
            {
                String relToFile = "";
                if (filePath.startsWith(Constants.PATH_FILE))
                {
                    relToFile = filePath.replace(Constants.PATH_FILE, "");
                }
                else
                {
                    relToFile = filePath.split("file.war/")[1];
                }
                return Constants.URL_FILE + relToFile;
            }
        }
        catch (Exception e)
        {
        }

        return "";
    }

    // �ֶδ�ȡ��
    // --------------------------------------------------------------------------------
    public int getFileId()
    {
        return fileId;
    }

    public void setFileId(int fileId)
    {
        this.fileId = fileId;
    }

    public int getResId()
    {
        return resId;
    }

    public void setResId(int resId)
    {
        this.resId = resId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFileSuffix()
    {
        return this.fileSuffix;
    }

    public void setFileSuffix(String fileSuffix)
    {
        this.fileSuffix = fileSuffix;
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getFileMd5()
    {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5)
    {
        this.fileMd5 = fileMd5;
    }

    public int getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted)
    {
        this.isDeleted = isDeleted;
    }
}
