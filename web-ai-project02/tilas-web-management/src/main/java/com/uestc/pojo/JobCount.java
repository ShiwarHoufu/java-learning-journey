package com.uestc.pojo;

import lombok.Data;

/*
* 职位人数统计的查询结果：一个对象对应一行（一个职位 + 该职位的人数）
* */
@Data
public class JobCount {
    private String jobName;
    private Integer num; //该职位的员工人数
}
