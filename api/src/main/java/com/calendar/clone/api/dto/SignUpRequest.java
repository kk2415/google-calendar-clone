package com.calendar.clone.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SignUpRequest {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;
}
