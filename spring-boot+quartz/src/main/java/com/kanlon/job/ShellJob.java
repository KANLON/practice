package com.kanlon.job;

import com.kanlon.common.Constant;
import com.kanlon.exception.QuartzException;
import com.kanlon.model.QuartzResultModel;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 执行shell的定时任务
 *
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class ShellJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(ShellJob.class);

    @Autowired
    private CommonJobService commonJobService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //初始调度结果
        QuartzResultModel model = commonJobService.startSchedule();
        Long oldTime = System.currentTimeMillis();
        //存放执行结果日志
        StringBuffer resultLog = new StringBuffer();
        // 执行退出的执行码
        Integer exitValue = null;
        BufferedReader bufferedReader = null;
        try {
            Trigger trigger = context.getTrigger();
            logger.info("执行的任务名为：" + trigger.getKey());
            JobDataMap data = context.getTrigger().getJobDataMap();
            model.setQuartzId((Long) data.get(Constant.QUARTZ_ID_STR));
            //执行命令行
            CountDownLatch latch = new CountDownLatch(1);
            Integer[] tempInteger = new Integer[1];
            runShell((String) data.get(Constant.INVOKE_PARAM_STR),resultLog,latch,tempInteger);
            //如果过了3个小时后，还没执行完，则直接返回
            latch.await(3, TimeUnit.HOURS);
            exitValue=tempInteger[0];
            if(exitValue == null) {
                throw new QuartzException("过了3小时后，命令还没执行完！");
            }
        } catch (Exception e) {
            logger.error("执行shell任务出错！", e);
            resultLog.append("\n" + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("执行shell任务出错！", e);
                    resultLog.append("\n" + e.getMessage());
                }
            }
        }

        if (null == exitValue || 0 != exitValue) {
            exitValue=-1000;
            resultLog.append("command exit value(" + exitValue + ") is failed");
        } else {
            model.setExecResult(1);
        }
        commonJobService.addResult(oldTime, resultLog.toString(), model, logger);
    }

    /**
     * 运行命令
     * @param shStr 命令
     * @return  运行结果
     **/
    @Async
    public Integer[] runShell(String shStr,StringBuffer result,CountDownLatch latch,Integer[] results) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        Process process;
        if(os.startsWith(Constant.OS_NAME)){
            process = Runtime.getRuntime().exec("cmd /c "+shStr);
        }else{
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr});
        }
        process.waitFor();
        BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = read.readLine()) != null) {
            result.append(line);
        }
        results[0] = process.exitValue();
        latch.countDown();
        return results;
    }

}