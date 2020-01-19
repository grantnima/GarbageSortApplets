package com.grant.outsourcing.gs.service;

import com.github.pagehelper.PageHelper;
import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.utils.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GarbageService
{
	@Resource private GarbageMapper garbageMapper;

	@Resource private UserCollectionService userCollectionService;

	public void save (Garbage garbage){
		garbageMapper.save(garbage);
	}

	public void update (Garbage garbage){
		garbageMapper.update(garbage);
	}

	public Garbage findByName (String name){
		return garbageMapper.findByName(name);
	}

	public PageResponse findBySort (String userId,Integer sort, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo,pageSize,"capital_letter asc");
		List<Garbage> garbageList = garbageMapper.findBySort(sort);
		if (garbageList != null && garbageList.size() != 0){
			for(Garbage garbage : garbageList){
				garbage.setCollected(userCollectionService.findByUserIdAndGarbageId(userId,garbage.getId()) != null);
			}
		}
		return new PageResponse<>(garbageList);
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
