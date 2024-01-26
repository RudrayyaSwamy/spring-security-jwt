package org.example.springsecurityjwt.config;

import org.example.springsecurityjwt.entity.UserEntity;
import org.example.springsecurityjwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserInfoSecurityServiceConfig implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfoConfig loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity=userRepository.findByName(username);
        return userEntity.map(UserInfoConfig::new)
                .orElseThrow(() ->new UsernameNotFoundException("User not found" +  username));

    }
}
