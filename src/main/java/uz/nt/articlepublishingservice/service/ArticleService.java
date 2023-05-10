package uz.nt.articlepublishingservice.service;

import org.springframework.http.ResponseEntity;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.LikesDto;

public interface ArticleService {
    ResponseEntity<?> add(ArticlesDto articlesDto);
    ResponseEntity<?> update(ArticlesDto articlesDto);
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> get(Integer id);
    ResponseEntity<?> like(LikesDto LikesDto);
}
