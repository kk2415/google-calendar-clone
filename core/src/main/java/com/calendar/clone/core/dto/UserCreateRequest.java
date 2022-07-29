package com.calendar.clone.core.dto;

import com.calendar.clone.core.domain.Entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserCreateRequest {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;

    private UserCreateRequest(String name, String email, String password, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public static UserCreateRequest of(String name, String email, String password, LocalDate birthday) {
        return new UserCreateRequest(name, email, password, birthday);
    }

    public User toEntity() {
        return User.of(name, email, password, birthday);
    }
}
