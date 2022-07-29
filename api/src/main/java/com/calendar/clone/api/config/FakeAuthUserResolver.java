package com.calendar.clone.api.config;

import com.calendar.clone.api.dto.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.calendar.clone.api.service.LoginService.LOGIN_SESSION_NAME;

public class FakeAuthUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터 타입이 AuthUser이면 이 Rosolver의 resolveArgument() 로직을 수행하라.
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String userId = webRequest.getParameter("userId");
        if (userId == null) {
            return new AuthUserResolver().resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }

        return AuthUser.of(Long.parseLong(userId));
    }
}
