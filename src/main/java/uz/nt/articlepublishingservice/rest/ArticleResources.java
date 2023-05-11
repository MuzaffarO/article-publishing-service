package uz.nt.articlepublishingservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.service.impl.ArticlesServiceImpl;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleResources {
    private final ArticlesServiceImpl articlesService;
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ArticlesDto articlesDto){
        return articlesService.add(articlesDto);
    }
    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> like(@PathVariable Integer articleId, @RequestParam Integer userId){
        return articlesService.like(articleId,userId);
    }
}
