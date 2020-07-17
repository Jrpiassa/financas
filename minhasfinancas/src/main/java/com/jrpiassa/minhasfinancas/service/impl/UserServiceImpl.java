package com.jrpiassa.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrpiassa.minhasfinancas.exception.AuthenticateException;
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
		Optional<User> user = userRepository.findByEmail(email);
		
		if (!user.isPresent()) {
			throw new AuthenticateException("Usuario não encontrado com o e-mail informado!");
		}
		
		if(!user.get().getPassword().equals(password)) {
			throw new AuthenticateException("Usuario não encontrado com a senha informada!");
		}

		return user.get();
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		validateEmail(user.getEmail());
		return userRepository.save(user);
	}

	@Override
	public void validateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new BusinesException("Já existe usuario cadastrado com este email");
		}

	}

	@Override
	public Optional<User> findById(Long id) {		
		return userRepository.findById(id);
	}

}
