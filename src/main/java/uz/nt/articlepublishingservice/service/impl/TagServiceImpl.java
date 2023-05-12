package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.repository.TagRepository;
import uz.nt.articlepublishingservice.service.TagService;
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    @Override
    public ResponseEntity<Set<Tag>> add(Set<String> tags) {
        Set<Tag> returnTags = tags.stream()
                .map(tag -> {
                    Optional<Tag> byName = repository.findByName(tag);
                    if(byName.isEmpty()){
                        return repository.save(new Tag(tag));
                    } else {
                        return byName.get();
                    }
                }).collect(Collectors.toSet());
        return ResponseEntity.ok(returnTags);
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
