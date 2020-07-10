package com.jrpiassa.minhasfinancas.service;

import com.jrpiassa.minhasfinancas.model.entity.User;

public interface UserService {
	
	User authenticate(String email, String password);
	
	User saveUser(User user);
	
	void validateEmail(String email);

}
