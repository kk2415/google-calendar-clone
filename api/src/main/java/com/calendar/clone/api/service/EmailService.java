package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.EngagementEmailStuff;
import com.calendar.clone.core.domain.Entity.Engagement;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff e);
}
