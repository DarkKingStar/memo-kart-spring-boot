package com.sudipta.payment.service;

import java.util.List;

import com.sudipta.payment.exception.UserException;
import com.sudipta.payment.modal.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

}
