package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.TagDto;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper extends CommonMapper<Set<TagDto>, Set<Tag>> {
    Tag toEntity(TagDto tagDto);
    TagDto toDto(Tag tag);
}
