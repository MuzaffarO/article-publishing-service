package uz.nt.articlepublishingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowsDto {
    private UsersDto user;
    private UsersDto follower;
}
