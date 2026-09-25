package com.uestc.mapper;

import com.uestc.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 员工经历
* */
@Mapper
public interface EmpExprMapper {

    //批量插入员工工作经历
    void insertBatch(List<EmpExpr> exprList);

    //根据员工id批量删除工作经历（动态sql，见EmpExprMapper.xml）
    void deleteByEmpIds(@Param("empIds") List<Integer> empIds);
}
