package com.grant.outsourcing.gs.api.request;

public class PostGarbageRequest
{
	/**
	 * 垃圾名
	 */
	private String name;

	/**
	 * 垃圾类别
	 */
	private Integer sort;

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
}
