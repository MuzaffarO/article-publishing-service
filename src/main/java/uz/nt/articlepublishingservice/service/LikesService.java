package uz.nt.articlepublishingservice.service;

import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;

public interface LikesService {
    ResponseDto<LikesDto> like(LikesDto likesDto);
}
