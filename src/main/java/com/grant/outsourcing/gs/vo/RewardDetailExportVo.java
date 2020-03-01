package com.grant.outsourcing.gs.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class RewardDetailExportVo {

    @Excel(name = "收件人", isImportField = "true")
    private String name;
    @Excel(name = "电话", isImportField = "true")
    private String phone;
    @Excel(name = "详细地址", isImportField = "true")
    private String address;
    @Excel(name = "邮箱", isImportField = "true")
    private String email;
    @Excel(name = "打赏次数", isImportField = "true")
    private Integer count;
    @Excel(name = "首次打赏时间", format = "yyyy-MM-dd HH:mm:ss", width = 24)
    private Date firstRewardTime;
    @Excel(name = "最近打赏时间", format = "yyyy-MM-dd HH:mm:ss", width = 24)
    private Date lastRewardTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getFirstRewardTime() {
        return firstRewardTime;
    }

    public void setFirstRewardTime(Date firstRewardTime) {
        this.firstRewardTime = firstRewardTime;
    }

    public Date getLastRewardTime() {
        return lastRewardTime;
    }

    public void setLastRewardTime(Date lastRewardTime) {
        this.lastRewardTime = lastRewardTime;
    }
}
