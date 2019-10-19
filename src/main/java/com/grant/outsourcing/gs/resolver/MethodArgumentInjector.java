package com.grant.outsourcing.gs.resolver;

import com.grant.outsourcing.gs.auth.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MethodArgumentInjector
{
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Autowired(required = false)
	private UserAuth userAuth;

	@PostConstruct
	private void injectCustomerMethodArgumentResolver() {
		List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();

		// token检验的优先级最高
		if (userAuth != null) {
			argumentResolvers.add(new UserMethodArgumentResolver(userAuth));
		}

		argumentResolvers.addAll(requestMappingHandlerAdapter.getArgumentResolvers());
		requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);
	}
}
