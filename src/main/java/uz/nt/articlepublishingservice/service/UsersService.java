package uz.nt.articlepublishingservice.service;

import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
}
