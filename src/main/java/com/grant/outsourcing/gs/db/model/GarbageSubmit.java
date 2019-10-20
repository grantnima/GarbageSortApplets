package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;

public class GarbageSubmit
{
	private Long id;

	@JSONField(name = "user_id")
	private String userId;

	private String garbageName;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getGarbageName()
	{
		return garbageName;
	}

	public void setGarbageName(String garbageName)
	{
		this.garbageName = garbageName;
	}
}
