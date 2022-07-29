package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.AuthUser;
import com.calendar.clone.api.dto.TaskCreateReq;
import com.calendar.clone.core.domain.Entity.Schedule;
import com.calendar.clone.core.repository.ScheduleRepository;
import com.calendar.clone.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(TaskCreateReq req, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.createTask(
                userService.getOrThrowById(authUser.getId()),
                req.getTaskAt(),
                req.getTitle(),
                req.getDescription());
        scheduleRepository.save(taskSchedule);
    }

}
