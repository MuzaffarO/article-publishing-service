package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.articlepublishingservice.dto.ArticlesDto;
import uz.nt.articlepublishingservice.model.Articles;
@Mapper(componentModel = "spring")
public interface ArticlesMapper extends CommonMapper<ArticlesDto, Articles> {
//    @Autowired
//    protected TagMapper tagMapper;
//    @Mapping(target = "tags", expression = "java(tagMapper.toDto(articles.getTags()))")
//    public abstract ArticlesDto toDto(Articles articles);
//    @Mapping(target = "tags", expression = "java(tagMapper.toEntity(articlesDto.getTags()))")
//    public abstract Articles toEntity(ArticlesDto articlesDto);
}
