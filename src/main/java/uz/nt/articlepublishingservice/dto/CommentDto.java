package uz.nt.articlepublishingservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Users;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    @NotBlank
    private String description;
    private Date createdDate;
    @NotNull
//    @Size(min=4, message= "whatever")
    private Users users;
    @NotNull
//    @Size(min=4, message= "whatever")
    private Articles articles;
}
