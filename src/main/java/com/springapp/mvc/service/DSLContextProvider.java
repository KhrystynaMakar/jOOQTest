package com.springapp.mvc.service;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class DSLContextProvider {
    @Autowired
    private DataSource dataSource;

    public static final Logger logger = LoggerFactory.getLogger(DSLContextProvider.class);

    public DSLContext initializedDatabase() {
        try {
            return DSL.using(dataSource.getConnection(), SQLDialect.MYSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("There are some database connection exception", e);
            return null;
        }
    }

    public DSLContextProvider (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DSLContextProvider() {
    }
}
