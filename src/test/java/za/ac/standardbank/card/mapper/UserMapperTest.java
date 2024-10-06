package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testMapUserToUpdateUserRequest_ValidUser() {

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        UpdateUserRequest result = userMapper.mapUserToUpdateUserRequest(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testMapUserToUpdateUserRequest_NullUser() {
        UpdateUserRequest result = userMapper.mapUserToUpdateUserRequest(null);
        assertNull(result);
    }

    @Test
    void testMapUserResponseToUser_ValidUserResponse() {

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");

        User result = userMapper.mapUserResponseToUser(userResponse);

        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getName(), result.getName());
        assertEquals(userResponse.getEmail(), result.getEmail());
    }

    @Test
    void testMapUserResponseToUser_NullUserResponse() {
        User result = userMapper.mapUserResponseToUser(null);
        assertNull(result);
    }
}