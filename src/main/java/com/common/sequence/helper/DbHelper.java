package com.common.sequence.helper;

import com.common.sequence.exception.SeqException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/11/17 14:54
 */
public class DbHelper {
    private static final long DELTA = 100000000L;
    private final static String REPLACE_TABLE_NAME = "#tableName";
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `range` (" +
            "id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT," +
            "`value` BIGINT ( 20 ) NOT NULL," +
            "`biz_name` VARCHAR ( 32 ) NOT NULL," +
            "create_time DATETIME NOT NULL," +
            "update_time DATETIME NOT NULL," +
            "PRIMARY KEY ( `id` )," +
            "UNIQUE uk_name ( `biz_name` ))";
    private final static String SELECT_RANGE = "SELECT `value` FROM `#tableName` where biz_name= ? ";
    private final static String INSERT_RANGE = "INSERT INTO `#tableName`(`value`,biz_name,create_time,update_time) VALUES(?,?,?,?)";
    private final static String UPDATE_RANGE = "UPDATE `#tableName` SET `value`= ? ,update_time= ? WHERE  biz_name= ? and value = ? ";

    public static Long selectRange(DataSource dataSource, String tableName, String bizName, long stepStart) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_RANGE.replace(REPLACE_TABLE_NAME, tableName));
            preparedStatement.setString(1, bizName);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                insertRange(dataSource, tableName, bizName, stepStart);
                return null;
            }
            long oldValue = resultSet.getLong(1);
            if (oldValue < 0) {
                throw new SeqException("序列值不能<0, value = " + oldValue + ", 请校验表的序列号 table_name : " + tableName);
            }
            if (oldValue > Long.MAX_VALUE - DELTA) {
                throw new SeqException("序列号溢出, value = " + oldValue + ", 请校验表的序列号 table_name : " + tableName);
            }
            return oldValue;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return null;
    }

    private static void insertRange(DataSource dataSource, String tableName, String bizName, long stepStart) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(INSERT_RANGE.replace(REPLACE_TABLE_NAME, tableName));
            statement.setLong(1, stepStart);
            statement.setString(2, bizName);
            statement.setObject(3, LocalDateTime.now());
            statement.setObject(4, LocalDateTime.now());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            close(statement);
            close(connection);
        }

    }

    static int i = 0;

    public static boolean updateRange(DataSource dataSource, String tableName, String bizName, Long newValue, Long oldValue) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_RANGE.replace(REPLACE_TABLE_NAME, tableName));
            statement.setLong(1, newValue);
            statement.setObject(2, LocalDateTime.now());
            statement.setString(3, bizName);
            statement.setLong(4, oldValue);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }


    public static void createTable(DataSource dataSource, String tableName) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE.replace(REPLACE_TABLE_NAME, tableName));
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private static void close(AutoCloseable statement) {
        if (!Objects.isNull(statement)) {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

}
