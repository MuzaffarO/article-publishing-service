package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Users;
@Mapper(componentModel = "spring")
public interface UsersMapper extends CommonMapper<UsersDto, Users> {
}
