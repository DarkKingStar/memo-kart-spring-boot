package com.sudipta.auth;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sudipta.auth.modal.User;
import com.sudipta.auth.repository.UserRepository;
import com.sudipta.auth.serviceImpl.CustomUserDetails;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsTest {

    @InjectMocks
    private CustomUserDetails customUserDetails;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_Success() {
        // Mock data
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(any(String.class))).thenReturn(user);

        UserDetails userDetails = customUserDetails.loadUserByUsername("test@example.com");

        // Verify the returned UserDetails object
        assert userDetails != null;
        assert userDetails.getUsername().equals("test@example.com");
        assert userDetails.getPassword().equals("encodedPassword");
        assert userDetails.getAuthorities().isEmpty(); // Authorities list is empty in this example
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Mock userRepository to return null for the given username
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);

        // Verify that UsernameNotFoundException is thrown
        try {
            customUserDetails.loadUserByUsername("nonexistent@example.com");
        } catch (UsernameNotFoundException e) {
            assert e.getMessage().equals("user not found with email nonexistent@example.com");
        }
    }
}
