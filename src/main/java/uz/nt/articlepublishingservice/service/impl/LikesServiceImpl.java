package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
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
        Likes like = likesMapper.toEntity(likesDto);
        Optional<Likes> likesOptional = likesRepository.findByArticleAndUser(like.getArticle(), like.getUser());
        if (likesOptional.isEmpty()) {
            likesRepository.save(like);
            return ResponseDto.<LikesDto>builder()
                    .data(likesMapper.toDto(like))
                    .build();
        } else {
            return null;
        }
    }


}
