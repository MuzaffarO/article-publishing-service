package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Users;

@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users> {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Mapping(target = "password",expression = "java(passwordEncoder.encode((usersDto.getPassword())))")
    public abstract Users toEntity(UsersDto usersDto);
}
