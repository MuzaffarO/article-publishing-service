package uz.nt.articlepublishingservice.service.impl;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.model.Users;
import uz.nt.articlepublishingservice.repository.ArticleRepositoryImpl;
import uz.nt.articlepublishingservice.repository.ArticlesRepository;
import uz.nt.articlepublishingservice.repository.UsersRepository;
import uz.nt.articlepublishingservice.service.ArticleService;
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;
import uz.nt.articlepublishingservice.service.mapper.ArticlesMapper;
import uz.nt.articlepublishingservice.service.mapper.UsersMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticleService {
    private final ArticlesRepository repository;
    private final ArticlesMapper mapper;
    private final TagServiceImpl tagService;
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    private final ArticleRepositoryImpl articleRepositoryImpl;


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
            Users users = usersMapper.toEntityPassword(userDto);
            Optional<Articles> article = repository.findById(articleId);
            if (article.isPresent()) {
                Articles articles = article.get();
                if (articles.getLikes().contains(users)) {
                    users.getLikes().remove(articles);
                } else {
                    users.getLikes().add(articles);
                }
                usersRepository.save(users);
                return ResponseEntity.ok(mapper.toDto(articles));
            } else {
                return ResponseEntity.badRequest().body("article not found");
            }
        } else{
            return ResponseEntity.ok("User not authenticated");
        }
    }

    @Override
    public ResponseEntity<Page<Articles>> getAll(Integer limit, Integer offset) {
//        try {
            PageRequest pageRequest = PageRequest.of(limit,offset);
            Page<Articles> articles = articleRepositoryImpl.articlesWithPagination(limit, offset);
            return ResponseEntity.ok(articles);

//        }catch (Exception e){
//            return ResponseEntity.ok(Page.empty());
//        }
    }

    @Override
    public ResponseDto<List<ArticlesDto>> feed() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            return ResponseDto.<List<ArticlesDto>>builder()
                    .message("not authenticated")
                    .success(true)
                    .build();
        }
        UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        Optional<Users> users = usersRepository.findById(usersDto.getId());
        List<Users> follows = users.get().getFollows();
        List<Articles> collect = follows.stream()
                .flatMap(us -> repository.findAllByAuthor(us).stream())
                .collect(Collectors.toList());
        return ResponseDto.<List<ArticlesDto>>builder()
                .data(collect.stream().map(ar->mapper.toDto(ar)).toList())
                .message(AppStatusMessages.OK)
                .success(true)
                .build();
    }

    @Override
    public ResponseEntity<?> favorite(String favorited) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        if(!authentication.isAuthenticated() || !usersDto.getUsername().equals(favorited)){
            return ResponseEntity.badRequest().body("not allow");
        }
        Optional<Users> byId = usersRepository.findById(usersDto.getId());

        return ResponseEntity.ok(byId.get().getLikes().stream().map(articles -> mapper.toDto(articles)).toList());
    }

    public ResponseEntity<?> getArticleByTag(String tag) {
        ResponseEntity<?> responseEntity = tagService.get(tag);
        if(responseEntity.getBody().equals(AppStatusMessages.NOT_FOUND)){
            return ResponseEntity.badRequest().body("bad request");
        }
        List<Articles> allByTags = repository.findAllByTags((Tag) responseEntity.getBody());

        return ResponseEntity.ok(allByTags.stream().map(articles -> mapper.toDto(articles)).toList());
    }
}
