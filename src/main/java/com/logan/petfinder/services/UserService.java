package com.logan.petfinder.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import com.logan.petfinder.models.User;


public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
