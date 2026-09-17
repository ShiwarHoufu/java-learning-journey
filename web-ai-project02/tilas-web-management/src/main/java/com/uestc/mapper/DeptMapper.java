package com.uestc.mapper;

import com.uestc.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

/*  结果封装

    方式一：手动映射
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
*/

    //方式二：起别名
    //@Select("select id,name,create_time createTime,update_time updateTime from dept order by update_time desc")

    //方式三：开启驼峰命名射射
    @Select("select id,name,create_time ,update_time from dept order by update_time desc")
    List<Dept> findAll();

    //删除部门
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    //新增部门
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    //查询回显
    @Select("select id,name,create_time,update_time from dept where id = #{id}")
    Dept selectById(Integer id);

    //修改部门
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
