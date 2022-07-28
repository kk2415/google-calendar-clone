package com.calendar.clone.core.domain;

import com.calendar.clone.core.domain.Entity.Schedule;
import lombok.Getter;


/**
 * @author kk2415
 * */
@Getter
public class Notification {

    private Schedule schedule;

    protected Notification() {}

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }
}
