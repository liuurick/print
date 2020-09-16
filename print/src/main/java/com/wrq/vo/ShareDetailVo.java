package com.wrq.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by wangqian on 2019/4/24.
 */
@Data
public class ShareDetailVo {

    private String fileTypeImg;

    private String fileType;

    private String title;

    private String desc;

    private Integer fileId;

    private Integer shareId;

    private Integer downloadNum;

    private Integer integral;

    private Integer pageNum;

    private String username;

    private Date createTime;

    private String tagName;

    private String content;

    private String myIntegral;

}
