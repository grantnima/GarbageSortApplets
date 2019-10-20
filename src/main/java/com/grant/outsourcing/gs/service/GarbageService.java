package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import com.grant.outsourcing.gs.db.model.Garbage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

	public String findNameById (Long id) {
		return garbageMapper.findNameById(id);
	}

	public Integer findSortById (Long id) {
		return garbageMapper.findSortById(id);
	}

	public List<Garbage> findByRegx (String regx) {
		return garbageMapper.findByRegx(regx);
	}

	public Garbage findById (Long id) {
		return garbageMapper.findById(id);
	}

	public List<Long> findRandomId (Integer num) {
		List<Long> response = new ArrayList<>();
		while(response.size() < num){
			Long randomId = garbageMapper.findIdByRandom();
			if(!response.contains(randomId)){
				response.add(randomId);
			}
		}
		return response;
	}
}
