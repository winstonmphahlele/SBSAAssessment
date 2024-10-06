package za.ac.standardbank.card.resource;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.service.UserService;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserResourceTest {

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() throws ResourceServiceException, ResourceAlreadyExistsException {
        CreateUserRequest request = new CreateUserRequest();
        UserResponse userResponse = new UserResponse();

        when(userService.save(request)).thenReturn(userResponse);

        Response response = userResource.createUser(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(userResponse, response.getEntity());
    }

    @Test
    void testUpdateUser_Success() throws ResourceServiceException, ResourceNotFoundException {
        UpdateUserRequest request = new UpdateUserRequest();
        UserResponse updatedUserResponse = new UserResponse();

        when(userService.update(request)).thenReturn(updatedUserResponse);

        Response response = userResource.updateUser(request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(updatedUserResponse, response.getEntity());
    }

    @Test
    void testUpdateUser_NotFound() throws ResourceServiceException, ResourceNotFoundException {
        UpdateUserRequest request = new UpdateUserRequest();

        when(userService.update(request)).thenThrow(new  ResourceNotFoundException(50004, "User with id " + request.getId() + " not found", null));

        Response response = userResource.updateUser(request);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void testDeleteUser_Success() throws ResourceNotFoundException {
        Long userId = 1L;

        doNothing().when(userService).delete(userId);

        Response response = userResource.deleteUser(userId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetUserById_Success() throws ResourceNotFoundException {
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();

        when(userService.findById(userId)).thenReturn(userResponse);

        Response response = userResource.getUserById(userId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userResponse, response.getEntity());
    }

    @Test
    void testGetAllUsers() {
        UserResponse userResponse = new UserResponse();
        when(userService.findAll()).thenReturn(Collections.singletonList(userResponse));

        Response response = userResource.getAllUsers();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof List);
    }
}