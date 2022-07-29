package com.calendar.clone.core.domain.Entity;

import com.calendar.clone.core.util.Encryptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

/**
 * @author kk2415
 * */
@AllArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Setter private String password;
    private LocalDate birthday;

    protected User() {}

    public User(String name, String email, String password, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public static User of(String name, String email, String password, LocalDate birthday) {
        return new User(name, email, password, birthday);
    }

    public boolean isMatch(Encryptor encryptor, String password) {
        return encryptor.isMatch(password, this.password);
    }
}
