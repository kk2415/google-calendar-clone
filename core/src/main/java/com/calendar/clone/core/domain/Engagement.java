package com.calendar.clone.core.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kk2415
 * */
@Getter
public class Engagement {

    private Long id;
    private Event event;
    private User user;
    private RequestStatus requestStatus;
    private LocalDateTime createAt;

    protected Engagement() {}

    public Engagement(Event event, User user, RequestStatus requestStatus) {
        this.event = event;
        this.user = user;
        this.requestStatus = requestStatus;
        this.createAt = LocalDateTime.now();
    }
}
