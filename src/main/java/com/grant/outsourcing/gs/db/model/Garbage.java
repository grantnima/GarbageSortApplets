package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Garbage
{
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 垃圾名
	 */
	private String name;

	/**
	 * 垃圾类别
	 */
	private Integer sort;

	/**
	 * 垃圾首字母
	 */
	@JSONField(name = "capital_letter")
	private String capitalLetter;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}

	public String getCapitalLetter()
	{
		return capitalLetter;
	}

	public void setCapitalLetter(String capitalLetter)
	{
		this.capitalLetter = capitalLetter;
	}
}
