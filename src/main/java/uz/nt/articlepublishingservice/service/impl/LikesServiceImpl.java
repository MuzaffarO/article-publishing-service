package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.model.Follows;
import uz.nt.articlepublishingservice.model.Likes;
import uz.nt.articlepublishingservice.repository.LikesRepository;
import uz.nt.articlepublishingservice.service.LikesService;
import uz.nt.articlepublishingservice.service.mapper.LikesMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final LikesMapper likesMapper;
    @Override
    public ResponseDto<LikesDto> like(LikesDto likesDto) {
        Likes follows = likesMapper.toEntity(likesDto);
        Optional<Follows> followsOptional = likesRepository.findByFollowerAndUser(follows.getFollower(), follows.getUser());
        if (followsOptional.isEmpty()) {
            followsRepository.save(follows);
            return ResponseDto.<FollowsDto>builder()
                    .data(followsMapper.toDto(follows))
                    .build();
        } else {
            return null;
        }
    }
}
