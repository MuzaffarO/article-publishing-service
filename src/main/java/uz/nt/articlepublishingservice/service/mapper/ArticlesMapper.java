package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.model.Articles;

@Mapper(componentModel = "spring")
public abstract class ArticlesMapper implements CommonMapper<ArticlesDto, Articles> {
    @Autowired
    protected UsersMapper usersMapper;
    @Mapping(target="author", expression ="java(usersMapper.toDto(articles.getAuthor()))")
    abstract public ArticlesDto toDto(Articles articles);
}
