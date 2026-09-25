package com.uestc.controller;


import com.uestc.pojo.Emp;
import com.uestc.pojo.EmpQueryParam;
import com.uestc.pojo.PageResult;
import com.uestc.pojo.Result;
import com.uestc.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /*
    * 员工分页查询
    * 前端传 page（页码）和 pageSize（每页记录数），
    * 返回 data.total（总条数）和 data.rows（当页员工列表）
    * */
    @GetMapping()
    public Result page(EmpQueryParam param)
    {
        log.info("分页查询员工，param: {}", param);
        PageResult<Emp> pageResult = empService.page(param);
        return Result.success(pageResult);
    }

    /*
    * 新增员工
    * */
    @PostMapping()
    public Result save(@RequestBody Emp emp){
        log.info("新增员工，emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    /*
    * 删除员工
    * */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("根据id批量删除员工:{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    /*
    * 查询回显
    * */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询员工，id: {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /*
    * 修改员工数据
    * */
    @PutMapping()
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工，emp: {}", emp);
        empService.update(emp);
        return Result.success();
    }

}