package org.example.quizengine.quiz.service;

import org.example.quizengine.quiz.entity.AppUser;
import org.example.quizengine.quiz.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    AppUserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findAppUserByUsername(username).orElseThrow();
    }
}
