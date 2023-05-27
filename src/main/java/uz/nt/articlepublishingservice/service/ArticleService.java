package uz.nt.articlepublishingservice.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.model.Articles;

import java.util.List;
public interface ArticleService {
    ResponseEntity<?> add(ArticlesDto articlesDto);
    ResponseEntity<?> update(ArticlesDto articlesDto);
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> get(Integer id);
    ResponseEntity<?> like(Integer articleId);
    ResponseEntity<Page<Articles>> getAll(Integer limit, Integer offset);

    ResponseDto<List<ArticlesDto>> feed();

    ResponseEntity<?> favorite(String favorited);
}
