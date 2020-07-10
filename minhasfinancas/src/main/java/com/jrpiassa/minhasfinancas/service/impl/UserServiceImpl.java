package com.jrpiassa.minhasfinancas.service.impl;

import org.springframework.stereotype.Service;

import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.model.repository.UserRepository;
import com.jrpiassa.minhasfinancas.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User authenticate(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new BusinesException("Já existe usuario cadastrado com este email");
		}

	}

}
