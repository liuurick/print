package com.wrq.exception;


import com.wrq.enums.ResultEnum;

/**
 * Created by wangqian on 2019/2/1.
 */
public class PrintException extends RuntimeException {

    private Integer code;

    public PrintException(ResultEnum resultEnum) {

        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public PrintException(Integer code, String message) {

        super(message);

        this.code = code;
    }
}
