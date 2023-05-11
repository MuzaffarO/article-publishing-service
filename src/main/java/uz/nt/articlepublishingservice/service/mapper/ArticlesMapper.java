package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.model.Articles;
@Mapper(componentModel = "spring")
public interface ArticlesMapper extends CommonMapper<ArticlesDto, Articles> {
    @Mapping(target = "likes",ignore = true)
    Articles toEntity(ArticlesDto dto);
    @Mapping(target = "likes",expression = "java(entity.getLikes().size())")
    ArticlesDto toDto(Articles entity);
}
