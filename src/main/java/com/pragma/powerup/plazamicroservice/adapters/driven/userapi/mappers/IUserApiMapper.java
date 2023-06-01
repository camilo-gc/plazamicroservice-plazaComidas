package com.pragma.powerup.plazamicroservice.adapters.driven.userapi.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.userapi.entity.UserDto;
import com.pragma.powerup.plazamicroservice.domain.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserApiMapper {

    @Mapping(target = "id_role", source = "idRole")
    UserDto toUserDto(User user);

    @Mapping(target = "idRole", source = "id_role")
    User toUser(UserDto userDto);

}
