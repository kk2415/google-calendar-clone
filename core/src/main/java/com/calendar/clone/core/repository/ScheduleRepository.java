package com.calendar.clone.core.repository;

import com.calendar.clone.core.domain.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUser_Id(Long id);
}
