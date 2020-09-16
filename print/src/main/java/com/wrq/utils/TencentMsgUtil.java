package com.wrq.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.wrq.dto.MessageParamDto;
import com.wrq.enums.MessageTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by wangqian on 2019/4/19.
 */
@Slf4j
public class TencentMsgUtil {

    /* 短信应用SDK AppID */
    private static int  appid = 55454;

    /* 短信应用SDK AppKey */
    private static String appkey = "xxxxx";

    /* 短信模板ID，需要在短信应用中申请 */
    private static int sendTemplateId = 4444;

    /* 短信模板ID，需要在短信应用中申请 */
    private static int refuseTemplateId = 4444;

    /* 签名 */
    private static String smsSign = "久一软件";


    public static boolean sendMessage (MessageParamDto paramDto) throws Exception {

        /* 您的文件打印完毕，请在在{1}前凭取件码{2}，至{3}取件，若有问题请联系店主{4}。 */

        /* 尊敬的用户，您所打印文件{1}被打印店{2}拒绝，原因为：{3}，如果有疑问请联系{4} */

        String[]  params = new String[]{paramDto.getParam1(), paramDto.getParam2(), paramDto.getParam3(), paramDto.getParam4()};


        int templateId;

        String phone = paramDto.getPhone();

        if ( paramDto.getTemplateCode() == MessageTypeEnum.SEND.getCode() ){

            templateId = sendTemplateId;

        }else {

            templateId = refuseTemplateId;
        }

        try {

            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);

            SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
                    sendTemplateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信

            log.info("[短信发送] 发送结果：{} ", result);

            if ( result.result == 0 ){
                return true;
            }else {
                return  false;
            }

        } catch (HTTPException e) {
            log.error("[信息服务] HTTP响应码错误，错误: {} ", e);
            throw e;
        } catch (JSONException e) {
            log.error("[信息服务] json解析错误，错误: {} ", e);
            throw e;
        } catch (IOException e) {
            log.error("[信息服务] 网络IO错误，错误: {} ", e);
            throw e;
        }
    }

}
