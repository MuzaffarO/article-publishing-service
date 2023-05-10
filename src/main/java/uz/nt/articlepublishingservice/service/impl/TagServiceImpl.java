package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.TagDto;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.repository.TagRepository;
import uz.nt.articlepublishingservice.service.TagService;
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;
import uz.nt.articlepublishingservice.service.mapper.TagMapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;
    @Override
    public ResponseEntity<Set<Tag>> add(Set<TagDto> tagDto) {
        Set<Tag> tags = new HashSet<>();
        for (TagDto dto : tagDto) {
            Optional<Tag> byName = repository.findByName(dto.getName());
            if(byName.isEmpty()){
                Tag savedTag = repository.save(mapper.toEntity(dto));
                tags.add(savedTag);
            }else{
                tags.add(byName.get());
            }
        }
        return ResponseEntity.ok(tags);
    }

    @Override
    public ResponseEntity<?> update(TagDto tagDto) {
        if(tagDto.getId() == null){
            return ResponseEntity.ok(AppStatusMessages.NULL_VALUE);
        }
        Optional<Tag> byId = repository.findById(tagDto.getId());
        if(byId.isEmpty()){
            return ResponseEntity.ok(AppStatusMessages.NOT_FOUND);
        }
        Tag tag = byId.get();
        if(tagDto.getName() != null){
            tag.setName(tagDto.getName());
        }
        try {
            repository.save(tag);
            return ResponseEntity.ok(mapper.toDto(tag));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        if(id == null){
            return ResponseEntity.ok(AppStatusMessages.NULL_VALUE);
        }
        Optional<Tag> byId = repository.findById(id);
        if (byId.isEmpty()){
            return ResponseEntity.ok(AppStatusMessages.NOT_FOUND);
        }
        repository.delete(byId.get());
        return ResponseEntity.ok(true);
    }
    public ResponseEntity<?> popularArticles() {
        return ResponseEntity.ok(repository.getPopularTags());
    }
}
