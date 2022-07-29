package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.LoginRequest;
import com.calendar.clone.api.dto.SignUpRequest;
import com.calendar.clone.core.domain.Entity.User;
import com.calendar.clone.core.dto.UserCreateRequest;
import com.calendar.clone.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginService {

    public static final String LOGIN_SESSION_NAME = "loginSession";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpRequest signUpRequest, HttpSession session) {
        User user = userService.create(UserCreateRequest.of(
                signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_NAME, user.getId());
    }

    @Transactional
    public void login(LoginRequest loginRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute(LOGIN_SESSION_NAME);
        if (userId != null) {
            return ;
        }

        userService.matchEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .ifPresentOrElse(
                        user -> session.setAttribute(LOGIN_SESSION_NAME, user.getId()),
                        () -> {throw new RuntimeException("password or email not match");}
                );
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_NAME);
    }
}
