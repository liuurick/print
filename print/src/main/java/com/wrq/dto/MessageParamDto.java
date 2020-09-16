package com.wrq.dto;

import lombok.Data;

/**
 * Created by wangqian on 2019/4/19.
 */
@Data
public class MessageParamDto {

    /* 您的文件打印完毕，请在在{1}前凭取件码{2}，至{3}取件，若有问题请联系店主{4}。 */

    /* 尊敬的用户，您所打印文件{1}被打印店{2}拒绝，原因为：{3}，如果有疑问请联系{4} */

    private Integer templateCode;

    private String phone;

    private String param1;

    private String param2;

    private String param3;

    private String param4;

}
