package com.wrq.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by wangqian on 2019/6/14.
 */
@Data
public class CommentForm {

    @NotEmpty(message = "评论内容不可为空")
    String content;

    Integer parentId;

    @NotNull(message = "评论目标需要确认")
    Integer targetId;

}
