package org.example.springsecurityjwt.controller;

import org.example.springsecurityjwt.entity.AuthRequest;
import org.example.springsecurityjwt.entity.AuthResp;
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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
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
    @PreAuthorize("hasAuthority('USER'),hasAuthority('ADMIN')")
    public  ResponseEntity<Optional<UserEntity>> findAllUsers(@PathVariable String userName) {
        return userService.findByName(userName);
    }

    @GetMapping("/user/deleteByName/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUserByName(@PathVariable int id) {
        System.out.println("delete");
        userService.deleteUserByName(id);
    }

    @PostMapping("/user/editUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserEntity editUser(@RequestBody UserEntity userEntity) {
        return userService.editUser(userEntity);
    }

    @PostMapping("/authenticate")
    public AuthResp authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("auth");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            AuthResp authResp=new AuthResp();
            authResp.setUsername(authRequest.getUsername());
            authResp.setTocken(jwtService.generateToken(authRequest.getUsername()));
            return authResp;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

}
