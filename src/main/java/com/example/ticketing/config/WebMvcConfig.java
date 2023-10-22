package com.example.ticketing.config;

import com.example.ticketing.resolver.BusinessUserArgumentResolver;
import com.example.ticketing.resolver.UserArgumentResolver;
import com.example.ticketing.service.businessuser.BusinessUserService;
import com.example.ticketing.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final BusinessUserService businessUserService;
	private final UserService userService;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new BusinessUserArgumentResolver(businessUserService));
		resolvers.add(new UserArgumentResolver(userService));
	}
}
