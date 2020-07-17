package com.jrpiassa.minhasfinancas.service;

import java.util.Optional;

import com.jrpiassa.minhasfinancas.model.entity.User;

public interface UserService {
	
	User authenticate(String email, String password);
	
	User saveUser(User user);
	
	void validateEmail(String email);
	
	Optional<User> findById(Long id);

}
