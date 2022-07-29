package com.calendar.clone.api.dto;

import com.calendar.clone.core.domain.RequestReplyType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyEngagementReq {

    private RequestReplyType type;

    public ReplyEngagementReq(RequestReplyType type) {
        this.type = type;
    }
}
