package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.UserCollectionMapper;
import com.grant.outsourcing.gs.db.model.UserCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserCollectionService
{
	@Resource private UserCollectionMapper userCollectionMapper;

	public void save (UserCollection userCollection) {
		userCollectionMapper.save(userCollection);
	}

	public List<UserCollection> findByUserId (String userId) {
		return userCollectionMapper.findByUserId(userId);
	}

	public void delete (String id) {
		userCollectionMapper.deleteById(id);
	}

	public UserCollection findByUserIdAndGarbageId (String userId, String garbageId){
		return userCollectionMapper.findByUserIdAndGarbageId(userId, garbageId);
	}
}
