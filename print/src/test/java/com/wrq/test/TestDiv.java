package com.wrq.test;

import com.wrq.dto.MessageParamDto;
import com.wrq.enums.MessageTypeEnum;
import com.wrq.utils.TencentMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by wangqian on 2019/4/7.
 */
@Slf4j
public class TestDiv {

    @Test
    public void div (){
        BigDecimal price = new BigDecimal("12.659");
        price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info(" pricepricepricepricepricepricepricepricepricepricepricepricepricepricepricepricepricepricepricepriceprice = {}", price);
    }

    @Test
    public void string (){


        String message = "45151-1515-5.png";

        String orderNo =  message.substring(message.lastIndexOf(".")+1);

        String shopId = message.substring(0, message.lastIndexOf("."));

        log.info("orderNo = {}", orderNo);

        log.info("shopId = {}", shopId);
    }

    @Test
    public void stringTest (){

        MessageParamDto messageParamDto = new MessageParamDto();

        messageParamDto.setTemplateCode(MessageTypeEnum.SEND.getCode());

        messageParamDto.setPhone("11111");


        /* 您的文件打印完毕，请在在{1}前凭取件码{2}，至{3}取件，若有问题请联系店主{4}。 */

        messageParamDto.setParam1("11111");

        messageParamDto.setParam2("11111");

        messageParamDto.setParam3("11111");

        messageParamDto.setParam4("11111");

        String[]  params = new String[]{messageParamDto.getParam1(), messageParamDto.getParam2(), messageParamDto.getParam3(), messageParamDto.getParam4()};


        log.info("获取的参数：{} ", params);

        log.info("获取的参数：{} ", params);
    }








}
