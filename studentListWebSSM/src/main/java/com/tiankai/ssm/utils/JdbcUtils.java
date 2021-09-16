package com.tiankai.ssm.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 操纵数据库的工具类
 * 使用了JdbcTemplate后，没必要再使用JdbcUtils工具类了。
 *
 * @author: xutiankai
 * @date: 7/30/2021 3:13 PM
 */
@Deprecated
public class JdbcUtils {
    private static DruidDataSource druidDataSource;

    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * 使用静态代码块初始化
     */
    static {
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
            // 这里强转就行了
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据库连接池中获取连接并返回
     *
     * @return 返回一个数据库连接Connection
     */
    public static Connection getConnection() {
        Connection connection = connections.get();

        if (connection == null) {
            try {
                connection = druidDataSource.getConnection();
                connections.set(connection);

                // 设置手动管理事务
                // TODO: 7/30/2021 目前自动提交事务，需要时再重构这段
//                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    /**
     * 提交事务，并释放连接
     * 要想使用事务的话，要配置TransactionFilter过滤器，web.xml中写<url-pattern>/*</url-pattern>，表示对当前工程下的所有请求进行过滤。
     * 我这个项目中没有这么写，使用了Spring的@Transactional注解，同样能完成这个功能。@Transactional注解建议写在Service层的类上。
     */
    public static void commitAndClose() {
        Connection connection = connections.get();

        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 关闭连接是必须要做的，所以放在finally{}中
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        connections.remove();
    }

    /**
     * 回滚事务，并关闭连接
     */
    public static void rollbackAndClose() {
        Connection connection = connections.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
