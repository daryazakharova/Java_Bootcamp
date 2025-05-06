package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private UsersServiceImpl usersService;
    private UsersRepository usersRepository;

    @BeforeEach
    public void init() {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    public void testAuthenticate_Success() {
        // Arrange
        User user = new User(1L, "testUser ", "password123");
        when(usersRepository.findByLogin("testUser ")).thenReturn(user);

        // Act
        boolean result = usersService.authenticate("testUser ", "password123");

        // Assert
        assertTrue(result);
        assertTrue(user.isAuthenticated());
        verify(usersRepository).update(user);
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        // Arrange
        when(usersRepository.findByLogin("unknownUser ")).thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate("unknownUser ", "password123"));

        assertEquals("Invalid login", exception.getMessage());
    }

    @Test
    public void testAuthenticate_AlreadyAuthenticated() {
        // Arrange
        User user = new User(1L, "testUser ", "password123");
        user.setAuthenticated(true);
        when(usersRepository.findByLogin("testUser ")).thenReturn(user);

        // Act & Assert
        AlreadyAuthenticatedException exception = assertThrows(AlreadyAuthenticatedException.class, () ->
            usersService.authenticate("testUser ", "password123"));

        assertEquals("User is already authenticated", exception.getMessage());
        verify(usersRepository, never()).update(user);
    }

    @Test
    public void testAuthenticate_WrongPassword() {
        // Arrange
        User user = new User(1L, "testUser ", "password123");
        when(usersRepository.findByLogin("testUser ")).thenReturn(user);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
            usersService.authenticate("testUser ", "wrongPassword"));

        assertEquals("Invalid password", exception.getMessage());
        assertFalse(user.isAuthenticated());
        verify(usersRepository, never()).update(user);
    }
}
