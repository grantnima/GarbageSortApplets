package com.grant.outsourcing.gs.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.constant.CacheKey;
import com.grant.outsourcing.gs.db.model.Garbage;
import com.grant.outsourcing.gs.db.model.User;
import com.grant.outsourcing.gs.service.GarbageService;
import com.grant.outsourcing.gs.service.UserCollectionService;
import com.grant.outsourcing.gs.vo.ExamQuestionVo;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ExamComponent
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamComponent.class);

	@Autowired private RedissonClient redissonClient;

	@Autowired private GarbageService garbageService;

	@Autowired private UserCollectionService userCollectionService;

	public Map<String,Object> getQuestionOrResult (User user) throws BaseException{
		//先从redis看有没有记录
		RMap<String,JSONObject> userExamMap = redissonClient.getMap(CacheKey.USER_EXAM_RECORD);
		if(userExamMap.containsKey(user.getId())){
			//有记录
			JSONObject examInfoObj = userExamMap.get(user.getId());
			//判断是否已完成
			if(examInfoObj.getBooleanValue("finished")){
				//已完成 返回测验结果
				return getExamResult(userExamMap,examInfoObj,user);
			}else {
				//未完成 继续返回题目
				return getExamQuestion(userExamMap,examInfoObj,user);
			}
		}else {
			//无记录 重新答题
			return getExamQuestion(userExamMap,null,user);
		}
	}

	private Map<String,Object> getExamResult (RMap<String,JSONObject> userExamMap, JSONObject obj, User user) throws BaseException {
		Map<String,Object> response = new HashMap<>();

		response.put("finished",obj.getBooleanValue("finished"));
		Map<String,Object> infoMap = new HashMap<>();
		infoMap.put("correct_num",obj.getIntValue("correct_num"));
		infoMap.put("wrong_num",obj.getIntValue("wrong_num"));

		List<Map<String,Object>> garbageList = new ArrayList<>();
		Map<String,Object> questionMap = obj.getJSONObject("question_map");
		for(Map.Entry<String,Object> entry : questionMap.entrySet()){
			ExamQuestionVo examQuestionVo = JSONObject.parseObject(JSON.toJSONString(entry.getValue()),ExamQuestionVo.class);
			Garbage garbage = garbageService.findById(examQuestionVo.getGarbageId());
			if(garbage == null){
				continue;
			}
			Map<String,Object> garbageItem = new HashMap<>();
			garbageItem.put("garbage_id",garbage.getId());
			garbageItem.put("garbage_name",garbage.getName());
			garbageItem.put("garbage_sort",garbage.getSort());
			garbageItem.put("collected",userCollectionService.findByUserIdAndGarbageId(user.getId(),garbage.getId()) != null);
			garbageItem.put("result",examQuestionVo.getResult());
			garbageItem.put("chosen",examQuestionVo.getChosen());
			garbageItem.put("question_num",entry.getKey());
			garbageList.add(garbageItem);
		}
		//排序
		garbageList.sort(new Comparator<Map<String, Object>>()
		{
			@Override public int compare(Map<String, Object> o1, Map<String, Object> o2)
			{
				Integer o1Sort = Integer.parseInt(o1.getOrDefault("question_num",0).toString());
				Integer o2Sort = Integer.parseInt(o2.getOrDefault("question_num",0).toString());
				if(o1Sort > o2Sort){
					return -1;
				}else if (o1Sort < o2Sort){
					return 1;
				}
				return 0;
			}
		});
		infoMap.put("garbage_list",garbageList);
		response.put("info",infoMap);
		//清除改用户答题缓存
		userExamMap.remove(user.getId());
		return response;
 	}

 	private Map<String,Object> getExamQuestion (RMap<String,JSONObject> userExamMap, JSONObject obj, User user) throws BaseException{
		Map<String,Object> response = new HashMap<>();
		response.put("finished",false);
		if(obj == null){
			//无记录 重新答题
			//先取随机10条题
			List<Long> garbageIds = garbageService.findRandomId(10);
			//初始化JSONObject
			obj = new JSONObject();
			obj.put("correct_num",0);
			obj.put("wrong_num",0);
			obj.put("finished",false);
			obj.put("question_num",1);
			//砌question_list
			JSONObject questionMap = new JSONObject();
			Integer count = 1;
			for(Long garbageId : garbageIds){
				JSONObject questionItem = new JSONObject();
				questionItem.put("garbage_id",garbageId);
				questionMap.put(count.toString(),questionItem);
				count++;
			}
			obj.put("question_map",questionMap);
			//初始化好后 put入缓存
			userExamMap.put(user.getId(),obj);
		}

		//返回一题
	    Map<String,Object> infoMap = new HashMap<>();
	    Integer questionNum = obj.getInteger("question_num");
	    JSONObject questionMap = obj.getJSONObject("question_map");
		JSONObject questionItem = questionMap.getJSONObject(questionNum.toString());
		Long garbageId = questionItem.getLongValue("garbage_id");
	    infoMap.put("question_num",questionNum);
	    infoMap.put("garbage_id",garbageId);
	    infoMap.put("garbage_name",garbageService.findNameById(garbageId));
	    infoMap.put("collected",userCollectionService.findByUserIdAndGarbageId(user.getId(),garbageId) != null);
	    response.put("info",infoMap);
		return response;
    }

    public void answerQuestion (User user, Integer result, Integer questionNum, Long garbageId) throws BaseException{
		RMap<String,JSONObject> userExamMap = redissonClient.getMap(CacheKey.USER_EXAM_RECORD);
		if(!userExamMap.containsKey(user.getId())){
			LOGGER.error("答题失败，缓存中没有该用户的题目,user_id: {}",user.getId());
			return;
		}
		JSONObject examInfoObj = userExamMap.get(user.getId());
		JSONObject questionMap = examInfoObj.getJSONObject("question_map");
		Integer correctNum = examInfoObj.getInteger("correct_num");
		Integer wrongNum = examInfoObj.getInteger("wrong_num");
		//判断对错
	    if(result.equals(garbageService.findSortById(garbageId))){
	    	//答对了
		    examInfoObj.put("correct_num",correctNum + 1);
			ExamQuestionVo examQuestionVo = JSONObject.parseObject(JSON.toJSONString(questionMap.getJSONObject(questionNum.toString())),ExamQuestionVo.class);
			examQuestionVo.setResult(true);
			questionMap.put(questionNum.toString(),examQuestionVo);
	    }else {
	    	//答错了
		    examInfoObj.put("wrong_num",wrongNum + 1);
		    ExamQuestionVo examQuestionVo = JSONObject.parseObject(JSON.toJSONString(questionMap.getJSONObject(questionNum.toString())),ExamQuestionVo.class);
		    examQuestionVo.setResult(false);
		    examQuestionVo.setChosen(result);
		    questionMap.put(questionNum.toString(),examQuestionVo);
	    }
	    //更新questionMap
	    examInfoObj.put("question_map",questionMap);
	    //判断题目数是否已完成
	    if(questionNum == 10){
	    	//已完成
		    examInfoObj.put("finished",true);
	    }else {
	    	//未完成 题目数加一
		    examInfoObj.put("question_num",questionNum + 1);
	    }
	    //更新缓存
	    userExamMap.put(user.getId(),examInfoObj);
    }
}
