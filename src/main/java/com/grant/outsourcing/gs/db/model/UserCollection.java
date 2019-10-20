package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;

public class UserCollection
{
	private String id;

	@JSONField(name = "garbage_id")
	private Long garbageId;

	@JSONField(name = "user_id")
	private String userId;

	@JSONField(name = "create_time")
	private Long createTime;

	public Long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Long createTime)
	{
		this.createTime = createTime;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Long getGarbageId()
	{
		return garbageId;
	}

	public void setGarbageId(Long garbageId)
	{
		this.garbageId = garbageId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
