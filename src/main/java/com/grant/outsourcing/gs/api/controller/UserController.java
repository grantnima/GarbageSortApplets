package com.grant.outsourcing.gs.api.controller;

import com.alibaba.fastjson.JSON;
import com.grant.outsourcing.gs.annotation.UserCheck;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.component.UserComponent;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseApp
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired private UserComponent userComponent;

	@GetMapping("/login")
	public Map<String,Object> login (@RequestParam(name = "code") String code) throws BaseException {
		LOGGER.debug("[login],code: {}",code);
		return buildResponse(userComponent.login(code));
	}

	@GetMapping("/info")
	public Map<String,Object> getUserInfo (@UserCheck User user) throws BaseException {
		LOGGER.debug("[getUserInfo],user: {}", JSON.toJSONString(user));
		return buildResponse(user);
	}

	@GetMapping("/test")
	public Map<String,Object> test (@UserCheck User user,@RequestParam(name = "code") String code) throws BaseException{
		if(code.equals("111")){
			throw new BaseException(ERespCode.INTERNAL_ERROR);
		}
		return buildResponse(code);
	}

	@PostMapping("/reward/detail")
	public Map<String,Object> submitDetail (@UserCheck User user,@RequestParam(name = "name") String name,
											@RequestParam(name = "phone") String phone,
											@RequestParam(name = "address") String address,
											@RequestParam(name = "email") String email) throws BaseException {
		LOGGER.debug("[submitDetail],user_id: {},name: {},phone: {},address: {},email: {}",user.getId(),name,phone,address,email);
		userComponent.submitDetail(user, name, phone, address, email);
		return buildResponse();
	}

	@GetMapping("/reward/check")
	public Map<String,Object> checkRewarded (@UserCheck User user) throws BaseException {
		LOGGER.debug("[checkRewarded],user_id: {}",user.getId());
		return buildResponse(userComponent.checkRewarded(user));
	}

	@GetMapping("/reward/export")
	public void exportRewardDetail (HttpServletResponse response) throws BaseException {
		LOGGER.debug("[exportRewardDetail]");
		userComponent.exportRewardDetail(response);
	}

}
