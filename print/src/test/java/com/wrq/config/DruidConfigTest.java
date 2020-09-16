package com.wrq.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by wangqian on 2019/3/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidConfigTest {


    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {

        /**
         * org.apache.tomcat.jdbc.pool.DataSource
         */
        System.out.println(dataSource);

        Connection connection = dataSource.getConnection();

        System.out.println(connection);

        connection.close();

    }

}