package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.model.Follows;
@Mapper(componentModel = "spring")
public interface FollowsMapper extends CommonMapper<FollowsDto, Follows> {
}
