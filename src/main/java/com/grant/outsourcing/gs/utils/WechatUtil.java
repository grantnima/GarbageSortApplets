package com.grant.outsourcing.gs.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.ERespCode;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class WechatUtil
{
	@Value("${wechat.app.id}")
	private String APP_ID;
	@Value("${wechat.app.secret}")
	private String APP_SECRET;

	public static JSONObject code2Session (String code) throws BaseException{
		String url = "https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		url= url.replace("APPID", "wx52eafb1d66f08849");
		url = url.replace("SECRET", "f377ef2c7a74964ed7b4e8a1bc6bee78");
		url = url.replace("JSCODE", code);

		try {
			String respStr = AsyncHttpHelper.get(url, new ArrayList<>());
			JSONObject resp = JSONObject.parseObject(respStr);
			Integer errorCode = resp.getInteger("errcode");
			if(errorCode == null || errorCode != 0){
				throw new BaseException(ERespCode.CODE_TO_SESSION_FAIL,"错误码为:" + errorCode);
			}
			String openId = resp.getString("openid");
			if (Strings.isNullOrEmpty(openId)) {
				throw new BaseException(ERespCode.CODE_TO_SESSION_FAIL,"openid为空");
			}
			return resp;
		} catch (Exception e) {
			throw new BaseException(ERespCode.INTERNAL_ERROR);
		}
	}

}
