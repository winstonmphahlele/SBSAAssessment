package za.ac.standardbank.card.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import za.ac.standardbank.card.exception.RepositoryException;
import za.ac.standardbank.card.exception.ResourceAlreadyExistsException;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.exception.ResourceServiceException;
import za.ac.standardbank.card.mapper.UserMapper;
import za.ac.standardbank.card.model.User;
import za.ac.standardbank.card.repository.implementation.UserRepository;
import za.ac.standardbank.generated.CreateUserRequest;
import za.ac.standardbank.generated.UpdateUserRequest;
import za.ac.standardbank.generated.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService {
    @Inject
    private UserRepository userRepository;

    private static final UserMapper USER_MAPPER = UserMapper.instance;

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map(u -> USER_MAPPER.mapUserToUserResponse(u)).collect(Collectors.toList());
        return userResponses;
    }

    private User findUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(50004, "User with id " + id + " not found", null));
        return user;
    }

    public UserResponse findById(Long id) throws ResourceNotFoundException {
        User user = findUserById(id);
        UserResponse userResponse = USER_MAPPER.mapUserToUserResponse(user);
        return userResponse;
    }

    public UserResponse save(CreateUserRequest userRequest) throws ResourceAlreadyExistsException, ResourceServiceException {
        try {
            User user = userRepository.save(USER_MAPPER.mapCreateUserRequestToUser(userRequest));
            UserResponse response = USER_MAPPER.mapUserToUserResponse(user);
            return response;
        } catch (EntityExistsException ex) {
            throw new ResourceAlreadyExistsException(50001, "User already exits", ex);
        } catch (RepositoryException ex) {
            throw new ResourceServiceException(50002, String.format("%s %s", "Error Saving user : ", ex.getMessage()),  ex);
        }
    }

    public UserResponse update(UpdateUserRequest updateUserRequest) throws ResourceNotFoundException, ResourceServiceException {
        try {
            User updatedUser = userRepository.update(USER_MAPPER.mapUpdateUserRequestToUser(updateUserRequest));
            UserResponse userResponse = USER_MAPPER.mapUserToUserResponse(updatedUser);
            return userResponse;
        }catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(50004, String.format("User with ID: %s does not exits", updateUserRequest.getId()), ex);
        } catch (RepositoryException ex) {
            throw new ResourceServiceException(50004, String.format("%s %s", "Error Updating user : ", ex.getMessage()), ex);
        }
    }

    public void delete(Long id) throws ResourceNotFoundException{
        User userToDelete = findUserById(id);
        userRepository.delete(userToDelete);
    }


}
