package uz.nt.articlepublishingservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.service.UsersService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;

    public ResponseDto<UsersDto> addUser(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);

    }
}

