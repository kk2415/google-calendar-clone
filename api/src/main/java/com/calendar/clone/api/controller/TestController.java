package com.calendar.clone.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final JavaMailSender javaMailSender;

    @GetMapping("/api/mail")
    public @ResponseBody void sendMail() {
        final MimeMessagePreparator preparator = (MimeMessage message) -> {
          final MimeMessageHelper messageHelper = new MimeMessageHelper(message);
          messageHelper.setTo("kkh2415@naver.com");
          messageHelper.setSubject("이메일 테스트입니다.");
          messageHelper.setText("이메일 테스트입니다.");
        };

        javaMailSender.send(preparator);
    }

    @GetMapping("test/templates")
    public String testTemplates(Model model) {
        final Map<String, Object> props = new HashMap<>();
        props.put("subject", "타이틀입니다.");
        props.put("calendar", "kkh2415@naver.com");
        props.put("period", "언제부터 언제까지.");
        props.put("attendees", List.of("test1@email.com", "test2@email.com", "test3@email.com"));
        props.put("acceptUrl", "http://localhost:8080");
        props.put("rejectUrl", "http://localhost:8080");
        model.addAllAttributes(props);

        return "engagement-email";
    }
}
