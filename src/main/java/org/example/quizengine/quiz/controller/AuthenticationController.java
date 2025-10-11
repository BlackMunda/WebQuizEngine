package org.example.quizengine.quiz.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quizengine.quiz.entity.AppUser;
import org.example.quizengine.quiz.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Requestbody{
        @Email
        String email;

        @NotEmpty
        @NotNull
        @Size(min = 5)
        String password;
    }

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AppUserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public void register(@RequestBody @Valid Requestbody requestbody){

        if (repository.findAppUserByUsername(requestbody.getEmail().split("@")[0]).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already taken");
        }
        AppUser user = new AppUser();
        user.setUsername(requestbody.getEmail().split("@")[0]);
        user.setPassword(passwordEncoder.encode(requestbody.getPassword()));
        user.setAuthority("ROLE_USER");
        repository.save(user);
        System.out.println("user created");
    }

    @PostMapping("/api/admin")
    public String admin(){
        return "YES YOU ARE ADMINO!!!!";
    }

    @GetMapping("/api/me")
    public String details(@AuthenticationPrincipal AppUser user) {
        return "Welcome " + user.getUsername() + " with role " + user.getAuthority();
    }

}
