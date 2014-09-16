package com.springapp.mvc.service;

import com.springapp.mvc.DatabaseConnection;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DSLContextProvider {
    public DSLContext initializedDatabase() {
        Connection conn = null;
        try {
            String userName = DatabaseConnection.USER;
            String password = DatabaseConnection.PASSWORD;
            String url = DatabaseConnection.URL;
            Class.forName(DatabaseConnection.CONNECTION_DRIVER).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception!: " + e);
        }
        return DSL.using(conn, SQLDialect.MYSQL);
    }
}
