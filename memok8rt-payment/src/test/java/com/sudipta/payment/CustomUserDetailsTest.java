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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.UserRepository;
import com.sudipta.payment.serviceImpl.CustomUserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsTest {

    @InjectMocks
    private CustomUserDetails customUserDetails;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_Success() {
        // Mock data
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("testPassword");

        when(userRepository.findByEmail(any(String.class))).thenReturn(mockUser);

        // Call the method
        UserDetails userDetails = customUserDetails.loadUserByUsername(email);

        // Verify the result
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Mock userRepository to return null for the given username
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);

        // Call the method and verify that UsernameNotFoundException is thrown
        assertThrows(UsernameNotFoundException.class, () -> customUserDetails.loadUserByUsername("nonexistent@example.com"));
    }
}

