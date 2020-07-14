package com.jrpiassa.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.model.entity.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	private String name;
	private String email;
	private String password;
	
	@BeforeEach
	public void setUp() {
		name = "User01";
		email = "User01@User01";
		password = "123";
	}

	@Test
	public void when_email_exists_then_return_true() {
		// scenario		
		User user = buildUser(name, email, password);
		entityManager.persist(user);

		// action
		boolean result = userRepository.existsByEmail(email);

		// verification
		Assertions.assertThat(result).isTrue();
	}	
	
	@Test
	public void when_email_notExists_then_return_false() {
		//scenario		
		
		//action
		boolean existsByEmail = userRepository.existsByEmail(email);
		
		//verification
		Assertions.assertThat(existsByEmail).isFalse();
		
	}
	
	@Test
	public void when_save_user_then_return_success() {
		//scenario		 
		User user = buildUser(name, email, password);
		
		//action
		User userSaved = userRepository.save(user);
		
		//verifcation
		Assertions.assertThat(userSaved.getId()).isNotNull();
	}
	
	@Test
	public void when_findByEmail_then_return_success() {
		//scenario
		User user = buildUser(name, email, password);
		entityManager.persist(user);
		
		//action
		Optional<User> findByEmail = userRepository.findByEmail(email);
		
		//verification
		Assertions.assertThat(findByEmail.isPresent()).isTrue();
	}
	
	@Test
	public void when_findByEmail_then_return_fail() {
		//scenario
		
		//action
		Optional<User> findByEmail = userRepository.findByEmail(email);
		
		//verification
		Assertions.assertThat(findByEmail.isPresent()).isFalse();
	}
	
	private static User buildUser(final String name, final String email, final String password) {
		return User.builder().name(name).email(email).password(password).build();
	}

}



