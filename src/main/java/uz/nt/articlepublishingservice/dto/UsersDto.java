package uz.nt.articlepublishingservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @NotNull(message = "Username cannot be null!")
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotNull(message = "Password cannot be null!")
    @NotBlank(message = "Password cannot be empty!")
    private String password;
    @NotNull(message = "Password cannot be null!")
    @NotBlank(message = "Password cannot be empty!")
    @Email(message = "Email must be valid!")
    private String email;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
