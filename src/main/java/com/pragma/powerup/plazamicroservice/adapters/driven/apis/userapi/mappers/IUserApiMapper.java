package com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.mappers;

import com.pragma.powerup.plazamicroservice.adapters.driven.apis.userapi.dto.UserApiDto;
import com.pragma.powerup.plazamicroservice.domain.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserApiMapper {

    @Mapping(target = "id_role", source = "idRole")
    UserApiDto toUserDto(User user);

    @Mapping(target = "idRole", source = "id_role")
    User toUser(UserApiDto userApiDto);

}
