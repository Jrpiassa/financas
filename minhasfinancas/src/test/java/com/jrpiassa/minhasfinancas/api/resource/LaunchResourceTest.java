package com.jrpiassa.minhasfinancas.api.resource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrpiassa.minhasfinancas.api.dto.LaunchDTO;
import com.jrpiassa.minhasfinancas.api.dto.LaunchUpdateStatusDTO;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.service.LaunchService;
import com.jrpiassa.minhasfinancas.service.UserService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@WebMvcTest(controllers = LaunchResource.class)
@AutoConfigureMockMvc
public class LaunchResourceTest {

	private static final String API = "/api/launches";
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	private static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private LaunchService launchService;

	@MockBean
	private UserService userService;

	@Test
	public void when_save_launch_then_return_success() throws Exception {
		// scenario
		LaunchDTO launchDTO = LaunchDTO.builder().description("Teste teste 04").month(1).year(2020)
				.value(new BigDecimal(200.0)).typeLaunch("EXPENSE").idUser(3l).build();

		Launch launch = Launch.builder().description("Teste teste 04").build();

		String email = "Teste01@email";
		String password = "123";

		User user = User.builder().id(1l).email(email).password(password).build();

		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.saveLaunch(Mockito.any(Launch.class))).thenReturn(launch);

		String json = new ObjectMapper().writeValueAsString(launchDTO);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void when_save_launch_then_return_error() throws Exception {
		// scenario
		LaunchDTO launchDTO = LaunchDTO.builder().description("Teste teste 04").month(1).year(2020)
				.value(new BigDecimal(200.0)).typeLaunch("EXPENSE").idUser(3l).build();		

		String email = "Teste01@email";
		String password = "123";

		User user = User.builder().id(1l).email(email).password(password).build();

		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.saveLaunch(Mockito.any(Launch.class))).thenThrow(BusinesException.class);

		String json = new ObjectMapper().writeValueAsString(launchDTO);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void when_update_launch_then_return_success() throws Exception {
		// scenario
		LaunchDTO launchDTO = LaunchDTO.builder().description("Teste teste 04").month(1).year(2020)
				.value(new BigDecimal(200.0)).typeLaunch("EXPENSE").idUser(3l).build();	
		
		Launch launch = Launch.builder().description("Teste teste 04").build();

		String email = "Teste01@email";
		String password = "123";

		User user = User.builder().id(1l).email(email).password(password).build();

		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
		Mockito.when(launchService.updateLaunch(Mockito.any(Launch.class))).thenReturn(launch);

		String json = new ObjectMapper().writeValueAsString(launchDTO);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/1")).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void when_update_launch_then_return_error() throws Exception {
		// scenario
		LaunchDTO launchDTO = LaunchDTO.builder().description("Teste teste 04").month(1).year(2020)
				.value(new BigDecimal(200.0)).typeLaunch("EXPENSE").idUser(3l).build();	
		
		Launch launch = Launch.builder().description("Teste teste 04").build();

		String email = "Teste01@email";
		String password = "123";

		User user = User.builder().id(1l).email(email).password(password).build();

		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
		Mockito.when(launchService.updateLaunch(Mockito.any(Launch.class))).thenThrow(BusinesException.class);

		String json = new ObjectMapper().writeValueAsString(launchDTO);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/1")).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());		

	}
	
	@Test
	public void when_delete_launch_then_return_success() throws Exception {
		// scenario
		LaunchDTO launchDTO = LaunchDTO.builder().description("Teste teste 04").month(1).year(2020)
				.value(new BigDecimal(200.0)).typeLaunch("EXPENSE").idUser(3l).build();	
		
		Launch launch = Launch.builder().description("Teste teste 04").build();

		String email = "Teste01@email";
		String password = "123";

		User user = User.builder().id(1l).email(email).password(password).build();

		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(launchService.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
		Mockito.doNothing().when(launchService).deleteLaunch(Mockito.any(Launch.class));

		String json = new ObjectMapper().writeValueAsString(launchDTO);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API.concat("/1")).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}
	
	@Test
	public void when_getLaunch_then_return_success() throws Exception {
		// scenario
		String description = "Teste01";
		Integer month = 9;
		Integer year = 2020;		

		String email = "Teste01@email";
		String password = "123";
		User user = User.builder().id(1l).email(email).password(password).build();
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		
		Launch launch = Launch.builder().description("Teste teste 01").build();
		List<Launch> launches = Arrays.asList(launch);
		Mockito.when(launchService.findLaunch(Mockito.any(Launch.class))).thenReturn(launches);

		String json = new ObjectMapper().writeValueAsString("");

		// action and verification
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("description", description);
		requestParams.add("month", String.valueOf(month));
		requestParams.add("year", String.valueOf(year));
		requestParams.add("user", String.valueOf(user.getId()));
				
				
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).params(requestParams).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void when_getLaunch_without_user_then_return_error() throws Exception {
		// scenario
		String description = "Teste01";
		Integer month = 9;
		Integer year = 2020;		

		String email = "Teste01@email";
		String password = "123";
		User user = User.builder().id(1l).email(email).password(password).build();
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Launch launch = Launch.builder().description("Teste teste 01").build();
		List<Launch> launches = Arrays.asList(launch);
		Mockito.when(launchService.findLaunch(Mockito.any(Launch.class))).thenReturn(launches);

		String json = new ObjectMapper().writeValueAsString("");

		// action and verification
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("description", description);
		requestParams.add("month", String.valueOf(month));
		requestParams.add("year", String.valueOf(year));
		requestParams.add("user", String.valueOf(user.getId()));
				
				
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).params(requestParams).accept(JSON).contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void when_launchUpdateStatus_then_return_success() throws Exception {
		// scenario
		LaunchUpdateStatusDTO launchUpdateStatus = new LaunchUpdateStatusDTO();
		launchUpdateStatus.setStatus(StatusLaunch.CANCELED.toString());
		
		Launch launch = Launch.builder().description("Teste teste 04").build();

		Mockito.when(launchService.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
		Mockito.when(launchService.updateLaunch(Mockito.any(Launch.class))).thenReturn(launch);

		String json = new ObjectMapper().writeValueAsString(launchUpdateStatus);

		// action and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/1/launch-update-status")).accept(JSON)
				.accept(APPLICATION_JSON_UTF8_VALUE)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

	}	
	
}
