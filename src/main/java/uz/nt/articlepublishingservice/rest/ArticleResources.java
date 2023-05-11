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
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return articlesService.getAll();
    }
    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody ArticlesDto articlesDto){
        return articlesService.update(articlesDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return articlesService.delete(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        return articlesService.get(id);
    }
    @GetMapping("/popular-tags")
    public ResponseEntity<?> popularTags(){
        return articlesService.popularArticles();
    }
    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> like(@PathVariable Integer articleId, @RequestParam Integer userId){
        return articlesService.like(articleId,userId);
    }
}
