package com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserApiDto {

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
