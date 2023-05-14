package uz.nt.articlepublishingservice.rest;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer id){
        return commentService.viewAll(id);
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<CommentDto> createComment(@RequestParam String comment,@PathVariable Integer id){
        return commentService.addComment(id, comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer id){
        return commentService.removeComment(id);
    }


}
