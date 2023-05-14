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
    public ResponseDto<CommentDto> addComment(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Articles> byId = articlesRepository.findById(id);
        if(!byId.isEmpty()) {
            try {
                UsersDto userDto = (UsersDto) authentication.getPrincipal();
                CommentDto build = CommentDto.<CommentDto>builder().articles(byId.get()).users(usersMapper.toEntityPassword(userDto)).build();
                Comment comment = commentMapper.toEntity(build);
                commentRepository.save(comment);
                return ResponseDto.<CommentDto>builder()
                        .data(commentMapper.toDto(comment))
                        .message("OK")
                        .code(1)
                        .success(true)
                        .build();
            } catch (Exception e) {
                return ResponseDto.<CommentDto>builder()
                        .code(-1)
                        .success(false)
                        .message("Database error" + ':' + e.getMessage())
                        .build();
            }
        }else {
            return ResponseDto.<CommentDto>builder()
                    .message("article not found")
                    .success(true)
                    .code(0)
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
