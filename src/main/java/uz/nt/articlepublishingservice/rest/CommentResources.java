package uz.nt.articlepublishingservice.rest;

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

    @GetMapping
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer article_id){
        return commentService.viewAll(article_id);
    }
    @PostMapping
    public ResponseDto<CommentDto> createComment(@RequestParam @Valid String comment, @PathVariable Integer article_id){
        return commentService.addComment(comment,article_id);
    }

    @DeleteMapping("{comment_id}")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer comment_id, @PathVariable String article_id){
        return commentService.removeComment(comment_id);
    }


}
