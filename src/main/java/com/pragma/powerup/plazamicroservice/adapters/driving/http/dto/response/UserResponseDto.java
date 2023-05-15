package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String surname;
    private String dni;
    private String phone;
    private String birthDate;
    private String email;
    private String password;
    private Long id_role;
}
