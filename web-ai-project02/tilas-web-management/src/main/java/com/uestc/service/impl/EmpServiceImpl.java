package com.uestc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uestc.mapper.EmpExprMapper;
import com.uestc.mapper.EmpMapper;
import com.uestc.pojo.*;
import com.uestc.service.EmpLogService;
import com.uestc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam param) {
        /*--------------分页查询原始方法----------------*/
        /*
        //计算分页索引
        int index = (param.getPage() - 1) * param.getPageSize();

        //查询员工总记录数
        Long total = empMapper.countAll();
        //分页查询员工数据
        List<Emp> rows = empMapper.page(index, param.getPageSize());

        return new PageResult<Emp>(total, rows);
        */

        /*--------------使用 PageHelper 分页插件----------------*/
        PageHelper.startPage(param.getPage(), param.getPageSize());

        Page<Emp> p = (Page<Emp>) empMapper.page(param);
        return new PageResult<Emp>(p.getTotal(), p.getResult());
        /*
        * 1. sql语句末尾不允许加分号；
        * 2. pagehelper只会对紧跟在startPage()方法后的第一个select语句生效；
        * */
    }

    @Transactional(rollbackFor = Exception.class) //开启事务；默认运行时异常会回滚事务。
    @Override
    public void save(Emp emp){
        try {
            //1. 保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //2. 保存员工工作经历

            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //设置员工ID
                for(EmpExpr expr : exprList){
                    expr.setEmpId(emp.getId());
                }

                empExprMapper.insertBatch(exprList);
            }
        }
        finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "添加员工" + emp);
            empLogService.insertLog(empLog);
        }

    }
}