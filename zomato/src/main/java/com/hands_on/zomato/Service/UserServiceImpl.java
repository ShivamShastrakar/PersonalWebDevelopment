package com.hands_on.zomato.Service;

import com.hands_on.zomato.DTO.UserRequestDTO;
import com.hands_on.zomato.DTO.UserResponseDTO;
import com.hands_on.zomato.Entity.UserEntity;
import com.hands_on.zomato.Mapper.UserMapper;
import com.hands_on.zomato.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        UserEntity user = userMapper.toEntity(request);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return List.of();
    }

    @Override
    public UserResponseDTO getUserById(UserRequestDTO requestDTO) {
        return null;
    }
}