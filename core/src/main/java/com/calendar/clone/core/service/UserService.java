package com.calendar.clone.core.service;

import com.calendar.clone.core.domain.Entity.User;
import com.calendar.clone.core.dto.UserCreateRequest;
import com.calendar.clone.core.repository.UserRepository;
import com.calendar.clone.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateRequest userCreateRequest) {
        checkEmailDuplication(userCreateRequest.getEmail());

        User user = userCreateRequest.toEntity();
        user.setPassword(encryptor.encrypt(userCreateRequest.getPassword()));

        userRepository.save(user);
        return user;
    }

    private void checkEmailDuplication(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {throw new RuntimeException("user already existed!!");});
    }

    @Transactional
    public Optional<User> matchEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }

    public User getOrThrowById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("no user."));
    }
}
