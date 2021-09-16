package com.tiankai.ssm.dao.impl;

import com.tiankai.ssm.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 提供了向数据库增删改查的方法
 * 需要引入commons-dbutils包
 * 为了实现回滚，在这里不再关闭连接，将异常抛出
 *
 * @author: xutiankai
 * @date: 7/30/2021 3:36 PM
 * @deprecated 使用JdbcTemplate后，BaseDao不再推荐使用了。
 */
@Deprecated
public abstract class BaseDAO {
    private final QueryRunner queryRunner = new QueryRunner();

    /**
     * update()方法用于执行增删改操作
     *
     * @param sql
     * @param args
     * @return 受影响的行数。若返回-1，表示执行失败。
     */
    public int update(String sql, Object... args) {
        Connection connection = JdbcUtils.getConnection();
        int affectedLineCount = -1; // 受影响的行数
        try {
            affectedLineCount = queryRunner.update(connection, sql, args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

        return affectedLineCount;
    }

    /**
     * 查询返回一个JavaBean的sql语句
     *
     * @param sql  执行的sql语句
     * @param type 返回的对象类型
     * @param args sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return 返回一个JavaBean
     */
    public <T> T queryForOne(String sql, Class<T> type, Object... args) {
        Connection connection = JdbcUtils.getConnection();
        T queryResult = null;
        try {
            queryResult = queryRunner.query(connection, sql, new BeanHandler<T>(type), args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

        return queryResult;
    }

    /**
     * 查询返回多个JavaBean的sql语句
     *
     * @param sql  执行的sql语句
     * @param type 返回的对象类型
     * @param args sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return 以List<T>的形式返回多个JavaBean
     */
    public <T> List<T> queryForList(String sql, Class<T> type, Object... args) {
        Connection connection = JdbcUtils.getConnection();
        List<T> queryResultList = null;
        try {
            queryResultList = queryRunner.query(connection, sql, new BeanListHandler<T>(type), args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

        return queryResultList;
    }

    /**
     * 执行返回一行一列的sql语句
     *
     * @param sql  sql语句
     * @param args sql语句中的参数
     * @return 返回单个查询结果
     */
    public Object queryForSingleValue(String sql, Object... args) {
        Connection connection = JdbcUtils.getConnection();
        Object queryValue = null;
        try {
            queryValue = queryRunner.query(connection, sql, new ScalarHandler<>(), args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }

        return queryValue;
    }
}
