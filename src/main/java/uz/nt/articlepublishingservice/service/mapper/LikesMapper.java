package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.model.Likes;

@Mapper(componentModel = "spring")
public abstract class LikesMapper implements CommonMapper<LikesDto, Likes> {
    protected UsersMapper usersMapper;
    protected ArticlesMapper articlesMapper;

    @Mapping(target = "article", expression = "java(articlesMapper.toDto(likes.getArticle()))")
    @Mapping(target = "user", expression = "java(usersMapper.toDto(likes.getUser()))")
    public abstract LikesDto toDto(Likes likes);

    @Mapping(target = "article", expression = "java(articlesMapper.toEntity(likesDto.getArticle()))")
    @Mapping(target = "user", expression = "java(usersMapper.toEntity(likesDto.getUser()))")
    public abstract Likes toEntity(LikesDto likesDto);
}