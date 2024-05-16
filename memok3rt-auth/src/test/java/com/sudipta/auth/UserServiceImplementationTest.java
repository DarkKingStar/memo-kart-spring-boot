package com.sudipta.auth;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.auth.config.JwtTokenProvider;
import com.sudipta.auth.exception.UserException;
import com.sudipta.auth.modal.User;
import com.sudipta.auth.repository.UserRepository;
import com.sudipta.auth.serviceImpl.UserServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    @InjectMocks
    private UserServiceImplementation userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void testFindUserById_Success() throws UserException {
        // Mock data
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        Optional<User> optionalUser = Optional.of(mockUser);

        when(userRepository.findById(any(Long.class))).thenReturn(optionalUser);

        // Call the method
        User foundUser = userService.findUserById(userId);

        // Verify the result
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        // Mock userRepository to return empty optional
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Call the method and verify that UserException is thrown
        assertThrows(UserException.class, () -> userService.findUserById(100L));
    }

    @Test
    public void testFindUserProfileByJwt_Success() throws UserException {
        // Mock data
        String jwtToken = "test_jwt_token";
        String userEmail = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(userEmail);

        when(jwtTokenProvider.getEmailFromJwtToken(any(String.class))).thenReturn(userEmail);
        when(userRepository.findByEmail(any(String.class))).thenReturn(mockUser);

        // Call the method
        User foundUser = userService.findUserProfileByJwt(jwtToken);

        // Verify the result
        assertNotNull(foundUser);
        assertEquals(userEmail, foundUser.getEmail());
    }

    @Test
    public void testFindUserProfileByJwt_UserNotFound() {
        // Mock jwtTokenProvider to return email
        String jwtToken = "test_jwt_token";
        when(jwtTokenProvider.getEmailFromJwtToken(any(String.class))).thenReturn("nonexistent@example.com");

        // Call the method and verify that UserException is thrown
        assertThrows(UserException.class, () -> userService.findUserProfileByJwt(jwtToken));
    }

    @Test
    public void testFindAllUsers_Success() {
        // Mock data
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAllByOrderByCreatedAtDesc()).thenReturn(userList);

        // Call the method
        List<User> foundUsers = userService.findAllUsers();

        // Verify the result
        assertNotNull(foundUsers);
        assertEquals(userList.size(), foundUsers.size());
    }
}

