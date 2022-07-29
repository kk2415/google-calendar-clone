package com.calendar.clone.core.domain.Entity;

import com.calendar.clone.core.domain.Event;
import com.calendar.clone.core.domain.Notification;
import com.calendar.clone.core.domain.ScheduleType;
import com.calendar.clone.core.domain.Task;
import com.calendar.clone.core.util.Period;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author kk2415
 * */
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Table(name = "schedule")
@Entity
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING) private ScheduleType scheduleType;

    @ManyToOne @JoinColumn(name = "user_id") private User user;

    protected Schedule() {}

    public static Schedule createEvent(User user, LocalDateTime startAt, LocalDateTime endAt, String title, String description) {
        return Schedule.builder()
                .startAt(startAt)
                .endAt(endAt)
                .title(title)
                .description(description)
                .scheduleType(ScheduleType.EVENT)
                .user(user)
                .build();
    }

    public static Schedule createNotification(User user, LocalDateTime notifyAt, String title) {
        return Schedule.builder()
                .startAt(notifyAt)
                .title(title)
                .scheduleType(ScheduleType.NOTIFICATION)
                .user(user)
                .build();
    }

    public static Schedule createTask(User user, LocalDateTime taskAt, String title, String description) {
        return Schedule.builder()
                .startAt(taskAt)
                .title(title)
                .description(description)
                .scheduleType(ScheduleType.TASK)
                .user(user)
                .build();
    }

    public Task toTask() {
        return new Task(this);
    }

    public Notification toNotification() {
        return new Notification(this);
    }

    public Event toEvent() {
        return new Event(this);
    }

    public boolean isOverlapped(LocalDate date) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(date);
    }

    public boolean isOverlapped(Period period) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(period);
    }
}
