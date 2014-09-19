package com.springapp.mvc.service;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class DSLContextProvider {
    @Autowired
    private DataSource dataSource;

    public DSLContext initializedDatabase() {
        try {
            return DSL.using(dataSource.getConnection(), SQLDialect.MYSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There are some database connection exception");
            return null;
        }
    }
}
