package com.uestc;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class JdbcTest {
    
    //使用静态sql语句。DML语句
    @Test
    public void testUpdate() throws Exception {
        //加载，注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //获取数据库连接
        String url = "jdbc:mysql://localhost:3307/db01";
        String username = "root";
        String password = "你的数据库密码";  // TODO 换成自己的密码再运行，此处不提交真实密码
        Connection connection = DriverManager.getConnection(url, username, password);

        //获取执行sql语句对象
        Statement statement = connection.createStatement();

        //执行sql语句
        int i = statement.executeUpdate("update user set age = 18 where id = 1");
        System.out.println("更新的行数为：" + i);

        //释放资源
        statement.close();
        connection.close();
    }
    
    //使用预编译sql语句。DQL语句
    //优势：性能更高，防止sql注入攻击
    @Test
    public void testQuery() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3307/db01";
        String username = "root";
        String password = "你的数据库密码";  // TODO 换成自己的密码再运行，此处不提交真实密码
        Connection connection = DriverManager.getConnection(url, username, password);

        String sql = "select id, username, password, age, mail from user where age = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 18);  //为第一个占位符设置值

        ResultSet resultSet = preparedStatement.executeQuery();

        User user = null;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));
            user.setMail(resultSet.getString("mail"));

            System.out.println(user);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}