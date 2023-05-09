package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.TagDto;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.repository.TagRepository;
import uz.nt.articlepublishingservice.service.TagService;
import uz.nt.articlepublishingservice.service.mapper.TagMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper mapper;
    @Override
    public ResponseEntity<?> add(TagDto tagDto) {
        Tag tag = mapper.toEntity(tagDto);
        try {
            Optional<Tag> byId = tagRepository.findById(tagDto.getId());
            if(byId.isEmpty()){
                tagRepository.save(tag);
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.ok(false);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
