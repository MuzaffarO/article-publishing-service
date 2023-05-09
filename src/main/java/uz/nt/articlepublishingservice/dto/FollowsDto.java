package uz.nt.articlepublishingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowsDto {
    private Integer id;
    private UsersDto user;
    private UsersDto follower;
}
