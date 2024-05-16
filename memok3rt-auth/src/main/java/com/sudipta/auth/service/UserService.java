package com.sudipta.auth.service;

import java.util.List;

import com.sudipta.auth.exception.UserException;
import com.sudipta.auth.modal.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

    

}
