package com.org.finfirm.services.impl;

import com.org.finfirm.domains.User;
import com.org.finfirm.dto.UserDto;
import com.org.finfirm.repository.UserRepository;
import com.org.finfirm.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User result = userRepository.save(user);
        UserDto response = new UserDto();
        response.setUserName(result.getUserName());
        response.setPassword(result.getPassword());
        return response;
    }

}
