package com.uestc.controller;

import cn.hutool.core.io.IoUtil;
import com.uestc.entity.User;
import com.uestc.service.UserService;
import com.uestc.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

//  private UserServiceImpl userService = new UserServiceImpl();
    //1.属性注入
    @Autowired // 自动注入Service实现类,不用new对象
//  @Qualifier("userServiceImpl")         指定注入的Bean为userServiceImpl
//  @Resource(name = "userServiceImpl")   指定注入的Bean为userServiceImpl
    private UserService userService;

    //2.构造函数注入
//  private UserService userService;
//  @Autowired
//  public UserController(UserService userService) {
//      this.userService = userService;
//  }

    //3.setter注入
//  private UserService userService;
//  @Autowired
//  public void setUserService(UserService userService) {
//      this.userService = userService;
//  }

    @GetMapping("/list")
    public List<User> list() {
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/user.txt");
//        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());
//
//        List<User> userList = lines.stream().map(line -> {
//            String[] parts = line.split(",");
//
//            Integer id = Integer.parseInt(parts[0]);
//            String username = parts[1];
//            String password = parts[2];
//            String name = parts[3];
//            Integer age = Integer.parseInt(parts[4]);
//            LocalDateTime updateTime = LocalDateTime.parse(parts[5]);
//
//            return new User(id, username, password, name, age, updateTime);
//        }).toList();

        //调用Service获取数据
        List<User> userList = userService.findAll();

        return userList;
    }
}