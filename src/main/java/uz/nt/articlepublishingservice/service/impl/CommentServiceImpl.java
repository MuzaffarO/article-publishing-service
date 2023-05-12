package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.dto.UsersDto;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Comment;
import uz.nt.articlepublishingservice.repository.ArticlesRepository;
import uz.nt.articlepublishingservice.repository.CommentRepository;
import uz.nt.articlepublishingservice.service.CommentService;
import uz.nt.articlepublishingservice.service.additional.AppStatusCodes;
import uz.nt.articlepublishingservice.service.mapper.CommentMapper;
import uz.nt.articlepublishingservice.service.mapper.UsersMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ArticlesRepository articlesRepository;
    private final UsersMapper usersMapper;

    @Override
    public ResponseDto<List<CommentDto>> viewAll(Integer id) {
        List<CommentDto> commentList = commentRepository.findAll().stream().map(commentMapper:: toDto).toList();

        return ResponseDto.<List<CommentDto>>builder()
                .message("OK")
                .code(AppStatusCodes.OK_CODE)
                .data(commentList)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<CommentDto> addComment(String comment, Integer article_id) {
        UsersDto principal = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Articles> article = articlesRepository.findById(article_id);
        if(article.isEmpty()){
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .message("article not found")
                    .build();
        }
        Comment commentSave = Comment.builder()
                .users(usersMapper.toEntity(principal))
                .description(comment)
                .articles(article.get())
                .build();
        try {
            return ResponseDto.<CommentDto>builder()
                    .data(commentMapper.toDto(
                            commentRepository.save(commentSave)
                            )
                    )
                    .message("OK")
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message("Database error")
                    .data(commentMapper.toDto(commentSave))
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
                    .message("Deleted")
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .data(commentMapper.toDto(comment))
                    .build();
        }
        return ResponseDto.<CommentDto>builder()
                .message("Comment " +id+ " is not available")
                .success(false)
                .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                .build();
    }
}