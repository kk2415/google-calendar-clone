package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.AuthUser;
import com.calendar.clone.api.dto.ScheduleDto;
import com.calendar.clone.api.util.DtoConverter;
import com.calendar.clone.core.repository.EngagementRepository;
import com.calendar.clone.core.repository.ScheduleRepository;
import com.calendar.clone.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getSchedulesByDay(LocalDate date, AuthUser authUser) {
        return getScheduleDtosByPeriod(authUser, Period.of(date, date));
    }

    public List<ScheduleDto> getSchedulesByWeek(LocalDate startOfWeek, AuthUser authUser) {
        return getScheduleDtosByPeriod(authUser, Period.of(startOfWeek, startOfWeek.plusDays(6)));

    }

    public List<ScheduleDto> getSchedulesByMonth(YearMonth yearMonth, AuthUser authUser) {
        return getScheduleDtosByPeriod(authUser, Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }

    private List<ScheduleDto> getScheduleDtosByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                scheduleRepository
                        .findAllByUser_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period))
                        .map(DtoConverter::toForListDto),
                engagementRepository
                        .findAllByUserId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.toForListDto(engagement.getSchedule()))
        ).collect(toList());
    }
}
