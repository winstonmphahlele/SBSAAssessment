package za.ac.standardbank.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.dto.UserDto;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.card.repository.implementation.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        userDto = new UserDto();
        userDto.setEmail("test@example.com");
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserDto> userDtos = userService.findAll();
        assertEquals(1, userDtos.size());
        assertEquals(user.getEmail(), userDtos.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    public void testFindById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto foundUserDto = userService.findById(1L);
        assertEquals(user.getEmail(), foundUserDto.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testFindById_UserDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.findById(2L));
        assertEquals("User with id 2 not found", exception.getMessage());
        verify(userRepository).findById(2L);
    }

    @Test
    public void testSave() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto, "Saved UserDto should not be null");
        assertEquals(userDto.getEmail(), savedUserDto.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdate() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.update(userDto);
        verify(userRepository).update(any(User.class));
    }

    @Test
    public void testDelete_UserDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> userService.delete(2L));
        assertEquals("User with id 2 not found", exception.getMessage());
        verify(userRepository).findById(2L);
        verify(userRepository, never()).delete(any());
    }
}
