package com.calendar.clone.core.domain;

import com.calendar.clone.core.domain.Entity.Schedule;
import lombok.Getter;


/**
 * @author kk2415
 * */
@Getter
public class Task {

    private Schedule schedule;

    public String getTitle() {
        return schedule.getTitle();
    }

    protected Task() {}

    public Task(Schedule schedule) {
        this.schedule = schedule;
    }
}
