package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.generated.CreateUserRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private final UserMapper userMapper = UserMapper.instance;

    @Test
    void mapCreateUserRequestToUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");
        User user = userMapper.mapCreateUserRequestToUser(createUserRequest);
        assertEquals(createUserRequest.getEmail(), user.getEmail());
    }

    @Test
    void mapUserToCreateUserRequest() {
        User user = new User();
        user.setEmail("test@example.com");
        CreateUserRequest createUserRequest = userMapper.mapUserToCreateUserRequest(user);
        assertEquals(user.getEmail(), createUserRequest.getEmail());
    }
}