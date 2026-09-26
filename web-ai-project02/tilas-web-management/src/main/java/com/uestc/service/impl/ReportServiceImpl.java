package com.uestc.service.impl;

import com.uestc.mapper.EmpMapper;
import com.uestc.pojo.JobCount;
import com.uestc.pojo.JobOption;
import com.uestc.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getJobData() {
        //1. 查询各职位人数，一行一个职位
        List<JobCount> rows = empMapper.countByJob();

        //2. 拆成两个平行的list：jobList[i] 和 dataList[i] 说的是同一个职位
        List<String> jobList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();
        for (JobCount row : rows) {
            jobList.add(row.getJobName());
            dataList.add(row.getNum());
        }

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getGenderData() {
        //1. 查询男女人数
        List<Map<String, Object>> genderList = empMapper.countByGender();
        return genderList;
    }
}
