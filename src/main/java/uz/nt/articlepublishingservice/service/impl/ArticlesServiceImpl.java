package uz.nt.articlepublishingservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.repository.ArticlesRepository;
import uz.nt.articlepublishingservice.repository.TagRepository;
import uz.nt.articlepublishingservice.service.ArticleService;
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;
import uz.nt.articlepublishingservice.service.mapper.ArticlesMapper;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticleService {
    private final ArticlesRepository repository;
    private final ArticlesMapper mapper;
    private final TagServiceImpl tagService;
    private final TagRepository tagRepository;
    @Override
    public ResponseEntity<?> add(ArticlesDto articlesDto) {
        ResponseEntity<Set<Tag>> add = tagService.add(articlesDto.getTags());
        Articles articles = mapper.toEntity(articlesDto);
        articles.setTags(add.getBody());
        try {
            Articles save = repository.save(articles);
            log.info("articles add {}",articles.getTitle());
            return ResponseEntity.ok(mapper.toDto(save));
        }catch (Exception e){
            log.error("articles add {}",e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(ArticlesDto articlesDto) {
        if(articlesDto.getId() == null){
            return ResponseEntity.badRequest().body("id is null");
        }
        Optional<Articles> byId = repository.findById(articlesDto.getId());
        if(byId.isEmpty()){
            return ResponseEntity.ok("Not found");
        }
        try {
            Articles uparticles = byId.get();
            if(articlesDto.getTitle() != null){
                uparticles.setTitle(articlesDto.getTitle());
            } if(articlesDto.getAbout() != null){
                uparticles.setAbout(articlesDto.getAbout());
            }
            repository.save(uparticles);
            return ResponseEntity.ok(mapper.toDto(uparticles));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        if(id == null){
            log.error("article delete null value");
            return ResponseEntity.badRequest().body("Null value");
        }
        Optional<Articles> byId = repository.findById(id);
        if(byId.isEmpty()){
            log.info("not found id delete article");
            return ResponseEntity.ok("not found");
        }
        try {
            repository.delete(byId.get());
            log.info("articles deleted {} ",id);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        if(id == null){
            return ResponseEntity.badRequest().body(AppStatusMessages.NULL_VALUE);
        }
        Optional<Articles> byId = repository.findById(id);
        if(byId.isEmpty()){
            return ResponseEntity.ok(AppStatusMessages.NOT_FOUND);
        }
        try {
            return ResponseEntity.ok(mapper.toDto(byId.get()));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(repository.findAll());
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
