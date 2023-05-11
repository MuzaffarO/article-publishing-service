package uz.nt.articlepublishingservice.service.mapper;

import org.hibernate.annotations.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Component
//@Mapper(componentModel = "spring")
public class TagMapper{
    Set<String> toDto(Set<Tag> tags){
        return tags.stream().map(tag -> tag.getName()).collect(Collectors.toSet());
    }
    Set<Tag> toEntity(Set<String> stringTag){
        return stringTag.stream().map(st->new Tag(st)).collect(Collectors.toSet());
    }
}
