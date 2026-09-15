package com.uestc.mapper;

import com.uestc.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
//该接口的实现类在MyBatis运行时自动创建，并交由Spring容器管理
public interface UserMapper {

    //@Select("select * from user;")
    public List<User> findAll();

    @Delete("delete from user where id = #{id}")
    public void deleteById(Integer id);
    //#{}是占位符，会用 ？替换，生成预编译的sql语句
    //${}是字符串拼接符号，将参数值直接拼接在sql中

    @Insert("insert into user (username, password, age,sex, mail) values (#{username}, #{password}, #{age}, #{sex}, #{mail})")
    public void insert(User user);               //填对象属性名，而不是数据库表字段名

    @Update("update user set username = #{username}, password = #{password}, age = #{age}, sex = #{sex}, mail = #{mail} where id = #{id}")
    public void update(User user);

    //根据用户名和密码查询用户
    @Select("select * from user where username = #{username} and password = #{password}")
    public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    //java编译后，默认会丢失方法参数名（.class文件里看不到 username，只能看到参数位置）
    //程序跑起来，调用方法，MyBatis读取@Param注解信息，处理 SQL 占位符，发给数据库执行 SQL
}
