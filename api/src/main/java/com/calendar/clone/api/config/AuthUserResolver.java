package com.calendar.clone.api.config;

import com.calendar.clone.api.dto.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.calendar.clone.api.service.LoginService.LOGIN_SESSION_NAME;

public class AuthUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터 타입이 AuthUser이면 이 Rosolver의 resolveArgument() 로직을 수행하라.
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long userId = (Long) webRequest.getAttribute(LOGIN_SESSION_NAME, WebRequest.SCOPE_SESSION);
        if (userId == null) {
            throw new RuntimeException("bad request. no session.");
        }

        return AuthUser.of(userId);
    }
}
