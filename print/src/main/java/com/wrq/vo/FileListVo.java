package com.wrq.vo;

import lombok.Data;

import java.util.Date;

/**
 * 个人中心 我的文件
 * Created by wangqian on 2019/4/12.
 */
@Data
public class FileListVo {

    private Integer fileId;

    private String fileName;

    private String createTime;

    private Integer share;

    private Integer integral;

    private String fileTypeImg;

    private String fileNewName; /* 用来下载 */

}
