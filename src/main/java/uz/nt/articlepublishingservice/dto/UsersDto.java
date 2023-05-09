package uz.nt.articlepublishingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    private String username;
    private String password;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
