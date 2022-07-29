package com.calendar.clone.api.service;

import com.calendar.clone.api.dto.AuthUser;
import com.calendar.clone.core.domain.Event;
import com.calendar.clone.core.domain.RequestReplyType;
import com.calendar.clone.core.domain.RequestStatus;
import com.calendar.clone.core.exception.CalendarException;
import com.calendar.clone.core.exception.ErrorCode;
import com.calendar.clone.core.repository.EngagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        // engagement 조회
        // 참석자가 auth user와 같은지 비교
        // requested 상태인지 체크
        // update

        return engagementRepository.findById(engagementId)
                .filter(e -> e.getRequestStatus().equals(RequestStatus.REQUESTED))
                .filter(e -> e.getUser().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getStatus();
    }
}
