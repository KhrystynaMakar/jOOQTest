package com.springapp.mvc.service;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class DSLContextProviderTest {

    @Autowired
    private DataSource fakeDataSource;

    private DSLContextProvider dslContextProvider;

    @Test
    public void testInitializedDatabase() throws Exception {
        dslContextProvider = new DSLContextProvider(fakeDataSource);
        Assert.assertEquals(null, dslContextProvider.initializedDatabase());
    }
}
