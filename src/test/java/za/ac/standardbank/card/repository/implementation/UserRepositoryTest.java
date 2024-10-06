package za.ac.standardbank.card.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.standardbank.card.exception.ResourceNotFoundException;
import za.ac.standardbank.card.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    public void testSave() {
        userRepository.save(user);
        verify(entityManager).persist(user);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testFindById_UserExists() {
        when(entityManager.find(User.class, 1L)).thenReturn(user);
        Optional<User> foundUser = userRepository.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        verify(entityManager).find(User.class, 1L);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testFindById_UserDoesNotExist() {
        when(entityManager.find(User.class, 2L)).thenReturn(null);
        Optional<User> foundUser = userRepository.findById(2L);
        assertFalse(foundUser.isPresent());
        verify(entityManager).find(User.class, 2L);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testDelete() {
        userRepository.delete(user);
        verify(entityManager).remove(user);
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testUpdate() throws ResourceNotFoundException {
        when(entityManager.find(User.class, 1L)).thenReturn(user);
        when(entityManager.merge(any(User.class))).thenReturn(user);

        userRepository.save(user);
        user.setName("Foo Bar");

        User updatedUser = userRepository.update(user);

        assertEquals(updatedUser.getName(), user.getName());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        verify(entityManager).merge(user);
    }

    @Test
    public void testUpdate_NonExistingUser() {
        when(entityManager.find(User.class, 1L)).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userRepository.update(user);
        });

        assertEquals("Entity not found!", exception.getMessage());
        verify(entityManager, never()).merge(any(User.class));
    }

    @Test
    public void testFindAll() {
        List<User> users = Collections.singletonList(user);
        TypedQuery<User> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT u FROM User u", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(users);

        List<User> foundUsers = userRepository.findAll();
        assertEquals(1, foundUsers.size());
        assertEquals(user.getEmail(), foundUsers.get(0).getEmail());
        verify(entityManager).createQuery("SELECT u FROM User u", User.class);
        verify(query).getResultList();
        verifyNoMoreInteractions(entityManager);
    }

    @Test
    public void testFindAll_EmptyResult() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT u FROM User u", User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<User> foundUsers = userRepository.findAll();
        assertTrue(foundUsers.isEmpty());
        verify(entityManager).createQuery("SELECT u FROM User u", User.class);
        verify(query).getResultList();
        verifyNoMoreInteractions(entityManager);
    }
}
