package com.uestc;

import com.uestc.mapper.UserMapper;
import com.uestc.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//SpringBoot 集成测试注解:当前测试类中的测试方法执行时，会自动加载SpringBoot应用的上下文
class SpringbootMybatisQuickstartApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> users = userMapper.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void testDeleteById() {
        userMapper.deleteById(1);
    }

    @Test
    public void testInsert() {
        User user = new User(null, "paris", "123456", 40,"男", "paris@example.com");
        userMapper.insert(user);
    }

    @Test
    public void testUpdate() {
        User user = new User(2, "lorn", "123456", 38,"男", "lorn@example.com");
        userMapper.update(user);
    }

    @Test
    public void testFindByUsernameAndPassword() {
        User user = userMapper.findByUsernameAndPassword("lorn", "123456");
        System.out.println(user);
    }

}
