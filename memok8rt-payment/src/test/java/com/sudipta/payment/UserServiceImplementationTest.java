package com.sudipta.payment;


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

import com.sudipta.payment.config.JwtTokenProvider;
import com.sudipta.payment.exception.UserException;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.UserRepository;
import com.sudipta.payment.serviceImpl.UserServiceImplementation;

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
        mockUser.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Call the method
        User foundUser = userService.findUserById(userId);

        // Verify the result
        assertNotNull(foundUser);
        assertEquals(mockUser.getId(), foundUser.getId());
        assertEquals(mockUser.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        // Mock userRepository to return Optional.empty() for the given userId
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method and verify that UserException is thrown
        assertThrows(UserException.class, () -> userService.findUserById(userId));
    }

    @Test
    public void testFindUserProfileByJwt_Success() throws UserException {
        // Mock data
        String jwt = "test.jwt.token";
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(jwtTokenProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Call the method
        User userProfile = userService.findUserProfileByJwt(jwt);

        // Verify the result
        assertNotNull(userProfile);
        assertEquals(mockUser.getEmail(), userProfile.getEmail());
    }

    @Test
    public void testFindUserProfileByJwt_UserNotFound() {
        // Mock data
        String jwt = "test.jwt.token";
        String email = "nonexistent@example.com";

        when(jwtTokenProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Call the method and verify that UserException is thrown
        assertThrows(UserException.class, () -> userService.findUserProfileByJwt(jwt));
    }

    
}
