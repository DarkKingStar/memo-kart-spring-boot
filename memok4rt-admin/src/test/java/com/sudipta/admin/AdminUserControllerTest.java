package com.sudipta.admin;
import com.sudipta.admin.controller.AdminUserController;
import com.sudipta.admin.dto.UserDTO;
import com.sudipta.admin.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminUserControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AdminUserController adminUserController;

    private final String jwtToken = "your_jwt_token";
    private final String authUrl = "http://localhost:5000/users/allusers";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() throws UserException {
        UserDTO[] mockUserArray = {new UserDTO(), new UserDTO()}; // Mock user data

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange(eq(authUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserDTO[].class)))
                .thenReturn(new ResponseEntity<>(mockUserArray, HttpStatus.OK));

        ResponseEntity<List<UserDTO>> response = adminUserController.getAllUsers(jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserDTO> userList = response.getBody();
        assertEquals(2, userList.size()); // Ensure that the response contains the expected number of users
        //assertEquals("John Doe", userList.get(0).getFirstName());
        //assertEquals("Jane Smith", userList.get(1).getFirstName());
    }
}
