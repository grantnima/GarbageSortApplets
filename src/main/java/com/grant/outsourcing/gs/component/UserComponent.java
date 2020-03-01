package com.grant.outsourcing.gs.component;

import cn.hutool.core.io.IoUtil;
import cn.hutool.log.StaticLog;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.RewardedDetail;
import com.grant.outsourcing.gs.db.model.SystemSetting;
import com.grant.outsourcing.gs.db.model.User;
import com.grant.outsourcing.gs.service.RewardDetailService;
import com.grant.outsourcing.gs.service.UserService;
import com.grant.outsourcing.gs.utils.*;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Component
public class UserComponent
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserComponent.class);

	@Autowired private UserService userService;

	@Autowired private RedissonClient redissonClient;

	@Resource
	private RewardDetailService rewardDetailService;

	@Resource
	private IdUtil idUtil;

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

	public void submitDetail (User user, String name, String phone, String address, String email) throws BaseException {
		if(!ValidateUtils.isPhone(phone)){
			throw new BaseException(ERespCode.TYPE_MISMATCH_EXCEPTION,"电话格式错误");
		}
		if(!ValidateUtils.checkEmail(email)){
			throw new BaseException(ERespCode.TYPE_MISMATCH_EXCEPTION,"邮箱格式错误");
		}
		//检查填过资料未
		boolean isNew = false;
		RewardedDetail rewardedDetail = rewardDetailService.findOneByUserId(user.getId());
		if(rewardedDetail == null){
			rewardedDetail = new RewardedDetail();
			rewardedDetail.setId(idUtil.nextId());
			rewardedDetail.setCreateTime(System.currentTimeMillis());
			rewardedDetail.setUserId(user.getId());
			rewardedDetail.setLastRewardTime(System.currentTimeMillis());
			rewardedDetail.setCount(1);
			isNew = true;
		}
		rewardedDetail.setName(name);
		rewardedDetail.setPhone(phone);
		rewardedDetail.setAddress(address);
		rewardedDetail.setEmail(email);

		if(isNew){
			rewardDetailService.save(rewardedDetail);
		}else {
			rewardDetailService.update(rewardedDetail);
		}
	}

	public Map<String,Object> checkRewarded (User user){
		Map<String,Object> response = new HashMap<>();
		RewardedDetail rewardedDetail = rewardDetailService.findOneByUserId(user.getId());
		if(rewardedDetail == null){
			//未打赏过
			response.put("rewarded",false);
		}else {
			//打赏过 加1打赏次数 set最近打赏时间
			rewardedDetail.setCount(rewardedDetail.getCount() + 1);
			rewardedDetail.setLastRewardTime(System.currentTimeMillis());
			rewardDetailService.update(rewardedDetail);
			response.put("rewarded",true);
		}
		return response;
	}

	public void exportRewardDetail (HttpServletResponse response) throws BaseException {
		List<Map<String,Object>> exportList = new ArrayList<>();
		List<RewardedDetail> rewardedDetails = rewardDetailService.findAll();
		if(rewardedDetails == null || rewardedDetails.size() == 0){
			throw new BaseException(ERespCode.RESOURCE_NOT_FOUND,"暂无打赏");
		}
		for(RewardedDetail rewardedDetail : rewardedDetails){
			Map<String,Object> exportItem = new HashMap<>();

			exportItem.put("name",rewardedDetail.getName());
			exportItem.put("phone",rewardedDetail.getPhone());
			exportItem.put("address",rewardedDetail.getAddress());
			exportItem.put("email",rewardedDetail.getEmail());
			exportItem.put("count",rewardedDetail.getCount());
			exportItem.put("firstRewardTime",new Date(rewardedDetail.getCreateTime()));
			exportItem.put("lastRewardTime",new Date(rewardedDetail.getLastRewardTime()));

			exportList.add(exportItem);
		}

		try {
			// 通过工具类创建writer，默认创建xls格式
			ExcelWriter writer = ExcelUtil.getWriter();
			//自定义标题别名
			writer.addHeaderAlias("name", "收件人");
			writer.addHeaderAlias("phone", "电话");
			writer.addHeaderAlias("address", "详细地址");
			writer.addHeaderAlias("email", "邮箱");
			writer.addHeaderAlias("count", "打赏次数");
			writer.addHeaderAlias("firstRewardTime", "首次打赏时间");
			writer.addHeaderAlias("lastRewardTime", "最近打赏时间");
			writer.setColumnWidth(1,50);
			writer.setColumnWidth(2,50);
			writer.setColumnWidth(5,25);
			writer.setColumnWidth(6,25);
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(exportList, true);
			//out为OutputStream，需要写出到的目标流

			//response为HttpServletResponse对象
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
//			String fileName = projectName + "-" + DateUtils.getDateStrFromTimestamp(System.currentTimeMillis(),"yyyyMMdd") + ".xls";
//			fileName = URLEncoder.encode(fileName,"UTF-8");
			response.setHeader("Content-Disposition","attachment;filename=" + "rewardedList.xls");
			ServletOutputStream out=response.getOutputStream();

			writer.flush(out, true);
			// 关闭writer，释放内存
			writer.close();
			//此处记得关闭输出Servlet流
			IoUtil.close(out);
		}catch (Exception e){
			StaticLog.error(e.getMessage(), e);
			throw new BaseException(ERespCode.INTERNAL_ERROR,"导出失败");
		}
	}
}
