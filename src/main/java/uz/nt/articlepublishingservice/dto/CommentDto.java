package uz.nt.articlepublishingservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Users;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Integer id;
    @NotBlank
    private String description;
    private Date createdDate;
    private UsersDto users;
//    @NotNull
//    private Articles articles;
}