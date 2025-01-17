package com.org.finfirm.Config;

import com.org.finfirm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.org.finfirm.domains.User byUsername = userRepository.findByUserName(username);

        if (byUsername == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Add authorities/roles here
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        // Return the user details as UserDetails
        return User.withUsername(byUsername.getUserName())
                .password(byUsername.getPassword())
                .authorities(authorities)
                .build();
    }
}
