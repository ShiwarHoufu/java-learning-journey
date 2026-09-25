package com.uestc.service;

import com.uestc.pojo.Emp;
import com.uestc.pojo.EmpQueryParam;
import com.uestc.pojo.PageResult;

import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam param);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);
}