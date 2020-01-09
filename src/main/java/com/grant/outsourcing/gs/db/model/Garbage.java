package com.grant.outsourcing.gs.db.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.grant.outsourcing.gs.annotation.DataBaseIgnore;

public class Garbage
{
	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

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

	@DataBaseIgnore
	private Boolean collected;

	public Boolean getCollected() {
		return collected;
	}

	public void setCollected(Boolean collected) {
		this.collected = collected;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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
