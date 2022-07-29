package com.calendar.clone.core.domain;

import com.calendar.clone.core.domain.Entity.Schedule;
import com.calendar.clone.core.domain.Entity.User;
import com.calendar.clone.core.util.Period;
import lombok.Getter;

import java.time.LocalDateTime;

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

    public Long getId() {
        return this.schedule.getId();
    }
    public LocalDateTime getStartAt() {
        return schedule.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return schedule.getEndAt();
    }

    public User getWriter() {
        return this.schedule.getUser();
    }

    public String getTitle() {
        return this.schedule.getTitle();
    }

    public Period getPeriod() {
        return Period.of(this.schedule.getStartAt(), this.schedule.getEndAt());
    }

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.getStartAt().isBefore(endAt) && startAt.isBefore(this.getEndAt());
    }
}
