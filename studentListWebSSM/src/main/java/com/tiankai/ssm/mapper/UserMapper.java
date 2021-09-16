package com.tiankai.ssm.mapper;

import com.tiankai.ssm.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: xutiankai
 * @date: 9/2/2021 7:51 PM
 */
@Repository
public interface UserMapper {
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
    User queryUserByUsername(@Param("username") String username);
}
