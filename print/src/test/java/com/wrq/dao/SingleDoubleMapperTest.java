package com.wrq.dao;

import com.wrq.pojo.SingleDouble;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by wangqian on 2019/3/31.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SingleDoubleMapperTest {

    @Autowired
    private SingleDoubleMapper singleDoubleMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {
    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testInsertSelective() throws Exception {

    }

    @Test
    public void testSelectByPrimaryKey() throws Exception {
        SingleDouble singleDouble = singleDoubleMapper.selectByPrimaryKey(1);
        log.info("【结果】 = {}",singleDouble.toString());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {

    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {

    }

    @Test
    public void testSelectSingleOrDoubleByShopId() throws Exception {

    }
}