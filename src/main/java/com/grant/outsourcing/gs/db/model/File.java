package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;

public class File {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件名
     */
    @JSONField(name = "file_name")
    private String fileName;

    /**
     * 文件url
     */
    @JSONField(name = "file_url")
    private String fileUrl;

    /**
     * 创建时间
     */
    @JSONField(name = "create_time")
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
