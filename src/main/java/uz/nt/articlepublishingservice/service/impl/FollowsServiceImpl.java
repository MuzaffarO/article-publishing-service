package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.model.Follows;
import uz.nt.articlepublishingservice.repository.FollowsRepository;
import uz.nt.articlepublishingservice.service.FollowsService;
import uz.nt.articlepublishingservice.service.mapper.FollowsMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowsServiceImpl implements FollowsService {
    private final FollowsRepository followsRepository;
    private final FollowsMapper followsMapper;

    @Override
    public ResponseDto<FollowsDto> follow(FollowsDto followsDto) {
        Follows follows = followsMapper.toEntity(followsDto);
        Optional<Follows> followsOptional = followsRepository.findByFollowerAndUser(follows.getFollower(), follows.getUser());
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
