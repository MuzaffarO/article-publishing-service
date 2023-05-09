package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.model.Comment;
import uz.nt.articlepublishingservice.repository.CommentRepository;
import uz.nt.articlepublishingservice.service.CommentService;
import uz.nt.articlepublishingservice.service.mapper.CommentMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public ResponseDto<List<CommentDto>> viewAll() {
        List<CommentDto> commentList = commentRepository.findAll().stream().map(commentMapper:: toDto).toList();

        return ResponseDto.<List<CommentDto>>builder()
                .message("OK")
                .code(1)
                .data(commentList)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<CommentDto> addComment(CommentDto commentDto) {
        try {
            return ResponseDto.<CommentDto>builder()
                    .data(commentMapper.toDto(
                            commentRepository.save(
                                    commentMapper.toEntity(commentDto)
                            )
                    ))
                    .message("OK")
                    .code(1)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CommentDto>builder()
                    .code(-1)
                    .success(false)
                    .message("Database error" + ':' + e.getMessage())
                    .data(commentDto)
                    .build();
        }
    }

    @Override
    public ResponseDto<CommentDto> removeComment(Integer id) {
        Optional<Comment> commentById = commentRepository.findById(id);
        if (commentById.isPresent()){
            Comment comment = commentById.get();
            commentRepository.deleteById(id);
            return ResponseDto.<CommentDto>builder()
                    .message("OK")
                    .code(1)
                    .success(true)
                    .data(commentMapper.toDto(comment))
                    .build();
        }
        return ResponseDto.<CommentDto>builder()
                .message("Comment " +id+ " is not available")
                .success(false)
                .code(-1)
                .build();
    }
}
