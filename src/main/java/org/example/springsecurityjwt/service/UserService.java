package org.example.springsecurityjwt.service;


import org.example.springsecurityjwt.entity.UserEntity;
import org.example.springsecurityjwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity<Optional<UserEntity>> findByName(String username) {
        Optional<UserEntity> userEntity = userRepository.findByName(username);
        return ResponseEntity.ok(userEntity);
    }

    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
