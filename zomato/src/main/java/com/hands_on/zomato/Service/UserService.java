package com.hands_on.zomato.Service;

import com.hands_on.zomato.DTO.UserRequestDTO;
import com.hands_on.zomato.DTO.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getUsers();

    UserResponseDTO getUserById(UserRequestDTO requestDTO);
}