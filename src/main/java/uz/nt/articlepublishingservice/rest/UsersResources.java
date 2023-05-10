package uz.nt.articlepublishingservice.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;

@RestController
@RequestMapping("/user")
public class UsersResources {
    @PostMapping()
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }
}

