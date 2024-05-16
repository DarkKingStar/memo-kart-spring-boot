package com.sudipta.auth.controller;

import java.time.LocalDateTime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.sudipta.auth.config.JwtTokenProvider;
import com.sudipta.auth.exception.UserException;

import com.sudipta.auth.modal.User;
import com.sudipta.auth.repository.UserRepository;
import com.sudipta.auth.request.LoginRequest;
import com.sudipta.auth.response.ApiResponse;
import com.sudipta.auth.response.AuthResponse;
import com.sudipta.auth.serviceImpl.CustomUserDetails;
import jakarta.validation.Valid;



@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetails customUserDetails;
	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger=LoggerFactory.getLogger(AuthController.class);

	private String CART_URL = "http://localhost:5003"; 
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException{
		
		  	String email = user.getEmail();
	        String password = user.getPassword();
	        String firstName=user.getFirstName();
	        String lastName=user.getLastName();
			String mobile=user.getMobile();
	        String role=user.getRole();
	        
	        System.out.println("sign up user --------> "+ email +","+password+","+firstName+","+lastName+","+mobile+","+role);

			User isEmailExist=userRepository.findByEmail(email);

	        // Check if user with the given email already exists
	        if (isEmailExist!=null) {
	        	logger.error("Logger:Email Is Already Used With Another Account");
	            throw new UserException("Email Is Already Used With Another Account");
	        }

	        // Create new user
			User createdUser= new User();
			createdUser.setEmail(email);
			createdUser.setFirstName(firstName);
			createdUser.setLastName(lastName);
	        createdUser.setPassword(passwordEncoder.encode(password));
			createdUser.setMobile(mobile);
	        createdUser.setRole(role);
			createdUser.setCreatedAt(LocalDateTime.now());
	        
	        User savedUser= userRepository.save(createdUser);
			
					//creating authentication request using a email and password
	        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
					//setting the authentication objcet within the security context. 
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String token = jwtTokenProvider.generateToken(authentication);

					// ----------created user's cart-------------
					HttpHeaders headers = new HttpHeaders();
					headers.setBearerAuth(token);
					HttpEntity<User> entity = new HttpEntity<User>(savedUser, headers);

					restTemplate.exchange(CART_URL+"/cart/create", HttpMethod.PUT, entity, ApiResponse.class);
					//-------------------------------------------

	        AuthResponse authResponse= new AuthResponse(token,true);
	        logger.info("User added successfully");
	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK); 
		
	}
	
	@PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse= new AuthResponse(token,true);
        logger.info("Logger:Sign in successfully");
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }

	
	
	private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        
        System.out.println("sign in userDetails - "+userDetails);
        
        if (userDetails == null) {
        	System.out.println("sign in userDetails - null " + userDetails);
        	logger.error("Logger:Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        	System.out.println("sign in userDetails - password not match " + userDetails);
        	logger.error("Logger:Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
