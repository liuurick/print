package com.wrq.pojo;

import java.util.Date;

public class Share {
    private Integer id;

    private String title;

    private String description;

    private Integer userId;

    private Integer fileId;

    private Integer isDelete;

    private String tag;

    private String viewNum;

    private Integer downloadNum;

    private Integer isHot;

    private Integer isTop;

    private String content;

    private Date createTime;

    private Date updateTime;

    public Share(Integer id, String title, String description, Integer userId, Integer fileId, Integer isDelete, String tag, String viewNum, Integer downloadNum, Integer isHot, Integer isTop, String content, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.fileId = fileId;
        this.isDelete = isDelete;
        this.tag = tag;
        this.viewNum = viewNum;
        this.downloadNum = downloadNum;
        this.isHot = isHot;
        this.isTop = isTop;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Share() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum == null ? null : viewNum.trim();
    }

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}