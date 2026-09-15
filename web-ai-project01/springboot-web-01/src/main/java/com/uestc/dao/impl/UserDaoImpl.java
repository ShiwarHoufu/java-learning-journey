package com.uestc.dao.impl;

import cn.hutool.core.io.IoUtil;
import com.uestc.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository // 把实现类交给IOC管理，作为Bean存在
public class UserDaoImpl implements UserDao {

    @Override
    public List<String> findAll() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/user.txt");
        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());
        return lines;
    }
}
