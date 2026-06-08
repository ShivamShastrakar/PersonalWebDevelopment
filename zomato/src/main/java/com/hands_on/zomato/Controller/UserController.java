package com.hands_on.zomato.Controller;

import com.hands_on.zomato.DTO.UserRequestDTO;
import com.hands_on.zomato.DTO.UserResponseDTO;
import com.hands_on.zomato.Entity.UserEntity;
import com.hands_on.zomato.Service.UserServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserServiceImpl userService;

    UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("create/")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        try{
            userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("users/")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }
}