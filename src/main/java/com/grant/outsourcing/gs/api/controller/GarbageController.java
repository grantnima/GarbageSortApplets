package com.grant.outsourcing.gs.api.controller;

import com.alibaba.fastjson.JSON;
import com.grant.outsourcing.gs.annotation.UserCheck;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.component.GarbageComponent;
import com.grant.outsourcing.gs.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/garbage")
public class GarbageController extends BaseApp
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GarbageController.class);

	@Autowired private GarbageComponent garbageComponent;

	@PostMapping("/add")
	public Map<String,Object> createGarbage (@RequestBody PostGarbageRequest request) throws BaseException {
		LOGGER.debug("[createGarbage], request: {}", JSON.toJSONString(request));
		garbageComponent.createGarbage(request);
		return buildResponse();
	}

	@PostMapping("/import")
	public Map<String,Object> importGarbage (@RequestParam MultipartFile file) throws BaseException {
		LOGGER.debug("[importGarbage], fileName: {}", file.getOriginalFilename());
		return buildResponse(garbageComponent.importGarbage(file));
	}

	@GetMapping("/dictionary")
	public Map<String,Object> getGarbageDictionary (@UserCheck User user,
	                                                @RequestParam(name = "sort") Integer sort) throws BaseException {
		LOGGER.debug("[getGarbageDictionary], user_id: {},sort: {}",user.getId(), sort);
		return buildResponse(garbageComponent.getGarbageDictionary(user,sort));
	}

	@PostMapping("/collection/operate")
	public Map<String,Object> addOrCancelCollection (@UserCheck User user,
	                                                 @RequestParam(name = "garbage_id") Long garbageId ) throws BaseException {
		LOGGER.debug("[addOrCancelCollection], user_id: {},garbage_id: {}", user.getId(),garbageId);
		garbageComponent.addOrCancelCollection(user,garbageId);
		return buildResponse();
	}

	@GetMapping("/collection/list")
	public Map<String,Object> getCollectionList (@UserCheck User user ) throws BaseException {
		LOGGER.debug("[getCollectionList], user_id: {}", user.getId());
		return buildResponse(garbageComponent.getCollectionList(user));
	}

	@GetMapping("/search")
	public Map<String,Object> searchGarbage (@UserCheck User user,
	                                         @RequestParam(name = "regx") String regx) throws BaseException {
		LOGGER.debug("[searchGarbage], user_id: {},regx: {}", user.getId(),regx);
		return buildResponse(garbageComponent.searchGarbage(user,regx));
	}

	@GetMapping("/search/record")
	public Map<String,Object> getSearchRecord (@UserCheck User user ) throws BaseException {
		LOGGER.debug("[getSearchRecord], user_id: {}", user.getId());
		return buildResponse(garbageComponent.getSearchRecord(user));
	}

	@PostMapping("/submit")
	public Map<String,Object> submitGarbage (@UserCheck User user,
	                                         @RequestParam(name = "garbage_name") String garbageName ) throws BaseException {
		LOGGER.debug("[submitGarbage], user_id: {},garbage_name: {}", user.getId(),garbageName);
		garbageComponent.submitGarbage(user,garbageName);
		return buildResponse();
	}

}
