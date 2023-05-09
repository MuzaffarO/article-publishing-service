package uz.nt.articlepublishingservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.articlepublishingservice.dto.LikesDto;
import uz.nt.articlepublishingservice.model.Likes;

@Mapper(componentModel = "spring")
//@RequiredArgsConstructor
public abstract class LikesMapper implements CommonMapper<LikesDto, Likes> {
    @Autowired
    protected UsersMapper usersMapper;
    @Autowired
    protected ArticlesMapper articlesMapper;

    @Mapping(target = "article", expression = "java(articlesMapper.toDto(likes.getArticle()))")
    @Mapping(target = "user", expression = "java(usersMapper.toDto(likes.getUser()))")
    public abstract LikesDto toDto(Likes likes);

    @Mapping(target = "article", expression = "java(articlesMapper.toEntity(likesDto.getArticle()))")
    @Mapping(target = "user", expression = "java(usersMapper.toEntity(likesDto.getUser()))")
    public abstract Likes toEntity(LikesDto likesDto);
}