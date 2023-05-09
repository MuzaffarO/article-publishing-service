package uz.nt.articlepublishingservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.articlepublishingservice.dto.FollowsDto;
import uz.nt.articlepublishingservice.model.Follows;
@Mapper(componentModel = "spring")
//@RequiredArgsConstructor
public abstract class FollowsMapper implements CommonMapper<FollowsDto, Follows> {
//    @Autowired
//    protected UsersMapper usersMapper;
//    @Mapping(target = "user",expression = "java(usersMapper.toDto(follows.getUser()))")
//    @Mapping(target = "follower",expression = "java(usersMapper.toDto(follows.getFollower()))")
//    public abstract FollowsDto toDto(Follows follows);
//    @Mapping(target = "user",expression = "java(usersMapper.toEntity(followsDto.getUser()))")
//    @Mapping(target = "follower",expression = "java(usersMapper.toEntity(followsDto.getFollower()))")
//    public abstract Follows toEntity(FollowsDto followsDto);
}
