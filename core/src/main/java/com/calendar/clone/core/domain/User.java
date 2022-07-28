package com.calendar.clone.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author kk2415
 * */
@Getter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime birthday;
    private LocalDateTime createAt;

    protected User() {}

    public User(String name, String email, String password, LocalDateTime birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.createAt = LocalDateTime.now();
    }

    public static User of(String name, String email, String password, LocalDateTime birthday) {
        return new User(name, email, password, birthday);
    }
}
