package com.miromax.cinema.mappers;

import com.miromax.cinema.dtos.UserDto;
import com.miromax.cinema.entities.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "registeredAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    User toUserEntity(UserDto userDto);
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "password", source = "passwordHash")
    @InheritConfiguration
    UserDto toUserDto(User user);

    default Page<UserDto> toUserDtoPage(Page<User> userPage) {
        return userPage.map(this::toUserDto);
    }
}