package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.TagDto;
import uz.nt.articlepublishingservice.model.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper extends CommonMapper<TagDto, Tag> {
}
