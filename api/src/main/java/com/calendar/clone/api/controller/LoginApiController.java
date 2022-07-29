package com.calendar.clone.api.controller;

import com.calendar.clone.api.dto.LoginRequest;
import com.calendar.clone.api.dto.SignUpRequest;
import com.calendar.clone.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest, HttpSession session) {
        loginService.signUp(signUpRequest, session);

        return ResponseEntity.ok().build();
    }

    @PostMapping("sign-in")
    public ResponseEntity<Void> signUp(@RequestBody LoginRequest loginRequest, HttpSession session) {
        loginService.login(loginRequest, session);

        return ResponseEntity.ok().build();
    }

    @PostMapping("sign-out")
    public ResponseEntity<Void> signUp(HttpSession session) {
        loginService.logout(session);

        return ResponseEntity.ok().build();
    }
}
