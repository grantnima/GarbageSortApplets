package com.grant.outsourcing.gs.component;

import com.alibaba.fastjson.JSONObject;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.model.User;
import com.grant.outsourcing.gs.service.UserService;
import com.grant.outsourcing.gs.utils.StringUtils;
import com.grant.outsourcing.gs.utils.WechatUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserComponent
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserComponent.class);

	@Autowired private UserService userService;

	@Autowired private RedissonClient redissonClient;

	public Map<String,Object> login (String code) throws BaseException{
		Map<String,Object> response = new HashMap<>();

//		JSONObject code2SessionResp = WechatUtil.code2Session(code);
//		String openId = code2SessionResp.getString("openid");
		String openId = code;

		//先看缓存里有没有
		RMap<String, User> userCache = redissonClient.getMap(CacheKey.USER_INFO);
		User user = userCache.get(openId);
		if(user == null){
			//缓存里没有
			//看看库里有没有
			user = userService.findByOpenId(openId);
			if(user == null){
				//库里都没有
				//新建
				user = new User();
				user.setId(StringUtils.getSimpleUUID());
				user.setOpenId(openId);
				user.setCreateTime(System.currentTimeMillis());
				userService.save(user);
				//保存到缓存
				userCache.put(openId,user);
				userCache.put(user.getId(),user);
			}
		}

		response.put("user_id",user.getId());
		return response;
	}
}
