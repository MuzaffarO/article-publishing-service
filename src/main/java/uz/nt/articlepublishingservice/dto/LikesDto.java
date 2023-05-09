package uz.nt.articlepublishingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDto {
    private Integer id;
    private Integer articleId;
    private Integer userId;
}
