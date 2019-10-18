package com.grant.outsourcing.gs.component;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.common.base.Strings;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.constant.Constant;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.service.GarbageService;
import com.grant.outsourcing.gs.utils.ChineseToFirstLetterUtil;
import com.grant.outsourcing.gs.utils.StringUtils;
import com.grant.outsourcing.gs.vo.GarbageImportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class GarbageComponent
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GarbageComponent.class);

	@Autowired private GarbageService garbageService;

	public void createGarbage (PostGarbageRequest request) throws BaseException {
		if(Strings.isNullOrEmpty(request.getName())){
			throw new BaseException(ERespCode.INTERNAL_ERROR);
		}
		Garbage garbage = new Garbage();
		garbage.setId(StringUtils.getSimpleUUID());
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

		int count = 1;
		for(GarbageImportVo importVo : result){
			Garbage garbage = garbageService.findByName(importVo.getName());
			if(garbage != null){
				response.add("第" + count + "行数据，垃圾名已存在");
				count++;
				continue;
			}
			if (importVo.getSortType() == null) {
				response.add("第" + count + "行数据，垃圾类型名不正确");
				count++;
				continue;
			}
			garbage = new Garbage();
			garbage.setId(StringUtils.getSimpleUUID());
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
			count++;
		}
		return response;
	}

	public List<Map<String,Object>> getGarbageDictionary (Integer sort) throws BaseException {
		List<Map<String,Object>> response = new ArrayList<>();
		List<Garbage> garbageList = garbageService.findBySort(sort);
		if(garbageList != null && garbageList.size() != 0){
			for(Garbage garbage : garbageList){
				Map<String,Object> responseItem = new HashMap<>();
				responseItem.put("id",garbage.getId());
				responseItem.put("name",garbage.getName());
				responseItem.put("capital_letter",garbage.getCapitalLetter());
				response.add(responseItem);
			}
		}
		return response;
	}

}
