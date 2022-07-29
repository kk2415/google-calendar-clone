package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.EngagementEmailStuff;
import com.calendar.clone.core.domain.Entity.Engagement;
import com.calendar.clone.core.domain.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class FakeEmailService implements EmailService {
    @Override
    public void sendEngagement(EngagementEmailStuff e) {
    }
}
