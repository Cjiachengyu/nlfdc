package com.kdl.nlfdc.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.StuAsm;


/**
 * 定时任务
 * 
 * @author cjia
 *
 * @version 创建时间：2015年7月7日
 */
public class TimerTaskService extends CmService
{
    private static final long serialVersionUID = 6311334213169303680L;

    // 计划设计为一个小时执行一次检查
    // 过期时间设置为一天
    // 一天： 86400s
    private int EXPIRE_TIME = 86400;    // 过了finishTime一天
    private int LIMIT_EXPIRE_TIME = EXPIRE_TIME + 43200 + 1;   // 每12小时执行一次，加1是为了防止边界数据遗漏，过滤区间为<>,不包含边界

    private static boolean isFirstTime = true;

    private final SimpleDateFormat logDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    
    /**
     * 执行幼教过期任务的自动批改
     */
    public void execute()
    {
        if (Constants.URL_DOMAIN_IP.equals("http://localhost:8080"))
        {
           // return;
        }
        
        logTimerTaskService("Start execute autoCorrect-expired-yj-stuAsm service");
        long startTime = Utils.currentSeconds();

        List<StuAsm> needCorrectStuAsmList;
        if (isFirstTime)
        {
            // 防止服务器重启或者故障耗时很长导致部分任务没有被批改
            needCorrectStuAsmList = getOverFinishTimeNeedCorrectStuAsm(EXPIRE_TIME,
                    EXPIRE_TIME + 500000);
            logTimerTaskService("first getData limit time:(" + EXPIRE_TIME + ", " + (EXPIRE_TIME + 500000) + ")");
            isFirstTime = false;
        }
        else
        {
            needCorrectStuAsmList = getOverFinishTimeNeedCorrectStuAsm(EXPIRE_TIME,
                    LIMIT_EXPIRE_TIME);
            logTimerTaskService("not-first getData limit time:(" + EXPIRE_TIME + ", " + LIMIT_EXPIRE_TIME + ")");
        }

        logTimerTaskService(needCorrectStuAsmList.size() + " stuAsm to be corrected");
        try
        {
            for (StuAsm stuAsm : needCorrectStuAsmList)
            {
                // 默认给四朵小红花
                correctYjAsm(stuAsm.getAsmId(), stuAsm.getClsId(), stuAsm.getStuId(), 4, "");
                logTimerTaskService("       corrected stuAsm(asmId:" + stuAsm.getAsmId() + ", clsId:"
                        + stuAsm.getClsId() + ", stuId:" + stuAsm.getStuId() + ")");
            }
        }
        catch (Exception e)
        {
            logTimerTaskService("catch exception");
        }

        needCorrectStuAsmList = null;
        long endTime = Utils.currentSeconds();
        logTimerTaskService("Finish execute autoCorrect-expired-yj-stuAsm service");
        logTimerTaskService("Cost Time: " + (endTime - startTime) + " seconds");
    }


    // private
    // --------------------------------------------------------------------------------
    private void logTimerTaskService(Object logText)
    {
        log(logText, getTimeraTaskServiceLogFilePath());
    }

    private String getTimeraTaskServiceLogFilePath()
    {
        String logFileName = Utils.getFullDateString(Utils.currentSeconds()) + ".tsk.log";
        String logFilePath = Constants.PATH_FILE + "timerTaskService/" + logFileName;
        return logFilePath;
    }

    private synchronized void log(Object logText, String logFilePath)
    {
        FileWriter filerWriter = null;
        BufferedWriter bufWriter = null;
        try
        {
            String toLog = String.valueOf(logText).replaceAll("[\r\n]", " ");

            StringBuffer logBuffer = new StringBuffer();
            logBuffer.append(logDateTimeFormat.format(new Date()));
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
