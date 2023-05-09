package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import uz.nt.articlepublishingservice.dto.TagDto;

public interface TagService {
    ResponseEntity<?> add(TagDto tagDto);
}
