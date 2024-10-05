package za.ac.standardbank.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.card.repository.implementation.UserRepository;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;

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
    private CreateUserRequest createUserRequest;

    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("test@example.com");

        createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@example.com");

        updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(1L);
        updateUserRequest.setName("James Doe");
        updateUserRequest.setEmail("test@example.com");
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserResponse> foundUsers = userService.findAll();
        assertEquals(1, foundUsers.size());
        assertEquals(user.getEmail(), foundUsers.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    public void testFindById_UserExists() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse foundUser = userService.findById(1L);
        assertEquals(user.getEmail(), foundUser.getEmail());
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
    public void testSave() throws ResourceServiceException, ResourceAlreadyExistsException {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse userResponse = userService.save(createUserRequest);

        assertNotNull(userResponse, "Saved UserDto should not be null");
        assertEquals(userResponse.getEmail(), createUserRequest.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdate() throws ResourceServiceException, ResourceAlreadyExistsException {
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.update(updateUserRequest);
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
