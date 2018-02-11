package me.mvcapp.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import me.mvcapp.db.JdbcUtils;

/**
 * 封装了基本的CRUD方法，以供子类继承使用
 * 当前DAO没有事物，直接在方法中获取数据库连接
 *
 * @param <T>: 当前DAO处理的实体类的类型是什么
 * @author Administrator
 */
public class DAO<T> {

    private Class<T> clazz = null;

    private QueryRunner queryRunner = new QueryRunner();

    @SuppressWarnings("unchecked")
    public DAO() {
        Type superClass = getClass().getGenericSuperclass();

        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    clazz = (Class<T>) typeArgs[0];
                }
            }
        }
    }

    /**
     * 返回某一个字段的值，或者返回数据表中要多少条记录等等
     */
    @SuppressWarnings("unchecked")
    protected <E> E getForValue(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConn(connection);
        }
        return null;
    }

    /**
     * 返回T所对应的List
     */
    protected List<T> getForList(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConn(connection);
        }
        return null;
    }

    /**
     * 返回对应的T的实体类对象
     */
    protected T get(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConn(connection);
        }
        return null;
    }

    /**
     * 该方法封装了INSERT、UPDATE、DELETE操作
     *
     * @param sql:SQL语句
     * @param args:填充SQL语句的占位符
     */
    protected void update(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            queryRunner.update(connection, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConn(connection);
        }
    }
}
