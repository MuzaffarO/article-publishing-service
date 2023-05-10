package uz.nt.articlepublishingservice.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;

    @PostMapping("sign-up")
    public ResponseDto<UsersDto> addUser(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @GetMapping("user-by-id")
    public ResponseDto<UsersDto> getUserById(@RequestParam Integer id) {
        return usersService.getUserById(id);
    }

    @GetMapping("user-list")
    public ResponseDto<List<UsersDto>> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PatchMapping("edit-user")
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto) {
        return usersService.updateUser(usersDto);
    }

    @DeleteMapping("delete-by-id")
    public ResponseDto<UsersDto> deleteUserById(@RequestParam Integer id) {
        return usersService.deleteUserById(id);
    }
}
