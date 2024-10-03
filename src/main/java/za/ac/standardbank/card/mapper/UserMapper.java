package za.ac.standardbank.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import za.ac.standardbank.card.dto.UserDto;
import za.ac.standardbank.card.model.User;


@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);
    User mapUserDtoToUser(UserDto userDto);
    UserDto mapUserToUserDto(User user);
}
