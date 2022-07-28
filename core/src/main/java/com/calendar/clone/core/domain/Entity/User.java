package com.calendar.clone.core.domain.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

/**
 * @author kk2415
 * */
@AllArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
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
