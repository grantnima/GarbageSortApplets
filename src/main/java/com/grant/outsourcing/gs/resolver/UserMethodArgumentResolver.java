package com.grant.outsourcing.gs.resolver;

import com.grant.outsourcing.gs.annotation.UserCheck;
import com.grant.outsourcing.gs.api.exception.BadRequestException;
import com.grant.outsourcing.gs.api.exception.BaseException;
import com.grant.outsourcing.gs.auth.UserAuth;
import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.db.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private UserAuth userAuth;

	private final static Logger LOGGER = LoggerFactory.getLogger(UserMethodArgumentResolver.class);

	public UserMethodArgumentResolver (UserAuth userAuth) {
		if (userAuth == null) {
			throw new NullPointerException("userAuth is null");
		}
		this.userAuth = userAuth;
	}

	@Override public boolean supportsParameter(MethodParameter methodParameter)
	{
		return methodParameter.hasParameterAnnotation(UserCheck.class);
	}

	@Override public Object resolveArgument(MethodParameter methodParameter,
	                                        ModelAndViewContainer modelAndViewContainer,
	                                        NativeWebRequest webRequest,
	                                        WebDataBinderFactory webDataBinderFactory) throws BaseException{
		// 有这个注解，则开启授权认证
		String userId = webRequest.getHeader(UserAuth.HEADER_USER_ID);
		User user = null;
		try {
			user = userAuth.auth(userId);
		} catch (BaseException e) {
			LOGGER.info("UserAuth fail to get user info, userId: {},msg: {}", userId, e.getMessage());
		}
		if (user == null) {
			throw new BadRequestException(ERespCode.EXPIRED_TOKEN);
		}
		return user;
	}
}
