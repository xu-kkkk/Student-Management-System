package com.tiankai.ssm.service.impl;

import com.tiankai.ssm.mapper.UserMapper;
import com.tiankai.ssm.service.UserService;
import com.tiankai.ssm.bean.User;
import com.tiankai.ssm.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: xutiankai
 * @date: 8/1/2021 9:39 AM
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    //    private final UserDAO userDAO = new UserDAOImpl();
//    @Autowired
//    private UserDAO userDAO;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public User queryUserByUsername(String username) {
        User user = null;
        try {
            user = userMapper.queryUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("未查询到user信息");
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean existUsername(String username) {
        User user = null;
        try {
            user = userMapper.queryUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("未查询到user信息");
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
        }

        return user != null;
    }
}
