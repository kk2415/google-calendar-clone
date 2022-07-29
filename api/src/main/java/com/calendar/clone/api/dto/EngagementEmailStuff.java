package com.calendar.clone.api.dto;

import com.calendar.clone.core.domain.RequestReplyType;
import com.calendar.clone.core.domain.RequestStatus;
import com.calendar.clone.core.util.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class EngagementEmailStuff {
    private static final String engagementUpdateApi = "http://localhost:8080/events/engagements/";
    private final Long engagementId;
    private final String toEmail;
    private final List<String> attendeeEmails;
    private final String title;
    private final Period period;

    public EngagementEmailStuff(Long engagementId, String toEmail, List<String> attendeeEmails, String title, Period period) {
        this.engagementId = engagementId;
        this.toEmail = toEmail;
        this.attendeeEmails = attendeeEmails;
        this.title = title;
        this.period = period;
    }

    public String getSubject() {
        return new StringBuilder()
                .append("[초대장] ")
                .append(title)
                .append(" - ")
                .append(period.toString())
                .append("(")
                .append(toEmail)
                .append(")")
                .toString();
    }

    public String getToEmail() {
        return this.toEmail;
    }

    public Map<String, Object> getProps() {
        final Map<String, Object> props = new HashMap<>();

        props.put("subject", title);
        props.put("calendar", toEmail);
        props.put("period", period);
        props.put("attendees", attendeeEmails);
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=" + RequestReplyType.ACCEPT.name());
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=" + RequestReplyType.REJECT.name());
        return props;
    }
}
