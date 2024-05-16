package com.sudipta.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sudipta.auth.exception.UserException;
import com.sudipta.auth.modal.User;
import com.sudipta.auth.service.UserService;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping({"/profile", "/jwt" })
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	//Used by Admin
	@GetMapping("/allusers")
	public ResponseEntity<List<User>> findAllUsersCall(@RequestHeader("Authorization") String jwt) throws UserException{
		List<User> user=userService.findAllUsers();
		return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
	}

	

	//Used by Order,Cart
	@GetMapping("/jwt/userid")
	public ResponseEntity<Long> getUserIdByJwt(@RequestHeader("Authorization") String jwt) throws UserException, Exception{
		User user=userService.findUserProfileByJwt(jwt);
		Long userId=user.getId();
		return new ResponseEntity<Long>(userId,HttpStatus.OK);
	}

	//Calling from Admin Interceptor
	@GetMapping("/role")
	public ResponseEntity<String> getRole(@RequestHeader("Authorization") String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<String>(user.getRole(),HttpStatus.OK);	
	}
}