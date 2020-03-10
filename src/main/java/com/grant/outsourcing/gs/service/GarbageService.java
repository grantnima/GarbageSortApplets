package com.grant.outsourcing.gs.service;

import com.github.pagehelper.PageHelper;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.mapper.GarbageMapper;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.utils.PageResponse;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Component
public class GarbageService
{
	@Resource private GarbageMapper garbageMapper;

	@Resource private UserCollectionService userCollectionService;

	@Resource
	private RedissonClient redissonClient;

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
//		List<Long> response = new ArrayList<>();
//		while(response.size() < num){
//			Long randomId = garbageMapper.findIdByRandom();
//			if(!response.contains(randomId)){
//				response.add(randomId);
//			}
//		}
//		return response;
		Integer max = garbageMapper.countAll();
		Random rand = new Random();
		Set<Integer> randomNums = new HashSet<>();
		while (randomNums.size() < num){
			randomNums.add((rand.nextInt(max - 1 + 1) + 1));// randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数
		}
		List<Integer> randomNumList = new ArrayList<>(randomNums);
		RMap<Integer,Long> examPool = redissonClient.getMap(CacheKey.EXAM_POOL);
		List<Long> response = new ArrayList<>();
		for(Integer randomNum : randomNumList){
			response.add(examPool.get(randomNum));
		}
		return response;
	}
}
