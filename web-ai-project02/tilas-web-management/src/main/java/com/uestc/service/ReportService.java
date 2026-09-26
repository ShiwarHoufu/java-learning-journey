package com.uestc.service;

import com.uestc.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {

    JobOption getJobData();

    List<Map<String, Object>> getGenderData();
}
