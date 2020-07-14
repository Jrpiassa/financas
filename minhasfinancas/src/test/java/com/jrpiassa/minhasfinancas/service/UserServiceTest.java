package com.jrpiassa.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.exception.AuthenticateException;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.model.repository.UserRepository;
import com.jrpiassa.minhasfinancas.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class UserServiceTest {

	@SpyBean
	private UserServiceImpl userService;

	@MockBean
	private UserRepository userRepository;

	private Long id;
	private String name;
	private String email;
	private String password;
	private static final String messageEmailNotFound = "Usuario não encontrado com o e-mail informado!";
	private static final String messagePasswordNotFound = "Usuario não encontrado com a senha informada!";
	private static final String messageEmailFound = "Já existe usuario cadastrado com este email";

	@BeforeEach
	public void setUp() {
		// userRepository = Mockito.mock(UserRepository.class);
		// userService = new UserServiceImpl(userRepository);
		id = 1l;
		name = "Test";
		email = "email@teste";
		password = "123";

	}

	@Test
	public void when_save_user_then_return_success() {
		// scenario
		Mockito.doNothing().when(userService).validateEmail(Mockito.anyString());
		User user = User.builder().id(id).name(name).email(email).password(password).build();
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		// action
		User saveUser = userService.saveUser(new User());

		// verifcation
		Assertions.assertThat(saveUser).isNotNull();
		Assertions.assertThat(saveUser.getId()).isEqualTo(id);
		Assertions.assertThat(saveUser.getName()).isEqualTo(name);
		Assertions.assertThat(saveUser.getEmail()).isEqualTo(email);
		Assertions.assertThat(saveUser.getPassword()).isEqualTo(password);
	}

	@Test
	public void when_save_user_withEmailFound_then_return_fail() {
		// scenario
		User user = User.builder().id(id).name(name).email(email).password(password).build();
		Mockito.doThrow(BusinesException.class).when(userService).validateEmail(user.getEmail());

		// action
		//Throwable catchThrowable = Assertions.catchThrowable(() -> userService.saveUser(user));

		// verifcation
		//Assertions.assertThat(catchThrowable).isInstanceOf(BusinesException.class).hasMessage(messageEmailFound);
		Mockito.verify(userService, Mockito.never()).saveUser(user);
	}

	@Test
	public void when_authenticate_then_return_success() {
		// scenario
		User user = User.builder().email(email).password(password).build();
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		// action
		User authenticate = userService.authenticate(email, password);

		// verification
		Assertions.assertThat(authenticate).isNotNull();
	}

	@Test
	public void when_authenticate_ByEmail_then_return_fail() {
		// scenario
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// action
		Throwable catchThrowable = Assertions.catchThrowable(() -> userService.authenticate(email, password));

		// verification
		Assertions.assertThat(catchThrowable).isInstanceOf(AuthenticateException.class)
				.hasMessage(messageEmailNotFound);
	}

	@Test
	public void when_authenticate_ByEmail_and_passwordIncorrect_then_return_fail() {
		// scenario
		String passwordIncorrect = "AAA";
		User user = User.builder().email(email).password(password).build();
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

		// action
		Throwable catchThrowable = Assertions.catchThrowable(() -> userService.authenticate(email, passwordIncorrect));

		// verification
		Assertions.assertThat(catchThrowable).isInstanceOf(AuthenticateException.class)
				.hasMessage(messagePasswordNotFound);
	}

	@Test
	public void when_valid_email_then_return_success() {
		// scenario
		Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		// action
		userService.validateEmail(email);
		// verification
	}

	@Test
	public void when_valid_email_then_return_error() {
		// scenario
		Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// action
		Throwable catchThrowable = Assertions.catchThrowable(() -> userService.validateEmail(email));

		// verification
		Assertions.assertThat(catchThrowable).isInstanceOf(BusinesException.class).hasMessage(messageEmailFound);
	}

}
