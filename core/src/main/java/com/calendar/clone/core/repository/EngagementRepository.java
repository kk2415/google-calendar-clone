package com.calendar.clone.core.repository;

import com.calendar.clone.core.domain.Entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByUserIdInAndSchedule_EndAtAfter(List<Long> attendeeIds, LocalDateTime startAt);

    List<Engagement> findAllByUserId(Long id);
}
