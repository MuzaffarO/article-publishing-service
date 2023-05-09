package uz.nt.articlepublishingservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.model.Follows;
@Mapper(componentModel = "spring")
public abstract class FollowsMapper implements CommonMapper<FollowsDto, Follows> {
    protected UsersMapper usersMapper;
    @Mapping(target = "user",expression = "java(usersMapper.toDto(follows.getUser()))")
    @Mapping(target = "follower",expression = "java(usersMapper.toDto(follows.getFollower()))")
    public abstract FollowsDto toDto(Follows follows);
    @Mapping(target = "user",expression = "java(usersMapper.toEntity(followsDto.getUser()))")
    @Mapping(target = "follower",expression = "java(usersMapper.toEntity(followsDto.getFollower()))")
    public abstract Follows toEntity(FollowsDto followsDto);
}
