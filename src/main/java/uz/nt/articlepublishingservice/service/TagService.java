package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.Set;

public interface TagService {
    ResponseEntity<Set<Tag>> add(Set<String> tagDto);
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> get(String name);
}
