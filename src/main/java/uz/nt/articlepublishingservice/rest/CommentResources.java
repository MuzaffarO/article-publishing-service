package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("articles/{article_id}/comment")
public class CommentResources {
    private final CommentService commentService;

    @Operation(
            summary = "View all comments that article_id = ?",
            method = "getAll",
            description = "Need to send article_id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @GetMapping
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer article_id){
        return commentService.viewAll(article_id);
    }

    @Operation(
            summary = "Add a new comment that article_id = ?",
            method = "createComment",
            description = "Need to send article_id, user_id and comment description",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping
    public ResponseDto<CommentDto> createComment(@RequestParam @Valid String comment, @PathVariable Integer article_id){
        return commentService.addComment(comment,article_id);
    }

    @Operation(
            summary = "Delete",
            method = "deleteComment",
            description = "Delete comment by comment_id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Comment info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Comment not found")}
    )
    @DeleteMapping("{comment_id}")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer comment_id, @PathVariable String article_id){
        return commentService.removeComment(comment_id);
    }


}
