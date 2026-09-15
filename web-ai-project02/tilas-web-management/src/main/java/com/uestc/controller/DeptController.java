package com.uestc.controller;

import com.uestc.pojo.Dept;
import com.uestc.pojo.Result;
import com.uestc.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    //@RequestMapping("/depts")  //处理所有请求方法
    @GetMapping("/depts")     //只处理GET请求
    public Result list() {
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

/*
    //方式一：使用HttpServletRequest获取请求参数
    @DeleteMapping("/delete")
    public Result delete(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        deptService.deleteById(id);
        return Result.success();
    }
*/


/*
    方式二：使用注解@RequestParam
    注意: @RequestParam注解通过 required 属性可以指定参数的名称和是否必填，默认是必填的
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(value = "id", required = false) Integer id) {
        deptService.deleteById(id);
        return Result.success();
    }
*/

    //方式三：省略@RequestParam注解的参数名称
    //前端传递的参数名称必须和方法参数名称一致
    @DeleteMapping("/depts")
    public Result delete(Integer id) {
        deptService.deleteById(id);
        return Result.success();
    }
}
