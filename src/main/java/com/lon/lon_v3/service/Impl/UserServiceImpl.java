package com.lon.lon_v3.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.lon_v3.entity.User;
import com.lon.lon_v3.mapper.UserMapper;
import com.lon.lon_v3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.service.Impl
 * @className: UserServiceImpl
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/9 14:48
 * @version: 1.0
 */

@Service
//@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    public UserMapper userMapper;

    Page page =new Page<>();
    @Override
    public Page<User> findUserByName(String name) {
        return userMapper.findUserByName(page.setSize(20).setCurrent(1),name);
    }

    @Override
    public Page<User> findUsersByName(String name) {

        QueryWrapper queryWrapper=new QueryWrapper<>().like("name",name);

        Page user = userMapper.selectPage(page.setSize(20).setCurrent(1),queryWrapper);
        return user;
    }

    @Override
    public Page<User> findUserByUno(Page page, String uno) {


        return userMapper.findUserByUno(page,uno);
    }

    @Override
    public User findUserRankByName(String uno) {
        return userMapper.findUserRankByName(uno);
    }

    @Override
    public void dynamicsInsert(String sql) {

    }

    @Override
    public void dynamicsUpdate(String sql) {

    }

    @Override
    public void dynamicsDelete(String sql) {

    }
}
