package uz.nt.articlepublishingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDto {
    private ArticlesDto article;
    private UsersDto user;
}
