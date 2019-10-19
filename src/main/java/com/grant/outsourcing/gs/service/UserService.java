package com.grant.outsourcing.gs.service;

import com.grant.outsourcing.gs.db.mapper.UserMapper;
import com.grant.outsourcing.gs.db.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserService
{
	@Resource private UserMapper userMapper;

	public void save (User user){
		userMapper.save(user);
	}

	public User findByOpenId (String openId){
		return userMapper.findByOpenId(openId);
	}

	public User findById (String id){
		return userMapper.findById(id);
	}
}
