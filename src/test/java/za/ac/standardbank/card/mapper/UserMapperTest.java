package za.ac.standardbank.card.mapper;

import org.junit.jupiter.api.Test;
import za.ac.standardbank.card.dto.UserDto;
import za.ac.standardbank.card.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = UserMapper.instance;

    @Test
    void mapUserDtoToUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        User user = userMapper.mapUserDtoToUser(userDto);
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    void mapUserToUserDto() {
        User user = new User();
        user.setEmail("test@example.com");
        UserDto userDto = userMapper.mapUserToUserDto(user);
        assertEquals(user.getEmail(), userDto.getEmail());
    }
}