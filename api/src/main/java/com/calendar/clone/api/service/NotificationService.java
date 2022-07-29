package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.AuthUser;
import com.calendar.clone.api.dto.NotificationCreateReq;
import com.calendar.clone.core.domain.Entity.Schedule;
import com.calendar.clone.core.domain.Entity.User;
import com.calendar.clone.core.repository.ScheduleRepository;
import com.calendar.clone.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public void create(NotificationCreateReq req, AuthUser authUser) {
        final User writer = userService.getOrThrowById(authUser.getId());
        req.getRepeatTimes()
                .forEach(notifyAt ->
                        scheduleRepository.save(Schedule.createNotification(writer, notifyAt, req.getTitle())));
    }
}
