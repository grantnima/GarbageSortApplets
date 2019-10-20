package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.GarbageSubmitMapper;
import com.grant.outsourcing.gs.db.model.GarbageSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class GarbageSubmitService
{
	@Resource private GarbageSubmitMapper garbageSubmitMapper;

	public void save (GarbageSubmit garbageSubmit){
		garbageSubmitMapper.save(garbageSubmit);
	}

	public GarbageSubmit findByUserIdAndGarbageName (String userId, String garbageName){
		return garbageSubmitMapper.findByUserIdAndGarbageName(userId, garbageName);
	}
}
