package com.calendar.clone.api.service;

import com.calendar.clone.api.controller.BatchApiController;
import com.calendar.clone.api.dto.EngagementEmailStuff;
import com.calendar.clone.core.domain.Entity.Engagement;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.calendar.clone.api.dto.EngagementEmailStuff.MAIL_TIME_FORMAT;

@RequiredArgsConstructor
@Service
public class RealEmailService implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEngagement(EngagementEmailStuff stuff) {
        final MimeMessagePreparator preparator = (MimeMessage message) -> {
          final MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(stuff.getToEmail());
            helper.setSubject(stuff.getSubject());
            helper.setText(
                    templateEngine.process("engagement-email",
                            new Context(Locale.KOREAN, stuff.getProps())), true
            );
        };

        emailSender.send(preparator);
    }

    @Override
    public void sendAlarmMail(BatchApiController.SendMailBatchReq req) {
        final MimeMessagePreparator preparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(req.getUserEmail());
            helper.setSubject(req.getTitle());
            helper.setText(String.format(
                    "[%s] %s",
                    req.getStartAt().format(DateTimeFormatter.ofPattern(MAIL_TIME_FORMAT)),
                    req.getTitle()));
        };
        emailSender.send(preparator);
    }
}
