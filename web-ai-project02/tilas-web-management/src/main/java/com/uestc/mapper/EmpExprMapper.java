package com.uestc.mapper;

import com.uestc.pojo.EmpExpr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
* 员工经历
* */
@Mapper
public interface EmpExprMapper {

    //批量插入员工工作经历
    void insertBatch(List<EmpExpr> exprList);
}
