package com.calendar.clone.core.domain.Entity;

import com.calendar.clone.core.domain.Event;
import com.calendar.clone.core.domain.RequestReplyType;
import com.calendar.clone.core.domain.RequestStatus;
import com.calendar.clone.core.domain.ScheduleType;
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
@Table(name = "engagement")
@Entity
public class Engagement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) private RequestStatus requestStatus;
    @ManyToOne @JoinColumn(name = "schedule_id") private Schedule schedule;
    @ManyToOne @JoinColumn(name = "user_id") private User user;

    protected Engagement() {}

    public Engagement(Schedule eventSchedule, User attendee) {
        assert eventSchedule.getScheduleType() == ScheduleType.EVENT;
        this.schedule = eventSchedule;
        this.requestStatus = RequestStatus.REQUESTED;
        this.user = attendee;
    }

    public Event getEvent() {
        return schedule.toEvent();
    }

    public User getAttendee() {
        return user;
    }

    public RequestStatus getStatus() {
        return requestStatus;
    }

    public boolean isOverlapped(LocalDate date) {
        return this.schedule.isOverlapped(date);
    }

    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }

    public Engagement reply(RequestReplyType type) {
        switch (type) {
            case ACCEPT:
                this.requestStatus = RequestStatus.ACCEPTED;
                break;
            case REJECT:
                this.requestStatus = RequestStatus.REJECTED;
                break;
        }
        return this;
    }
}
