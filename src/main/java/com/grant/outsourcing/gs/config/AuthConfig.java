package com.grant.outsourcing.gs.config;

import com.grant.outsourcing.gs.auth.UserAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AuthConfig
{
	@Bean(name = "accountSdk")
	public UserAuth createUserAuth() throws Exception {
		return new UserAuth();
	}
}
