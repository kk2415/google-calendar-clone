package com.calendar.clone.api.dto;

import com.calendar.clone.core.domain.RequestReplyType;
import com.calendar.clone.core.domain.RequestStatus;
import com.calendar.clone.core.util.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Data
public class EngagementEmailStuff {
    private static final String engagementUpdateApi = "http://localhost:8080/events/engagements/";
    public static final String MAIL_TIME_FORMAT = "yyyy년 MM월 dd일(E) a hh시 mm분";
    public static final List<Pair<String, Predicate<Period>>> endAtFormatParts = Arrays.asList(
            Pair.of("yyyy년 ", period -> period.getEndAt().getYear() == period.getStartAt().getYear()),
            Pair.of("MM월 ", period -> period.getEndAt().getMonth() == period.getStartAt().getMonth()),
            Pair.of("dd일(E) ", period -> period.getEndAt().getDayOfMonth() == period.getStartAt().getDayOfMonth())
    );

    private final Long engagementId;
    private final String toEmail;
    private final List<String> attendeeEmails;
    private final String title;
    private final Period period;
    private final String periodStr;

    public EngagementEmailStuff(Long engagementId,
                                String toEmail,
                                List<String> attendeeEmails,
                                String title,
                                Period period) {
        this.engagementId = engagementId;
        this.toEmail = toEmail;
        this.attendeeEmails = attendeeEmails;
        this.title = title;
        this.period = period;
        this.periodStr = getPeriodStrRecursive();
    }

    public static String getEndAtFormat(
            Period period,
            String format,
            List<Pair<String, Predicate<Period>>> remainEndAtFormatParts
    ) {
        if (remainEndAtFormatParts.isEmpty()) {
            return format;
        } else if (remainEndAtFormatParts.get(0).getSecond().test(period)) {
            return getEndAtFormat(
                    period,
                    format.replace(remainEndAtFormatParts.get(0).getFirst(), ""),
                    remainEndAtFormatParts.subList(1, remainEndAtFormatParts.size()));
        } else {
            return format;
        }
    }

    private String getPeriodStr() {
        final String startAtFormat = "yyyy년 MM월 dd일(E) a hh시 mm분";
        String endAtFormat = "yyyy년 MM월 dd일(E) a hh시 mm분";
        if (period.getEndAt().getYear() == period.getStartAt().getYear()) {
            endAtFormat = endAtFormat.replace("yyyy년 ", "");
            if (period.getEndAt().getMonth() == period.getStartAt().getMonth()) {
                endAtFormat = endAtFormat.replace("MM월 ", "");
                if (period.getEndAt().getDayOfMonth() == period.getStartAt().getDayOfMonth()) {
                    endAtFormat = endAtFormat.replace("dd일(E) ", "");
                }
            }
        }
        return period.getStartAt().format(DateTimeFormatter.ofPattern(startAtFormat)) + " - "
                + period.getEndAt().format(DateTimeFormatter.ofPattern(endAtFormat));
    }

    private String getPeriodStrRecursive() {
        final String endAtFormat = getEndAtFormat(period, MAIL_TIME_FORMAT, endAtFormatParts);
        return period.getStartAt().format(DateTimeFormatter.ofPattern(MAIL_TIME_FORMAT)) + " - "
                + period.getEndAt().format(DateTimeFormatter.ofPattern(endAtFormat));
    }

    public String getSubject() {
        return new StringBuilder()
                .append("[초대장] ")
                .append(title)
                .append(" - ")
                .append(periodStr.toString())
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
        props.put("period", periodStr);
        props.put("attendees", attendeeEmails);
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=" + RequestReplyType.ACCEPT.name());
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=" + RequestReplyType.REJECT.name());
        return props;
    }
}
