package com.wrq.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wangqian on 2019/5/25.
 */
@Data
public class ShopForm {


    private Integer id;

    @NotEmpty(message = "请填写店铺名后再进行提交")
    private String name;

    @NotEmpty(message = "请填写营业时间后再进行提交")
    private String workTime;

    @NotEmpty(message = "请填写邮箱后再进行提交")
    private String email;

    @NotEmpty(message = "请填写手机号后再进行提交")
    private String phone;

    @NotEmpty(message = "请填写打烊时间后再进行提交")
    private String closeTime;

    @NotEmpty(message = "请填写店铺描述后再进行提交")
    private String desc;

    @NotEmpty(message = "请填写富文本后再进行提交")
    private String richText;

    @NotEmpty(message = "请填写标题后再进行提交")
    private String address;

    @NotEmpty(message = "请上传副图后再进行提交")
    private String miniImgNewName;

    @NotEmpty(message = "请上传主图后再进行提交")
    private String mainImgNewName;

}
