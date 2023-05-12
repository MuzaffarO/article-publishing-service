package uz.nt.articlepublishingservice.service;

import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;

import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
    ResponseDto<UsersDto> getUserById(Integer id);

    ResponseDto<List<UsersDto>> getAllUsers();

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> deleteUserById(Integer id);

    ResponseDto<UsersDto> follow(FollowsDto followsDto);
}
