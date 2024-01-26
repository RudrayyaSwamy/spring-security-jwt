package org.example.springsecurityjwt.controller;

import org.example.springsecurityjwt.entity.AuthRequest;
import org.example.springsecurityjwt.entity.UserEntity;
import org.example.springsecurityjwt.service.JwtService;
import org.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

   @Autowired
    private UserService userService;

   @Autowired
    private JwtService jwtService;

   @Autowired
   private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addUser")
    public UserEntity addUser(@RequestBody UserEntity userEntity) {
        return userService.save(userEntity);
    }

    @GetMapping("/user/findAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserEntity>> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/findByName/{userName}")
    @PreAuthorize("hasAuthority('USER')")
    public  ResponseEntity<Optional<UserEntity>> findAllUsers(@PathVariable String userName) {
        return userService.findByName(userName);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

}
