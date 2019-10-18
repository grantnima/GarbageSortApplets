package com.grant.outsourcing.gs.component;

import com.google.common.base.Strings;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.api.request.PostGarbageRequest;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.service.GarbageService;
import com.grant.outsourcing.gs.utils.ChineseToFirstLetterUtil;
import com.grant.outsourcing.gs.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
}
