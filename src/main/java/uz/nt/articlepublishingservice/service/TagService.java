package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.Set;

public interface TagService {
    ResponseEntity<Set<Tag>> add(Set<Tag> tagDto);
    ResponseEntity<?> update(Tag tagDto);
    ResponseEntity<?> delete(Integer id);
}
