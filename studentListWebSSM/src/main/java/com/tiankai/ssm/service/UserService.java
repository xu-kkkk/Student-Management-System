package com.tiankai.ssm.service;

import com.tiankai.ssm.bean.User;

/**
 * @author: xutiankai
 * @date: 8/1/2021 9:38 AM
 */
public interface UserService {
    /**
     * 添加一个用户信息
     *
     * @param user User对象的实例
     */
    void addUser(User user);

    /**
     * 根据用户名username找到对应的User对象并返回
     *
     * @param username
     * @return User对象
     */
    User queryUserByUsername(String username);

    /**
     * 判断用户名username是否存在
     * @param username
     * @return true: 用户名存在; false: 用户名不存在。
     */
    boolean existUsername(String username);
}
