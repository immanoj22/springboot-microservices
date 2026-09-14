package com.authService.authService.User.service;

import com.authService.authService.User.Exception.UserNotFoundException;
import com.authService.authService.User.Models.User;
import com.authService.authService.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSevice implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new UserNotFoundException("user not found");
        }

        return new UserPrinciple(user);
    }
}
