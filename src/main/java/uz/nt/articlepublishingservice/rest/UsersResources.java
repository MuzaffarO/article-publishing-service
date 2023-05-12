package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.LoginDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.service.UsersService;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping("/sign-up")
    public ResponseDto<UsersDto> addUser(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getUserById(id);
    }
    @GetMapping("user-list")
    public ResponseDto<List<UsersDto>> getAllUsers(){
        return usersService.getAllUsers();
    }
    @PatchMapping("edit-user")
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
    @DeleteMapping("/{id}")
    public ResponseDto<UsersDto> deleteUserById(@PathVariable Integer id){
        return usersService.deleteUserById(id);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping("/follow/{id}")
    public ResponseDto<UsersDto> follow(@PathVariable Integer id){
        return usersService.follow(id);
    }
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }
}

