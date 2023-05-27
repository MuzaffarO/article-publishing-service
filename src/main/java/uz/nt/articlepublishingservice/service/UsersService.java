package uz.nt.articlepublishingservice.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.LoginDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;

import java.net.http.HttpRequest;
import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
    ResponseDto<UsersDto> getUserById(Integer id);

    ResponseDto<List<UsersDto>> getAllUsers();

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> deleteUserById(Integer id);

    ResponseDto<UsersDto> follow(Integer id);
    ResponseDto<String> login(LoginDto loginDto);
}
