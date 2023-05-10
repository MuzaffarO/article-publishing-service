package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.model.Articles;
@Mapper(componentModel = "spring")
public interface ArticlesMapper extends CommonMapper<ArticlesDto, Articles> {
    @Mapping(target = "likes",ignore = true)
    Articles toEntity(ArticlesDto articlesDto);
    @Mapping(target = "likes",expression = "java(articles.getLikes().size())")
    ArticlesDto toDto(Articles articles);
}
