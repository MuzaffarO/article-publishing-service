package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import uz.nt.articlepublishingservice.dto.TagDto;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.Set;

public interface TagService {
    ResponseEntity<Set<Tag>> add(Set<TagDto> tagDto);
    ResponseEntity<?> update(TagDto tagDto);
    ResponseEntity<?> delete(Integer id);
}
