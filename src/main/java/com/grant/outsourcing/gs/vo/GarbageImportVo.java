package com.grant.outsourcing.gs.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class GarbageImportVo
{
	@Excel(name = "设备名称（必填）", isImportField = "true")
	private String name;

	@Excel(name = "紧急程度", isImportField = "true",replace = {"可回收垃圾_1","厨余垃圾_2","有害垃圾_3","其他垃圾_4"})
	private Integer sortType;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getSortType()
	{
		return sortType;
	}

	public void setSortType(Integer sortType)
	{
		this.sortType = sortType;
	}
}