package za.ac.standardbank.card.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import za.ac.standardbank.card.dto.UserDto;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.mapper.UserMapper;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.card.repository.implementation.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {
    @Inject
    private UserRepository userRepository;

    private static final UserMapper USER_MAPPER = UserMapper.instance;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(u -> USER_MAPPER.mapUserToUserDto(u)).collect(Collectors.toList());
        return userDtos;
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(400, "User with id " + id + " not found"));
        return USER_MAPPER.mapUserToUserDto(user);
    }

    public UserDto save(UserDto userDto) {
        User user = userRepository.save(USER_MAPPER.mapUserDtoToUser(userDto));
        return USER_MAPPER.mapUserToUserDto(user);
    }

    public void update(UserDto userDto) {
        User user = userRepository.update(USER_MAPPER.mapUserDtoToUser(userDto));
        userRepository.update(user);
    }

    public void delete(Long id) {
        User userToDelete = USER_MAPPER.mapUserDtoToUser(findById(id));
        userRepository.delete(userToDelete);
    }


}
