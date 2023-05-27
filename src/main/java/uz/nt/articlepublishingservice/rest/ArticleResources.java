package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.service.impl.ArticlesServiceImpl;
import uz.nt.articlepublishingservice.service.impl.TagServiceImpl;

import java.util.List;
@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class ArticleResources {
    private final ArticlesServiceImpl articlesService;
    private final TagServiceImpl tagService;

    @Operation(
            summary = "Adds a new article",
            method = "POST",
            description = "Need to send ArticlesDto to this endpoint to create new article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Article info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ArticlesDto articlesDto){
        return articlesService.add(articlesDto);
    }

    @Operation(
            summary = "Gets all articles",
            method = "GET",
            description = "Get all articles",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Article info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Articles not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/get-all")
    public ResponseEntity<Page<Articles>> getAll(@RequestParam Integer limit, @RequestParam Integer offset){
        return articlesService.getAll(limit, offset);
    }

    @Operation(
            summary = "Update article",
            method = "PATCH",
            description = "Need to send ArticleDto to this endpoint to article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Article info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Article not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody ArticlesDto articlesDto){
        return articlesService.update(articlesDto);
    }

    @Operation(
            summary = "Delete a article by article ID",
            method = "DELETE",
            description = "Need to send the article ID to delete the article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Article info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Article not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return articlesService.delete(id);
    }

    @Operation(
            summary = "Gets a article by article ID",
            method = "GET",
            description = "Need to send the article ID to get this article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Article info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Article not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        return articlesService.get(id);
    }

    @Operation(
            summary = "Gets popular tags",
            method = "GET",
            description = "Get all popular tags",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Tags info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Tags not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/popular-tags")
    public ResponseEntity<?> popularTags(){
        return tagService.popularArticles();
    }

    @Operation(
            summary = "Like",
            method = "POST",
            description = "Like the article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Like info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> like(@PathVariable Integer articleId){
        return articlesService.like(articleId);
    }
    @GetMapping("/feed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<List<ArticlesDto>> feed(){
        return articlesService.feed();
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> favorited(@RequestParam String favorited){
        return articlesService.favorite(favorited);
    }
    @GetMapping("/tag")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> getArticleByTag(@RequestParam String tag){
        return articlesService.getArticleByTag(tag);
    }
}
