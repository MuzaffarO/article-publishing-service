package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Users;
import uz.nt.articlepublishingservice.repository.UsersRepository;
import uz.nt.articlepublishingservice.service.UsersService;
import uz.nt.articlepublishingservice.service.additional.AppStatusCodes;
import uz.nt.articlepublishingservice.service.mapper.UsersMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    @Override
    public ResponseDto<UsersDto> addUser(UsersDto usersDto) {
        return null;
    }
}
