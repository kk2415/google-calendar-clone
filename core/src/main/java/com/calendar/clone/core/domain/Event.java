package com.calendar.clone.core.domain;

import com.calendar.clone.core.domain.Entity.Schedule;
import lombok.Getter;

/**
 * @author kk2415
 * */
@Getter
public class Event {

    private Schedule schedule;

    protected Event() {}

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }
}
