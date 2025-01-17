package com.org.finfirm.contollers;

import com.org.finfirm.dto.UserDto;
import com.org.finfirm.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/save-user")
public class UserController {
    @Autowired
    private UserServices userServices;

    public UserController() {
        System.out.println("UserController initialized!");
    }


    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDTO ) {
        UserDto userDto = userServices.saveUser(userDTO);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> saveUser() {
        // Save user logic
        return ResponseEntity.ok("User saved successfully");
    }

}
