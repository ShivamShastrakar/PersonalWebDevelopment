package com.hands_on.zomato.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "First name is required")
        @Size(max = 35, message = "First name must be at most 35 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must be at most 50 characters")
        String lastName,

        @NotNull(message = "Phone number is required")
        Long phoneNo,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,

        @NotNull(message = "City pin is required")
        Long cityPin,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String emailAddress
) {
}
