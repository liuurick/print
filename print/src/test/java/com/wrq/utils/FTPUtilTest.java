package com.wrq.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

/**
 * Created by wangqian on 2019/4/12.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FTPUtilTest {

    @Autowired
    private FTPUtil ftpUtil;

    @Test
    public void testDownLoadFile() throws Exception {


    }
}