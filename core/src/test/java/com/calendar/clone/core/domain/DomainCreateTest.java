package com.calendar.clone.core.domain;

import com.calendar.clone.core.domain.Entity.Schedule;
import com.calendar.clone.core.domain.Entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author kk2415
 * */
public class DomainCreateTest {

    @Test
    void createTest() {
        User writer = User.of("writer", "email@email.com", "pwd", LocalDate.now());
        final Schedule taskSchedule = Schedule.createTask(writer, LocalDateTime.now(), "할 일", "할 일");

        Assertions.assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        Assertions.assertEquals(taskSchedule.toTask().getTitle(), taskSchedule.getTitle());
    }
}
