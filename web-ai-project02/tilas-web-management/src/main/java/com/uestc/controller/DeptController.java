package com.uestc.controller;

import com.uestc.pojo.Dept;
import com.uestc.pojo.Result;
import com.uestc.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* @RequestMapping("/depts")
* 提取公共路径前缀，方法上就可以不写这部分了
* */
@Slf4j
@RestController
public class DeptController {

    //使用@Slf4j注解，无需手动创建logger对象
    //private final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    //@RequestMapping("/depts")  //处理所有请求方法
    @GetMapping("/depts")     //只处理GET请求
    public Result list() {
        log.info("查询所有部门");
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
        log.info("删除部门，id: {}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /*
    * 新增部门
    *
    * @RequestBody：将前端传递的JSON字符串转换为指定对象
    * （json键名必须和对象属性名一致）
    * */
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /*
    * 查询回显
    *
    * @PathVariable：获取路径参数
    * */
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询部门信息，id: {}", id);
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }

    /*
    * 修改部门
    * */
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门: {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}

