package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.service.impl.ArticlesServiceImpl;
import uz.nt.articlepublishingservice.service.impl.TagServiceImpl;

@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class ArticleResources {
    private final ArticlesServiceImpl articlesService;
    private final TagServiceImpl tagService;
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ArticlesDto articlesDto){
        return articlesService.add(articlesDto);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        return articlesService.getAll();
    }
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody ArticlesDto articlesDto){
        return articlesService.update(articlesDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return articlesService.delete(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        return articlesService.get(id);
    }
    @GetMapping("/popular-tags")
    public ResponseEntity<?> popularTags(){
        return tagService.popularArticles();
    }
    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> like(@PathVariable Integer articleId){
        return articlesService.like(articleId);
    }
}
