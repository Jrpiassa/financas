package com.jrpiassa.minhasfinancas.api.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrpiassa.minhasfinancas.api.dto.UserDTO;
import com.jrpiassa.minhasfinancas.exception.AuthenticateException;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.service.LaunchService;
import com.jrpiassa.minhasfinancas.service.UserService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("/api/user")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LaunchService launchService;

	
	@PostMapping
	public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {

		User user = buildUser(userDTO);

		try {
			return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
		} catch (BusinesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody UserDTO userDTO) {
		try {
			User authenticate = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
			return ResponseEntity.ok(authenticate);
		} catch (AuthenticateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("{id}/balance")
	public ResponseEntity getBalance( @PathVariable("id") Long id) {
		Optional<User> user = userService.findById(id);
		
		if(!user.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		BigDecimal balance = launchService.getBalanceUser(id);
		return ResponseEntity.ok(balance);
	}

	private User buildUser(UserDTO userDTO) {
		return User.builder().name(userDTO.getName()).email(userDTO.getEmail()).password(userDTO.getPassword()).build();
	}
}
