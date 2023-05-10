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
@RequestMapping("comment")
public class CommentResources {
    private final CommentService commentService;

    @GetMapping
    public ResponseDto<List<CommentDto>> getAll(){
        return commentService.viewAll();
    }
    @PostMapping("/add")
    public ResponseDto<CommentDto> createComment(@RequestBody @Valid CommentDto commentDto){
        return commentService.addComment(commentDto);
    }

    @DeleteMapping("/delete")
    public ResponseDto<CommentDto> deleteComment(@RequestParam Integer id){
        return commentService.removeComment(id);
    }


}