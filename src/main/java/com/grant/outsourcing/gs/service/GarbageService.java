package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import com.grant.outsourcing.gs.db.model.Garbage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class GarbageService
{
	@Resource private GarbageMapper garbageMapper;

	public void save (Garbage garbage){
		garbageMapper.save(garbage);
	}
}
