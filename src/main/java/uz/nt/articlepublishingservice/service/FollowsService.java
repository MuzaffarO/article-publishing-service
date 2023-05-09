package uz.nt.articlepublishingservice.service;

import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;

public interface FollowsService {
    ResponseDto<FollowsDto> follow(FollowsDto followsDto);

}
