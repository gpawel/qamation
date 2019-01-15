package org.qamation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtils {
    private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
    private String dbUrl;
    private String userName;
    private String passWord;

    private Connection connection;

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
    }

    public ResultSet select(String selectQuery) {
        Statement stmnt;
        try {
            stmnt = connection.createStatement();

        } catch (SQLException e) {
            String message = "Unable to create a statement.";
            log.error(message);
            e.printStackTrace();
            throw new RuntimeException(message,e);
        }

        try {
            ResultSet result = stmnt.executeQuery(selectQuery);
            return result;
        } catch (SQLException e) {
            String message = "Unable to execute selectQuery\n"+selectQuery;
            log.error(message);
            e.printStackTrace();
            throw new RuntimeException(message,e);
        }
    }


}
