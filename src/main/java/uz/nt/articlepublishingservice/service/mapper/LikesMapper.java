package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.model.Likes;

@Mapper(componentModel = "spring")
public interface LikesMapper extends CommonMapper<LikesDto, Likes> {
}