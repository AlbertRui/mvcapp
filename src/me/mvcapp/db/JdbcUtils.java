package me.mvcapp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC操作的工具类
 *
 * @author Administrator
 */
public class JdbcUtils {

    /**
     * 静态的只被初始化一次
     */
    private static DataSource dataSource;

    /*
      静态代码块初始化数据源,数据源只能被初始化一次
     */
    static {
        dataSource = null;
        dataSource = new ComboPooledDataSource("mvcapp");
    }

    /**
     * 释放Connection连接
     */
    public static void releaseConn(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回数据源的一个Connection对象
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
