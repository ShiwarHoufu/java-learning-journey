package com.uestc.controller;

import com.uestc.pojo.JobOption;
import com.uestc.pojo.Result;
import com.uestc.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /*
    * 获取岗位人数统计数据
    * */
    @GetMapping("/empJobData")
    public Result getJobData(){
        log.info("获取岗位人数数据");
        JobOption jobOption = reportService.getJobData();
        return Result.success(jobOption);
    }

    /*
    * 获取员工性别统计数据
    * */
    @GetMapping("/empGenderData")
    public Result getGenderData(){
        log.info("获取员工性别统计数据");
        List<Map<String, Object>> genderList = reportService.getGenderData();
        return Result.success(genderList);
    }
}
