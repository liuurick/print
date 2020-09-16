package com.wrq.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by wangqian on 2019/4/23.
 */
@Data
public class ShareListVo {

    private Integer shareId;

    private String title;

    private String desc;

    private String tagName;

    private Integer integral;

    private String viewNum;

    private String username;

    private String createTime;

    private String headImg;

}
