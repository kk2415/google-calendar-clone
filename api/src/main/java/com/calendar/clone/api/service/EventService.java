package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.AuthUser;
import com.calendar.clone.api.dto.EngagementEmailStuff;
import com.calendar.clone.api.dto.EventCreateReq;
import com.calendar.clone.core.domain.Entity.Engagement;
import com.calendar.clone.core.domain.Entity.Schedule;
import com.calendar.clone.core.domain.Entity.User;
import com.calendar.clone.core.domain.RequestStatus;
import com.calendar.clone.core.exception.CalendarException;
import com.calendar.clone.core.exception.ErrorCode;
import com.calendar.clone.core.repository.EngagementRepository;
import com.calendar.clone.core.repository.ScheduleRepository;
import com.calendar.clone.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EventService {
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;
    private final EmailService emailService;

    @Transactional
    public void create(EventCreateReq req, AuthUser authUser) {
        // attendees 의 스케쥴 시간과 겹치지 않는지?
        final List<Engagement> engagementList =
                engagementRepository.findAllByUserIdInAndSchedule_EndAtAfter(req.getAttendeeIds(), req.getStartAt());

        if (engagementList.stream()
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt()) &&
                        e.getStatus() == RequestStatus.ACCEPTED)) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }

        final Schedule eventSchedule = Schedule.createEvent(
                userService.getOrThrowById(authUser.getId()),
                req.getStartAt(),
                req.getEndAt(),
                req.getTitle(),
                req.getDescription());

        scheduleRepository.save(eventSchedule);

        final List<User> attendeeList = req.getAttendeeIds().stream().map(userService::getOrThrowById).collect(toList());

        attendeeList.forEach(user -> {
            final Engagement e = engagementRepository.save(new Engagement(eventSchedule, user));
            emailService.sendEngagement(new EngagementEmailStuff(
                    e.getId(),
                    e.getAttendee().getEmail(),
                    attendeeList.stream().map(User::getEmail).collect(toList()),
                    e.getEvent().getTitle(),
                    e.getEvent().getPeriod()
            ));
        });
    }

}