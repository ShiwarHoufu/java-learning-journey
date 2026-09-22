package com.uestc.service;

import com.uestc.pojo.Emp;
import com.uestc.pojo.EmpQueryParam;
import com.uestc.pojo.PageResult;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam param);

    void save(Emp emp);
}