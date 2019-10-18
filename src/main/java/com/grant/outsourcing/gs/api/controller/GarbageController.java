package com.grant.outsourcing.gs.api.controller;

import com.alibaba.fastjson.JSON;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.component.GarbageComponent;
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
	public Map<String,Object> getGarbageDictionary (@RequestParam(name = "sort") Integer sort) throws BaseException {
		LOGGER.debug("[getGarbageDictionary], sort: {}", sort);
		return buildResponse(garbageComponent.getGarbageDictionary(sort));
	}

}
