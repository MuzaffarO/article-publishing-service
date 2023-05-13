package uz.nt.articlepublishingservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.model.Users;
import uz.nt.articlepublishingservice.repository.ArticlesRepository;
import uz.nt.articlepublishingservice.service.ArticleService;
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;
import uz.nt.articlepublishingservice.service.mapper.ArticlesMapper;
import uz.nt.articlepublishingservice.service.mapper.UsersMapper;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticleService {
    private final ArticlesRepository repository;
    private final ArticlesMapper mapper;
    private final TagServiceImpl tagService;
    private final UsersMapper usersMapper;

    @Override
    public ResponseEntity<?> add(ArticlesDto articlesDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UsersDto)){
            return ResponseEntity.badRequest().body("User not authenticated");

        }
        if(articlesDto.getAuthor().getId() == ((UsersDto) authentication.getPrincipal()).getId()) {
            ResponseEntity<Set<Tag>> add = tagService.add(articlesDto.getTags());
            Articles articles = mapper.toEntity(articlesDto);
            articles.setTags(add.getBody());
            articles.setAuthor(usersMapper.toEntity((UsersDto) authentication.getPrincipal()));
            try {
                Articles save = repository.save(articles);
                log.info("articles add {}", articles.getTitle());
                return ResponseEntity.ok(mapper.toDto(save));
            } catch (Exception e) {
                log.error("articles add {}", e.getMessage());
                return ResponseEntity.ok(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("not allowed user");
        }
    }

    @Override
    public ResponseEntity<?> update(ArticlesDto articlesDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        if(usersDto.getId() != articlesDto.getAuthor().getId()){
            return ResponseEntity.badRequest().body("method not allow");
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        if(id == null){
            log.error("article delete null value");
            return ResponseEntity.badRequest().body("Null value");
        }
        Optional<Articles> byId = repository.findById(id);
        if(byId.isEmpty()){
            log.info("not found id delete article");
            return ResponseEntity.ok("not found");
        }
        if(byId.get().getAuthor().getId() == usersDto.getId()) {
            try {
                repository.delete(byId.get());
                log.info("articles deleted {} ", id);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                return ResponseEntity.ok(e.getMessage());
            }
        }else {
            return ResponseEntity.badRequest().body("not allow");
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
    public ResponseEntity<?> like(Integer articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            UsersDto userDto = (UsersDto) authentication.getPrincipal();
            Users users = usersMapper.toEntity(userDto);
            Optional<Articles> article = repository.findById(articleId);
            if (article.isPresent()) {
                if (article.get().getLikes().contains(users)) {
                    article.get().getLikes().remove(users);
                } else {
                    article.get().getLikes().add(users);
                }
                repository.save(article.get());
                return ResponseEntity.ok(mapper.toDto(article.get()));
            } else {
                return ResponseEntity.badRequest().body("article not found");
            }
        } else{
            return ResponseEntity.ok("User not authenticated");
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
