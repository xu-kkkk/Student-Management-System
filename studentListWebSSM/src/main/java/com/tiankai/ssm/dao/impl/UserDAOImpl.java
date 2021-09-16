package com.tiankai.ssm.dao.impl;

import com.tiankai.ssm.dao.UserDAO;
import com.tiankai.ssm.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 实现了UserDao中定义的方法
 * Spring自动注入JdbcTemplate，移除了对BaseDao的依赖，不再需要继承BaseDao
 *
 * @author: xutiankai
 * @date: 8/1/2021 9:33 AM
 */
@Deprecated
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate; // 自动注入

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO t_user(`username`, `password`, `email`) VALUES(?, ?, ?);";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Override
    public User queryUserByUsername(String username) {
        String sql = "SELECT `id`, `username`, `password`, `email` FROM t_user WHERE `username`= ?;";
//        return queryForOne(sql, User.class, username);

        // 使用queryForMap方法或queryForObject方法查询数据库时，
        // 如果结果集为空，则会抛出"EmptyResultDataAccessException"异常。
        // 如果结果集数量多于1个，则会抛出"IncorrectResultSizeDataAccessException"异常。
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
    }
}
