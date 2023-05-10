package uz.nt.articlepublishingservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.service.impl.FollowsServiceImpl;
import uz.nt.articlepublishingservice.service.impl.LikesServiceImpl;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesResources {
    private final LikesServiceImpl likesService;
    private final FollowsServiceImpl followsService;

    @PostMapping("like")
    public ResponseDto<LikesDto> addLike(@RequestBody LikesDto likesDto) {
        return likesService.like(likesDto);
    }

    @PostMapping("follow")
    public ResponseDto<FollowsDto> addLike(@RequestBody FollowsDto followsDto) {
        return followsService.follow(followsDto);
    }

}
