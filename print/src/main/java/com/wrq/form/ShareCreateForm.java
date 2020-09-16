package com.wrq.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


/**
 * Created by wangqian on 2019/4/22.
 */
@Data
public class ShareCreateForm {

    @NotEmpty(message = "请填写标题后再进行提交")
    private String title;

    @NotEmpty(message = "请填写描述后再进行提交")
    private String desc;

    @NotEmpty(message = "请填写详细内容后再进行提交")
    private  String richText;

    @NotNull(message = "请选择分类后再进行提交")
    private Integer tagValue;

    @NotNull(message = "请上传文件或者选择文件后再进行提交")
    private Integer fileId;

}
