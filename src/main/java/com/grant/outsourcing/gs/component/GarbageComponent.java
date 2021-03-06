package com.grant.outsourcing.gs.component;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.api.response.PaginationResponse;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.constant.Constant;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.db.model.GarbageSubmit;
import com.grant.outsourcing.gs.db.model.User;
import com.grant.outsourcing.gs.db.model.UserCollection;
import com.grant.outsourcing.gs.service.GarbageService;
import com.grant.outsourcing.gs.service.GarbageSubmitService;
import com.grant.outsourcing.gs.service.UserCollectionService;
import com.grant.outsourcing.gs.utils.*;
import com.grant.outsourcing.gs.vo.GarbageImportVo;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("Duplicates")
@Component
public class GarbageComponent
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GarbageComponent.class);

	@Autowired private GarbageService garbageService;

	@Autowired private UserCollectionService userCollectionService;

	@Autowired private RedissonClient redissonClient;

	@Autowired private IdUtil idUtil;

	@Autowired private GarbageSubmitService garbageSubmitService;

	public void createGarbage (PostGarbageRequest request) throws BaseException {
		if(Strings.isNullOrEmpty(request.getName())){
			throw new BaseException(ERespCode.INTERNAL_ERROR);
		}
		Garbage garbage = new Garbage();
		garbage.setId(idUtil.nextId());
		garbage.setName(request.getName());
		garbage.setSort(request.getSort());
		String capitalLetter = "";
		try {
			capitalLetter = ChineseToFirstLetterUtil.ChineseToFirstLetter(request.getName()).substring(0,1);
		}catch (Exception e){
			LOGGER.error("垃圾名首字母获取失败，垃圾名: {}",request.getName());
		}
		if(!Strings.isNullOrEmpty(capitalLetter)){
			garbage.setCapitalLetter(capitalLetter);
		}
		garbageService.save(garbage);
	}

	public List<String> importGarbage (MultipartFile file) throws BaseException {
		List<String> response = new ArrayList<>();

		ImportParams importParams = new ImportParams();
		importParams.setTitleRows(0);
		importParams.setKeyIndex(0);

		List<GarbageImportVo> result = null;

		try{
			result = ExcelImportUtil.importExcel(file.getInputStream(),GarbageImportVo.class,importParams);
		}catch (Exception e){
			LOGGER.error("excel分析错误");
			response.add("Excel分析失败");
			return response;
		}

		if(result == null || result.size() == 0){
			response.add("Excel数据为空");
			return response;
		}
		BigDecimal totalCount = new BigDecimal(result.size());
		int count = 1;
		for(GarbageImportVo importVo : result){
			if (importVo.getSortType() == null) {
				response.add("第" + count + "行数据，垃圾类型名不正确");
				count++;
				continue;
			}

			Garbage garbage = garbageService.findByName(importVo.getName());
			if(garbage != null){
				garbage.setSort(importVo.getSortType());
				garbageService.update(garbage);
				response.add("第" + count + "行数据，垃圾名已存在，已更新垃圾类型");
				count++;
				continue;
			}

			garbage = new Garbage();
			garbage.setId(idUtil.nextId());
			garbage.setSort(importVo.getSortType());
			garbage.setName(importVo.getName());
			String capitalLetter = "";
			try {
				capitalLetter = ChineseToFirstLetterUtil.ChineseToFirstLetter(importVo.getName()).substring(0,1);
			}catch (Exception e){
				LOGGER.error("垃圾名首字母获取失败，垃圾名: {}",importVo.getName());
				response.add("垃圾名首字母获取失败，垃圾名:" + importVo.getName());
				continue;
			}
			garbage.setCapitalLetter(capitalLetter);
			garbageService.save(garbage);
			LOGGER.info("新垃圾: {},类型: {}",garbage.getName(),garbage.getSort());
			//进度条
			LOGGER.debug("[导入数据进度]: {}%",new BigDecimal(count).divide(totalCount,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			count++;
		}
		return response;
	}

	public PageResponse getGarbageDictionary (User user, Integer sort, Integer pageNo, Integer pageSize) throws BaseException {
//		Map<String,List<Map<String,Object>>> response = new HashMap<>();
//		List<Garbage> garbageList = garbageService.findBySort(sort);
//		if(garbageList != null && garbageList.size() != 0){
//			for(Garbage garbage : garbageList){
//				if(response.containsKey(garbage.getCapitalLetter())){
//					List<Map<String,Object>> capitalList = response.get(garbage.getCapitalLetter());
//					Map<String,Object> responseItem = new HashMap<>();
//					responseItem.put("id",garbage.getId());
//					responseItem.put("name",garbage.getName());
//					responseItem.put("capital_letter",garbage.getCapitalLetter());
//					responseItem.put("collected",userCollectionService.findByUserIdAndGarbageId(user.getId(),garbage.getId()) != null);
//					capitalList.add(responseItem);
//					response.put(garbage.getCapitalLetter(),capitalList);
//				}else {
//					List<Map<String,Object>> capitalList = new ArrayList<>();
//					Map<String,Object> responseItem = new HashMap<>();
//					responseItem.put("id",garbage.getId());
//					responseItem.put("name",garbage.getName());
//					responseItem.put("capital_letter",garbage.getCapitalLetter());
//					responseItem.put("collected",userCollectionService.findByUserIdAndGarbageId(user.getId(),garbage.getId()) != null);
//					capitalList.add(responseItem);
//					response.put(garbage.getCapitalLetter(),capitalList);
//				}
//			}
//		}
//		return response;
		if (pageNo < 1) {
			throw new BaseException(ERespCode.TYPE_MISMATCH_EXCEPTION, "pageNo参数错误");
		}
		return garbageService.findBySort(user.getId(),sort,pageNo,pageSize);
	}

	public void addOrCancelCollection (User user, Long garbageId ) throws BaseException {
		UserCollection userCollection = userCollectionService.findByUserIdAndGarbageId(user.getId(),garbageId);
		if(userCollection != null){
			//取消收藏
			userCollectionService.delete(userCollection.getId());
			return;
		}
		//加入收藏
		userCollection = new UserCollection();
		userCollection.setId(StringUtils.getSimpleUUID());
		userCollection.setGarbageId(garbageId);
		userCollection.setUserId(user.getId());
		userCollection.setCreateTime(System.currentTimeMillis());
		userCollectionService.save(userCollection);
	}

	public List<Map<String,Object>> getCollectionList (User user) throws BaseException {
		List<Map<String,Object>> response = new ArrayList<>();
		List<UserCollection> collections = userCollectionService.findByUserId(user.getId());
		if(collections == null || collections.size() == 0){
			return response;
		}
		for(UserCollection collection : collections){
			Map<String,Object> responseItem = new HashMap<>();
			responseItem.put("collection_id",collection.getId());
			responseItem.put("garbage_id",collection.getGarbageId());
			responseItem.put("garbage_name",garbageService.findNameById(collection.getGarbageId()));
			responseItem.put("garbage_sort",garbageService.findSortById(collection.getGarbageId()));
			response.add(responseItem);
		}
		return response;
	}

	public List<Map<String,Object>> searchGarbage (User user, String regx) throws BaseException {
		List<Map<String,Object>> response = new ArrayList<>();
		//保存搜索记录
		saveSearchRecord(user.getId(),regx);

		List<Garbage> garbageList = garbageService.findByRegx("%" + regx + "%");
		if(garbageList == null || garbageList.size() == 0){
			return response;
		}
		for(Garbage garbage : garbageList){
			garbage.setMatchScore(SimilarityUtils.levenshtein(regx,garbage.getName()));
		}
		garbageList.sort(new Comparator<Garbage>() {
			@Override
			public int compare(Garbage o1, Garbage o2) {
				if(o1.getMatchScore() > o2.getMatchScore()){
					return -1;
				}
				if(o1.getMatchScore() < o2.getMatchScore()){
					return 1;
				}
				return 0;
			}
		});
		for(Garbage garbage : garbageList){
			Map<String,Object> responseItem = new HashMap<>();
			responseItem.put("garbage_id",garbage.getId().toString());
			responseItem.put("garbage_name",garbage.getName());
			responseItem.put("garbage_sort",garbage.getSort());
			responseItem.put("collected",userCollectionService.findByUserIdAndGarbageId(user.getId(),garbage.getId()) != null);
			response.add(responseItem);
		}

		return response;
	}

	private void saveSearchRecord (String userId, String regx){
		RMap<String,List<String>> recordMap = redissonClient.getMap(CacheKey.USER_SEARCH_RECORD);
		List<String> regxList = recordMap.get(userId);
		if(regxList == null){
			//该用户还未有搜索记录
			regxList = new ArrayList<>();
			regxList.add(regx);
			recordMap.put(userId,regxList);
		}else {
			//用户已有搜索记录 加一个
			//不是已有才加入
			if(!regxList.contains(regx)){
				regxList.add(regx);
				recordMap.put(userId,regxList);
			}
		}
	}

	public List<String> getSearchRecord (User user) throws BaseException {
		RMap<String,List<String>> recordMap = redissonClient.getMap(CacheKey.USER_SEARCH_RECORD);
		return recordMap.getOrDefault(user.getId(),new ArrayList<>());
	}

	public void submitGarbage (User user, String garbageName) throws BaseException{
		GarbageSubmit submit = garbageSubmitService.findByUserIdAndGarbageName(user.getId(),garbageName);
		if(submit != null){
			LOGGER.error("用户多次上传不在库垃圾，user_id: {},garbage_name: {}",user.getId(),garbageName);
			return;
		}
		submit = new GarbageSubmit();
		submit.setId(idUtil.nextId());
		submit.setGarbageName(garbageName);
		submit.setUserId(user.getId());
		garbageSubmitService.save(submit);
	}

	public void cleanSearchRecord (String userId) throws BaseException{
		RMap<String,List<String>> recordMap = redissonClient.getMap(CacheKey.USER_SEARCH_RECORD);
		recordMap.remove(userId);
	}
}
