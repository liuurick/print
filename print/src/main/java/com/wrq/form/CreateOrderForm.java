package com.wrq.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by wangqian on 2019/4/8.
 */
@Data
public class CreateOrderForm {

    private Integer shopId;

    @NotNull(message = "请上传打印文件后操作")
    private Integer pageCount;

    @NotNull(message = "请填写打印文件份数")
    private Integer fileQuantity;

    @NotNull(message = "请选择单页/双页参数")
    private  Integer singleOrDouble;

    @NotNull(message = "请选择黑白/彩打参数")
    private Integer blackOrColor;

    @NotNull(message = "文件的尺寸参数必填")
    private Integer pageSize;

    private String orderPrice;

    private String userDesc;

    private Integer fileId;

}
