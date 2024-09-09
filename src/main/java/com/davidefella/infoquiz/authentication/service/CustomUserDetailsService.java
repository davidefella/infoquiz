package com.davidefella.infoquiz.authentication.service;

import com.davidefella.infoquiz.repository.UserInfoQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoQuizRepository userInfoQuizRepository;

    /*
    * TODO: Capire con test se necessario o meno
    * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userInfoQuizRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}
