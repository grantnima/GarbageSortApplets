package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 打赏人信息
 */
public class RewardedDetail {

    private Long id;

    @JSONField(name = "user_id")
    private String userId;

    private String name;

    private String phone;

    private String address;

    private String email;

    @JSONField(name = "create_time")
    private Long createTime;

    /**
     * 打赏次数
     */
    private Integer count;

    @JSONField(name = "last_reward_time")
    private Long lastRewardTime;

    public Long getLastRewardTime() {
        return lastRewardTime;
    }

    public void setLastRewardTime(Long lastRewardTime) {
        this.lastRewardTime = lastRewardTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
