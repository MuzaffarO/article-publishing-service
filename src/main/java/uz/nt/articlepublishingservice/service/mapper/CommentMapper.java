package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper extends CommonMapper<CommentDto, Comment>{

}
