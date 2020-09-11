package com.jrpiassa.minhasfinancas.business.rule;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class LaunchRulesTest {	
	
	@Test
	public void when_validateLaunch_then_return_success() {
		//scenario
		Launch launch = buildLaunch("complete");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isNull();
	}
	
	@Test
	public void when_validateLaunch_then_return_description_error() {
		//scenario
		Launch launch = buildLaunch("withoutdescription");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));			
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe uma descrição válida !");
	}
	
	@Test
	public void when_validateLaunch_then_return_month_error() {
		//scenario
		Launch launch = buildLaunch("withoutmonth");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe um mês válido !");
	}
	
	@Test
	public void when_validateLaunch_then_return_year_error() {
		//scenario
		Launch launch = buildLaunch("withoutyear");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe um ano válido !");
	}
	
	@Test
	public void when_validateLaunch_then_return_user_error() {
		//scenario
		Launch launch = buildLaunch("withoutuser");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe um usuário !");
	}
	
	@Test
	public void when_validateLaunch_then_return_value_error() {
		//scenario
		Launch launch = buildLaunch("withoutvalue");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe um valor válido !");
	}
	
	@Test
	public void when_validateLaunch_then_return_typeLaunch_error() {
		//scenario
		Launch launch = buildLaunch("withouttypeLaunch");		
		LaunchRules launchRules = Mockito.spy(new LaunchRules(launch));				
		
		//action
		String validateLaunch = launchRules.validateLaunch();		
		
		//verfication
		Assertions.assertThat(validateLaunch).isEqualTo("Informe o tipo do lançamento !");
	}
	
	private Launch buildLaunch(String build) {
		Launch launch = null;
		User user = new User(1l,"Teste01", "teste@email", "123", LocalDate.now());
		switch (build) {		
        case "withoutdescription":
        	launch = Launch.builder().year(2019).month(1).value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).build();
			break;
			
        case "withoutmonth":
        	launch = Launch.builder().description("Lancamento qualquer").year(2019).value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).build();
			break;
			
        case "withoutyear":
        	launch = Launch.builder().month(1).description("Lancamento qualquer").value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).build();
			break;
			
        case "withoutuser":
        	launch = Launch.builder().month(1).description("Lancamento qualquer").year(2019).month(1).description("Lancamento qualquer")
			.value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).build();
			break;
			
        case "withoutvalue":
        	launch = Launch.builder().month(1).description("Lancamento qualquer").year(2019).month(1).description("Lancamento qualquer")
			.typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).user(user).build();
			break;
			
        case "withouttypeLaunch":
        	launch = Launch.builder().month(1).description("Lancamento qualquer").year(2019).month(1).description("Lancamento qualquer")
			.statusLaunch(StatusLaunch.PENDING).value(BigDecimal.valueOf(10))
			.dateRegister(LocalDate.now()).user(user).build();
			break;

		default:			
			launch = Launch.builder().year(2019).month(1).description("Lancamento qualquer")
			.value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
			.dateRegister(LocalDate.now()).user(user).build();
			break;
		}
		
		return launch;
	}

}
