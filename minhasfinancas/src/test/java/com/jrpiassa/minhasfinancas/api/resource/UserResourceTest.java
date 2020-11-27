package com.jrpiassa.minhasfinancas.api.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrpiassa.minhasfinancas.api.dto.UserDTO;
import com.jrpiassa.minhasfinancas.exception.AuthenticateException;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.service.LaunchService;
import com.jrpiassa.minhasfinancas.service.UserService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@WebMvcTest(controllers = UserResource.class)
@AutoConfigureMockMvc
public class UserResourceTest {
	
	private static final String API = "/api/user";
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private LaunchService launchService;
	
	@Test
	public void when_autenticate_user_then_return_success() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();
		User user = User.builder().id(1l).email(email).password(password).build();
		
		Mockito.when(userService.authenticate(email, password)).thenReturn(user);
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/authenticate")).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value(user.getEmail()));
		
	}
	
	@Test
	public void when_autenticate_user_then_return_error() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();		
		
		Mockito.when(userService.authenticate(email, password)).thenThrow(AuthenticateException.class);
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/authenticate")).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void when_save_user_then_return_success() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();
		User user = User.builder().id(1l).email(email).password(password).build();
		
		Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(user);
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value(user.getEmail()));
		
	}
	
	@Test
	public void when_save_user_then_return_error() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();		
		
		Mockito.when(userService.saveUser(Mockito.any(User.class))).thenThrow(BusinesException.class);
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void when_getbalance_user_then_return_success() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();
		User user = User.builder().id(1l).email(email).password(password).build();
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.getBalanceUser(Mockito.anyLong())).thenReturn(new BigDecimal(0));
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/1/balance")).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("0"));
		
	}
	
	@Test
	public void when_getbalance_user_then_return_error() throws Exception {
		//scenario
		String email = "Teste01@email";
		String password = "123";
		
		UserDTO userDTO = UserDTO.builder().email(email).password(password).build();		
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.empty());		
		String json = new ObjectMapper().writeValueAsString(userDTO);
		
		//action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/1/balance")).accept(JSON)
				.contentType(JSON).content(json);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}

}
