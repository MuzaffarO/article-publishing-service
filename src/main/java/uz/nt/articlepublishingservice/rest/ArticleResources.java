package uz.nt.articlepublishingservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.LikesDto;
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
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody LikesDto likesDto){
        return articlesService.like(likesDto);
    }
}
