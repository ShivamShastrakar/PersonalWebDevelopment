package com.hands_on.zomato.Mapper;

import com.hands_on.zomato.DTO.UserRequestDTO;
import com.hands_on.zomato.DTO.UserResponseDTO;
import com.hands_on.zomato.Entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDTO request) {
        return UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNo(request.phoneNo())
                .address(request.address())
                .cityPin(request.cityPin())
                .emailAddress(request.emailAddress())
                .build();
    }

    public UserResponseDTO toResponse(UserEntity user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNo(),
                user.getAddress(),
                user.getCityPin(),
                user.getEmailAddress()
        );
    }
}
