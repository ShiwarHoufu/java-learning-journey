package com.uestc.mapper;

import com.uestc.pojo.Emp;
import com.uestc.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmpMapper {

    /*
    * 分页查询的原始方法
    /*
    //查询员工总记录数
    @Select("select count(*) from emp ")
    Long countAll();

    //分页查询员工数据，左外连接dept表带出部门名称
    //d.name起别名dept_name，经驼峰映射填充到Emp.deptName
    @Select("select e.*, d.name dept_name from emp e left join dept d on e.dept_id = d.id " +
            " order by e.update_time desc limit #{index}, #{pageSize}")
    List<Emp> page(@Param("index") Integer index, @Param("pageSize") Integer pageSize);
    */


    /*
    * 使用 PageHelper 分页插件
    */
    //@Select("select e.*, d.name dept_name from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
    List<Emp> page(EmpQueryParam param);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp (username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp); //因为这里传的是Emp对象，注意上方 '#{}' 内要和Emp类的属性名一致，所以用驼峰命名
}
