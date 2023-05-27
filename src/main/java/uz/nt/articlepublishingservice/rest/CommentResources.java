package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/comment")
@SecurityRequirement(name = "Authorization")
public class CommentResources {
    private final CommentService commentService;

    @Operation(
            summary = "Gets all comments",
            method = "GET",
            description = "Get all comments of this article",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Comments not found")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer id){
        return commentService.viewAll(id);
    }

    @Operation(
            summary = "Adds a new comment",
            method = "POST",
            description = "Need to send String comment and article ID to this endpoint to create new comment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<CommentDto> createComment(@RequestParam String comment,@PathVariable Integer id){
        return commentService.addComment(id, comment);
    }

    @Operation(
            summary = "Delete a comment by comment ID",
            method = "DELETE",
            description = "Need to send the comment ID to delete the comment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Comment not found")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer id){
        return commentService.removeComment(id);
    }


}
