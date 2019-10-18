package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import com.grant.outsourcing.gs.db.model.Garbage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GarbageService
{
	@Resource private GarbageMapper garbageMapper;

	public void save (Garbage garbage){
		garbageMapper.save(garbage);
	}

	public Garbage findByName (String name){
		return garbageMapper.findByName(name);
	}

	public List<Garbage> findBySort (Integer sort) {
		return garbageMapper.findBySort(sort);
	}
}
