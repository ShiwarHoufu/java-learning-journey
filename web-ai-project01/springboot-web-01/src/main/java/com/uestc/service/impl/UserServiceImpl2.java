package com.uestc.service.impl;

import com.uestc.dao.UserDao;
import com.uestc.entity.User;
import com.uestc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service // 把实现类交给IOC管理，作为Bean存在
public class UserServiceImpl2 implements UserService {

//    private UserDao userDao = new UserDaoImpl();
    @Autowired // 自动注入Dao实现类,不用new对象
    private UserDao userDao;
    
    @Override
    public List<User> findAll() {
        //调用Dao获取数据
        List<String> lines = userDao.findAll();

        List<User> userList = lines.stream().map(line -> {
            String[] parts = line.split(",");

            Integer id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            Integer age = Integer.parseInt(parts[4]);
            LocalDateTime updateTime = LocalDateTime.parse(parts[5]);

            return new User(id, username, password, name, age, updateTime);
        }).toList();

        return userList;
    }
}
