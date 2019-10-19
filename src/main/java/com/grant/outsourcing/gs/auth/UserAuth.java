package com.grant.outsourcing.gs.auth;

import com.google.common.base.Strings;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.model.User;
import com.grant.outsourcing.gs.service.UserService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAuth
{
	/**
	 * http请求头 access_token
	 */
	public static final String HEADER_USER_ID = "GS_USER_ID";

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private UserService userService;

	public User auth(String userId) throws BaseException {

		// 临时改逻辑
		if (Strings.isNullOrEmpty(userId)) {
			return null;
		}

		RMap<String, User> userCache = redissonClient.getMap(CacheKey.USER_INFO);
		User user = userCache.get(userId);

		if (user == null) {
			// 兼容旧用户
			user = userService.findById(userId);
			if (user != null) {
				userCache.put(userId, user);
			}
		}

		return user;
	}

}
