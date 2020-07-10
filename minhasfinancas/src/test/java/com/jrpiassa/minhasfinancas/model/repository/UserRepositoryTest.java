package com.jrpiassa.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.model.entity.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("Test")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void When_Email_Exists_Then_Return_True() {
		// scenario
		final String name = "User01";
		final String email = "User01@User01";
		User user = User.builder().name(name).email(email).build();
		userRepository.save(user);

		// action
		boolean result = userRepository.existsByEmail(email);

		// verification
		Assertions.assertThat(result).isTrue();
	}

}
