package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.ArticlesDto;

public interface ArticleService {
    ResponseEntity<?> add(ArticlesDto articlesDto);
    ResponseEntity<?> update(ArticlesDto articlesDto);
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> get(Integer id);
    ResponseEntity<?> getAll();
    ResponseEntity<?> like(LikesDto LikesDto);
}
