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
        Optional<Users> byEmail = usersRepository.findByEmail(usersDto.getEmail());
        Optional<Users> byUsername = usersRepository.findByUsername(usersDto.getUsername());

        if (byEmail.isPresent() || byUsername.isPresent())
            return ResponseDto.<UsersDto>builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message("User with this email " + usersDto.getEmail() + " already exists!")
                    .build();

        Users users = userMapper.toEntity(usersDto);
        users.setCreatedAt(LocalDateTime.now());
        usersRepository.save(users);

        return ReplyDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
                .message(OK)
                .build();
    }
}
