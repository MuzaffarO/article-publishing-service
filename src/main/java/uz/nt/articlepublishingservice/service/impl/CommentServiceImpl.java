package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
import uz.nt.articlepublishingservice.service.additional.AppStatusMessages;
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
        
        List<CommentDto> commentList = commentRepository.findAllByArticles_Id(id).stream().map(commentMapper:: toDto).toList();

        return ResponseDto.<List<CommentDto>>builder()
                .message("OK")
                .code(1)
                .data(commentList)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<CommentDto> addComment(Integer id, String comment) {
        UsersDto principal = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Articles> article = articlesRepository.findById(id);
        if (article.isEmpty()) {
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .message(AppStatusMessages.NOT_FOUND)
                    .build();
        }
        Comment commentSave = Comment.builder()
                .users(usersMapper.toEntityPassword(principal))
                .description(comment)
                .articles(article.get())
                .build();
        try {
            CommentDto commentDto = commentMapper.toDto(commentRepository.save(commentSave));
            return ResponseDto.<CommentDto>builder()
                    .data(commentDto)
                    .message(AppStatusMessages.OK)
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CommentDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR)
                    .data(commentMapper.toDto(commentSave))
                    .build();
        }
    }

    @Override
    public ResponseDto<CommentDto> removeComment(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersDto usersDto = (UsersDto) authentication.getPrincipal();
        Optional<Comment> commentById = commentRepository.findById(id);
        if(commentById.isEmpty()){
            return ResponseDto.<CommentDto>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .success(false)
                    .code(AppStatusCodes.OK_CODE)
                    .build();
        }
        if(commentById.get().getUsers().getId() != usersDto.getId()){
            return ResponseDto.<CommentDto>builder()
                    .message("method not allow")
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .build();
        }

        Comment comment = commentById.get();
        commentRepository.deleteById(id);
        return ResponseDto.<CommentDto>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .success(true)
                .data(commentMapper.toDto(comment))
                .build();

    }
}
