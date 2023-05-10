package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper extends CommonMapper<CommentDto, Comment>{

}