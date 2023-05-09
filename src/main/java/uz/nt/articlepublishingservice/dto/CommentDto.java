package uz.nt.articlepublishingservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
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
    private Users users;
    @NotNull
    private Articles articles;
}
