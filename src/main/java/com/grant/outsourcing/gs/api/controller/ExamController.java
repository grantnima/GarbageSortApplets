package com.grant.outsourcing.gs.api.controller;

import com.grant.outsourcing.gs.annotation.UserCheck;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.component.ExamComponent;
import com.grant.outsourcing.gs.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseApp
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

	@Autowired private ExamComponent examComponent;

	@GetMapping("/receive")
	public Map<String,Object> getQuestionOrResult (@UserCheck User user) throws BaseException {
		LOGGER.debug("[getQuestionOrResult],user_id: {}",user.getId());
		return buildResponse(examComponent.getQuestionOrResult(user));
	}

	@PostMapping("/answer")
	public Map<String,Object> answerQuestion (@UserCheck User user,
	                                          @RequestParam(name = "result") Integer result,
	                                          @RequestParam(name = "question_num") Integer questionNum,
	                                          @RequestParam(name = "garbage_id") Long garbageId) throws BaseException{
		LOGGER.debug("[answerQuestion],user_id: {},result: {},question_num: {},garbage_id: {}",user.getId(),result,questionNum,garbageId);
		examComponent.answerQuestion(user, result, questionNum, garbageId);
		return buildResponse();
	}
}
