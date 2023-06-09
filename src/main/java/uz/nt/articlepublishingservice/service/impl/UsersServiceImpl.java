package uz.nt.articlepublishingservice.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.LoginDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Users;
import uz.nt.articlepublishingservice.repository.UsersRepository;
import uz.nt.articlepublishingservice.security.JwtService;
import uz.nt.articlepublishingservice.service.UsersService;
import uz.nt.articlepublishingservice.service.additional.AppStatusCodes;
import uz.nt.articlepublishingservice.service.mapper.UsersMapper;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.articlepublishingservice.service.additional.AppStatusCodes.*;
import static uz.nt.articlepublishingservice.service.additional.AppStatusMessages.*;


@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService , UserDetailsService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto usersDto) {
        try {
            Optional<Users> byEmail = usersRepository.findByEmail(usersDto.getEmail());
            Optional<Users> byUsername = usersRepository.findByUsername(usersDto.getUsername());

            if (byEmail.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                        .message("User with this email " + usersDto.getEmail() + " already exists!")
                        .build();

            if (byUsername.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                        .message("User with this username " + usersDto.getUsername() + " already exists!")
                        .build();

            Users users = usersMapper.toEntity(usersDto);
            users.setRole("USER");
            usersRepository.save(users);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .data(usersMapper.toDto(users))
                    .message(OK)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .data(usersDto)
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<UsersDto>> getAllUsers() {
        try {
            return ResponseDto.<List<UsersDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(usersRepository.findAll().stream()
                            .map(usersMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<UsersDto>>builder()
                    .code(1)
                    .message("Unexpected error with database: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message("Id is null!")
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }
        try {
            Optional<Users> userOptional = usersRepository.findById(usersDto.getId());
            if (userOptional.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .message("User with ID " + usersDto.getId() + " is not found")
                        .code(NOT_FOUND_ERROR_CODE)
                        .data(usersDto)
                        .build();
            }
            if (usersDto.getEmail() != null) {
                Optional<Users> byEmail = usersRepository.findByEmail(usersDto.getEmail());
                if (byEmail.isPresent() && !userOptional.get().getEmail().equals(usersDto.getEmail()))
                    return ResponseDto.<UsersDto>builder()
                            .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                            .message("User with this email " + usersDto.getEmail() + " already exists!")
                            .build();
            }
            if (usersDto.getUsername() != null) {
                Optional<Users> byUsername = usersRepository.findByUsername(usersDto.getUsername());
                if (byUsername.isPresent() && !userOptional.get().getUsername().equals(usersDto.getUsername()))
                    return ResponseDto.<UsersDto>builder()
                            .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                            .message("User with this username " + usersDto.getUsername() + " already exists!")
                            .build();
            }

            Users updatedUser = editUserInfo(userOptional.get(), usersDto);
            updatedUser.setUpdatedAt(LocalDateTime.now());
            usersRepository.save(updatedUser);

            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(updatedUser))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(1)
                    .message("Unexpected error with database: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<UsersDto> getUserById(Integer id) {
        try {
            Optional<Users> byId = usersRepository.findById(id);
            if (byId.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(usersMapper.toDto(byId.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> deleteUserById(Integer id) {
        try {
            Optional<Users> byId = usersRepository.findById(id);
            if (byId.isPresent()) {
                usersRepository.deleteById(id);
                return ResponseDto.<UsersDto>builder()
                        .success(true)
                        .message(OK)
                        .data(usersMapper.toDto(byId.get()))
                        .build();
            }
            return ResponseDto.<UsersDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }

    public Users editUserInfo(Users user, UsersDto usersDto) {
        user.setEmail(Optional.ofNullable(usersDto.getEmail()).orElse(user.getEmail()));
        user.setBio(Optional.ofNullable(usersDto.getBio()).orElse(user.getBio()));
        user.setUsername(Optional.ofNullable(usersDto.getUsername()).orElse(user.getUsername()));

        return user;
    }

    @Override
    public ResponseDto<UsersDto> follow(Integer id) {
//        UsersDto followerDto = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //Optional<Users> follower = usersRepository.findById(followsDto.getFollower().getId());
//        Optional<Users> followingUser = usersRepository.findById(id);
//        if (followingUser.isEmpty()) {
//            return ResponseDto.<UsersDto>builder()
//                    .code(-2)
//                    .message("Users not found")
//                    .build();
//        }
//        Users follower = usersMapper.toEntity(followerDto);
//        if (follower.getFollows().contains(followingUser.get())) {
//            follower.getFollows().remove(followingUser.get());
//        } else {
//            follower.getFollows().add(followingUser.get());
//        }
//        usersRepository.save(follower);
//        return ResponseDto.<UsersDto>builder()
//                .code(0)
//                .message("OK")
//                .success(true)
//                .data(usersMapper.toDto(followingUser.get()))
//                .build();



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UsersDto)) {
            return ResponseDto.<UsersDto>builder()
                    .code(-1)
                    .message("User not authenticated")
                    .build();
        }

        UsersDto followerDto = (UsersDto) authentication.getPrincipal();
        Optional<Users> followerOptional = usersRepository.findById(followerDto.getId());
        Optional<Users> followingUserOptional = usersRepository.findById(id);

        if (followerOptional.isEmpty() || followingUserOptional.isEmpty()) {
            return ResponseDto.<UsersDto>builder()
                    .code(-2)
                    .message("Users not found")
                    .build();
        }

        Users follower = followerOptional.get();
        Users followingUser = followingUserOptional.get();

        if (follower.getFollows().contains(followingUser)) {
            follower.getFollows().remove(followingUser);
        } else {
            follower.getFollows().add(followingUser);
        }

        usersRepository.save(follower);

        return ResponseDto.<UsersDto>builder()
                .code(0)
                .message("OK")
                .success(true)
                .data(usersMapper.toDto(followingUser))
                .build();
    }


    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByUsername(username);
        if(users.isEmpty()) throw new UsernameNotFoundException("user is not found");

        return usersMapper.toDto(users.get());
    }
    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if(!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .code(OK_CODE)
                .message(OK)
                .data(jwtService.generateToken(users))
                .success(true)
                .build();
    }
}
