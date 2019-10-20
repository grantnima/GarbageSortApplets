package com.grant.outsourcing.gs.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class ExamQuestionVo
{
	@JSONField(name = "garbage_id")
	private Long garbageId;

	private Boolean result;

	private Integer chosen;

	public Long getGarbageId()
	{
		return garbageId;
	}

	public void setGarbageId(Long garbageId)
	{
		this.garbageId = garbageId;
	}

	public Boolean getResult()
	{
		return result;
	}

	public void setResult(Boolean result)
	{
		this.result = result;
	}

	public Integer getChosen()
	{
		return chosen;
	}

	public void setChosen(Integer chosen)
	{
		this.chosen = chosen;
	}
}
