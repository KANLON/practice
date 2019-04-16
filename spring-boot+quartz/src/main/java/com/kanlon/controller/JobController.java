package com.kanlon.controller;

import com.kanlon.common.DateTimeFormat;
import com.kanlon.model.*;
import com.kanlon.service.AppQuartzService;
import com.kanlon.service.JobUtil;
import com.kanlon.service.QuartzResultService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.util.*;

/**
 * job的controller
 *
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@RequestMapping("/")
@RestController
public class JobController {

    @Autowired
    private JobUtil jobUtil;

    @Autowired
    private AppQuartzService appQuartzService;

    @Autowired
    private QuartzResultService quartzResultService;

    private Logger logger = LoggerFactory.getLogger(JobController.class);


    /**
     * 从自己的数据库表获取所有任务，包含任务id
     *
     * @return com.kanlon.model.CommonResponse
     **/
    @GetMapping("/allJobs")
    public CommonResponse getAllTaskFromMyTable(PageModel page) {
        PageDatasModel model = new PageDatasModel(page);
        model.setDatas(appQuartzService.getAllTaskFromMyTable(page));
        logger.info("获取全部任务成功");
        return CommonResponse.succeedResult(model);
    }

    /**
     * 添加一个job
     *
     * @param appQuartz 任务信息
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value = "/addJob")
    public CommonResponse addJob(@Valid @RequestBody AppQuartz appQuartz) throws Exception {
        Date date = new Date();
        appQuartz.setCtime(date);
        appQuartz.setMtime(date);
        appQuartzService.insertAppQuartzSer(appQuartz);
        return CommonResponse.succeedResult();
    }


    /**
     * 更新,修改
     *
     * @param appQuartz 新的任务信息
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value = "/updateJob")
    public CommonResponse modifyJob(@Valid @RequestBody AppQuartz appQuartz) throws SchedulerException, ParseException {
        if(appQuartz.getQuartzId()==null){
            return CommonResponse.failedResult("更新的任务id不能为null",-1);
        }
        appQuartz.setMtime(new Date());
        appQuartzService.updateAppQuartzSer(appQuartz);
        return CommonResponse.succeedResult();
    }

    /**
     * 删除job
     *
     * @param quartzIds 请求参数id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value = "/deleteJob")
    public CommonResponse deleteJob(@Valid @NotEmpty @RequestBody Long[] quartzIds) throws SchedulerException {
        for (Long quartzId : quartzIds) {
            appQuartzService.deleteAppQuartzByIdSer(quartzId);
        }
        logger.info("要删除的任务id为："+ Arrays.toString(quartzIds));
        return CommonResponse.succeedResult();
    }

    /**
     * 根据任务id暂停job
     *
     * @param quartzIds 任务id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value = "/pauseJob")
    public CommonResponse pauseJob(@Valid @NotEmpty @RequestBody Long[] quartzIds) throws Exception {
        for (Long quartzId : quartzIds) {
            AppQuartz appQuartz = appQuartzService.selectAppQuartzByIdSer(quartzId);
            jobUtil.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());
        }
        return CommonResponse.succeedResult();
    }

    /**
     * 根据id恢复job
     * @param quartzIds 任务id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value = "/resumeJob")
    public CommonResponse resumeJob(@Valid @NotEmpty @RequestBody Long[] quartzIds) throws Exception {
        for (Long quartzId : quartzIds) {
            AppQuartz appQuartz = appQuartzService.selectAppQuartzByIdSer(quartzId);
            jobUtil.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());
        }
        return CommonResponse.succeedResult();
    }

    /**
     * 暂停所有
     *
     * @return com.kanlon.model.ReturnMsg
     **/
    @GetMapping(value = "/pauseAll")
    public CommonResponse pauseAllJob() throws Exception {
        jobUtil.pauseAllJob();
        return CommonResponse.succeedResult();
    }

    /**
     * 恢复所有
     *
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value = "/resumeAll", method = RequestMethod.GET)
    public CommonResponse resumeAllJob() throws Exception {
        jobUtil.resumeAllJob();
        return CommonResponse.succeedResult();
    }


    /**
     * 获取所有任务
     *
     * @return com.kanlon.model.ReturnMsg
     **/
    @GetMapping("/tasks")
    public CommonResponse getAllTask() throws SchedulerException {
        return jobUtil.getAllTask();
    }


    /**
     * 根据任务id获取任务执行记录
     * @param quartzId 任务id
     * @param pageModel 分页参数
     * @return com.kanlon.model.CommonResponse
     **/
    @GetMapping("/job/results")
    public CommonResponse getQuartzResultByQuartzId(@RequestParam("quartzId") Long quartzId,
            @RequestParam(value = "st",required = false) String st,
            @RequestParam(value = "et",required = false) String et,
            @RequestParam(value = "execResult",required = false) Integer execResult,PageModel pageModel){
        if(st==null){
            st="2000-10-10";
        }
        if(et==null){
            et="2042-10-10";
        }
        //如果不传执行结果，则查询啊所有执行日志结果
        if(execResult==null){
            return CommonResponse.succeedResult(quartzResultService.getQuartzResultByQuartzId(quartzId,st,et,pageModel));
        }else{
            return CommonResponse.succeedResult(quartzResultService.getQuartzResultByQuartzIdAndResult(quartzId,st,et,execResult,pageModel));
        }

    }


}