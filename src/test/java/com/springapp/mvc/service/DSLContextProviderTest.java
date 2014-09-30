package com.springapp.mvc.service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class DSLContextProviderTest {

    private DSLContextProvider dslContextProvider;

    @Test
    public void testInitializedDatabase() throws Exception {
        DataSource ds = new MysqlDataSource();
        dslContextProvider = new DSLContextProvider(ds);
        Assert.assertEquals(null, dslContextProvider.initializedDatabase());
    }
}
