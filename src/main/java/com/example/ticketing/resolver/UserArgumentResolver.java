package com.example.ticketing.resolver;

import com.example.ticketing.domain.User;
import com.example.ticketing.service.user.UserService;
import com.example.ticketing.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String AUTHORIZATION_HEADER_NAME = "X-Authorization";

	private final UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (Objects.isNull(servletRequestAttributes)) {
			return null;
		}

		final String authorization = servletRequestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER_NAME);

		final Long userId = JwtUtils.getIdFromToken(authorization);

		return userService.getUser(userId);
	}
}
