package com.hands_on.zomato.DTO;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        Long phoneNo,
        String address,
        Long cityPin,
        String emailAddress
) {
}
