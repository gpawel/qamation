package org.qamation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtils {
    private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
    private String dbUrl;
    private String userName;
    private String passWord;

    private Connection connection = null;
    private Statement statement = null;
    public DBUtils (String url, String name, String pass) {
        dbUrl = url;
        userName = name;
        passWord = pass;

        try {
            connection = DriverManager.getConnection(dbUrl,userName,passWord);
        } catch (SQLException e) {
            String message = "Unable to create db connection to "+dbUrl+"\n"+e.toString();
            log.error(message);
            e.printStackTrace();
            throw new RuntimeException(message);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            String message = "Unable to create a statement.";
            log.error(message);
            e.printStackTrace();
            throw new RuntimeException(message,e);
        }
    }

    public ResultSet select(String selectQuery) {
        return select(selectQuery,100);
    }

    public ResultSet select(String selectQuery, int fetch) {
        if (statement == null) throw new RuntimeException("Statement object for this connection is null");
        try {
            statement.setFetchSize(fetch);
            ResultSet result = statement.executeQuery(selectQuery);
            return result;
        } catch (SQLException e) {
            String message = "Unable to execute selectQuery\n"+selectQuery;
            log.error(message);
            e.printStackTrace();
            throw new RuntimeException(message,e);
        }
    }

    public void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            log.error("Could not close db connection to "+dbUrl);
            e.printStackTrace();
        }
    }

    private final class ShutDownHook extends Thread {
        private DBUtils dbUtils;

        ShutDownHook(DBUtils db) {
            this.dbUtils = db;
        }

        @Override
        public void run() {
            dbUtils.close();
        }
    }






}
