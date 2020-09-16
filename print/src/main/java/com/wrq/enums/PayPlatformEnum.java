package com.wrq.enums;

/**
 * Created by wangqian on 2019/4/8.
 */
public enum PayPlatformEnum {

        ALIPAY(0,"支付宝");
        private  String value;
        private  int code;
        PayPlatformEnum(Integer code,String value){
            this.code   = code;
            this.value  = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }

}
