package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    /**
     * http://localhost:8080/swagger-ui/index.html - SWAGGER URL
     **/

    @Operation(
            summary = "Adds a new user",
            method = "POST",
            description = "Need to send UserDto to this endpoint to create new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping("/sign-up")
    public ResponseDto<UsersDto> addUser(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @Operation(
            summary = "Gets a user by user ID",
            method = "GET",
            description = "Need to send the user ID to get this user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getUserById(id);
    }

    @Operation(
            summary = "Gets all user",
            method = "GET",
            description = "Get all users",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping
    public ResponseDto<List<UsersDto>> getAllUsers(){
        return usersService.getAllUsers();
    }

    @Operation(
            summary = "Update user",
            method = "PATCH",
            description = "Need to send UserDto to this endpoint to user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @Operation(
            summary = "Delete a user by user ID",
            method = "DELETE",
            description = "Need to send the user ID to delete the user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseDto<UsersDto> deleteUserById(@PathVariable Integer id){
        return usersService.deleteUserById(id);
    }

    @Operation(
            summary = "Follow user",
            method = "POST",
            description = "Need to send user ID to this endpoint to follow a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping("/follow/{id}")
    public ResponseDto<UsersDto> follow(@PathVariable Integer id){
        return usersService.follow(id);
    }

    @Operation(
            summary = "Get token",
            method = "POST",
            description = "Need to send LoginDto (username, password) to this endpoint to get a token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Username or password incorrect")
            }
    )
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }
}

