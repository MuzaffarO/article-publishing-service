package uz.nt.articlepublishingservice.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @NotNull @Negative
    private Integer userId;
    @NotNull @Negative
    private Integer articleId;
}
