package com.grant.outsourcing.gs.api.controller;

import com.alibaba.fastjson.JSON;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.component.GarbageComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
