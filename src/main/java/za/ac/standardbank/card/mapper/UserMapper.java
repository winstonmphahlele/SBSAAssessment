package za.ac.standardbank.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;


@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User mapCreateUserRequestToUser(CreateUserRequest createUserRequest);
    CreateUserRequest mapUserToCreateUserRequest(User user);

    User mapUpdateUserRequestToUser(UpdateUserRequest updateUserRequest);
    UpdateUserRequest mapUserToUpdateUserRequest(User user);

    User mapUserResponseToUser(UserResponse userResponse);
    UserResponse mapUserToUserResponse(User user);

}
