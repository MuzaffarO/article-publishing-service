package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.model.Comment;

@Mapper(componentModel = "spring")
public abstract class CommentMapper implements CommonMapper<CommentDto, Comment>{
    @Mapping(target = "users", expression = )
    public abstract CommentDto toDto(Comment comment);
    public abstract Comment toEntity(CommentDto commentDto);
}
