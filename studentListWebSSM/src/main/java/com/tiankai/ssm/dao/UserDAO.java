package com.tiankai.ssm.dao;

import com.tiankai.ssm.bean.User;

/**
 * 定义了关于用户账号信息的DAO
 *
 * @author: xutiankai
 * @date: 8/1/2021 9:31 AM
 * @deprecated 整合了mybatis, 使用映射xxxMapper, 不再用xxxDAO
 */
@Deprecated
public interface UserDAO {
    /**
     * 向数据库中添加用户账号信息
     *
     * @param user
     * @return 受影响的行数
     */
    int addUser(User user);

    /**
     * 根据用户名查询用户，并将查询结果封装成User对象后返回
     *
     * @param username
     * @return
     */
    User queryUserByUsername(String username);
}
