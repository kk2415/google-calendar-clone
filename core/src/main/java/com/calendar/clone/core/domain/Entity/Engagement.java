package com.calendar.clone.core.domain.Entity;

import com.calendar.clone.core.domain.Event;
import com.calendar.clone.core.domain.RequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
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
public class Engagement {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) private RequestStatus requestStatus;
    @ManyToOne @JoinColumn(name = "schedule_id") private Schedule schedule;
    @ManyToOne @JoinColumn(name = "user_id") private User user;
    private LocalDateTime createAt;

    protected Engagement() {}
}
