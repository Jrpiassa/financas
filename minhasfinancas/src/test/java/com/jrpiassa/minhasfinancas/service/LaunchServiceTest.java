package com.jrpiassa.minhasfinancas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;
import com.jrpiassa.minhasfinancas.model.repository.LaunchRepository;
import com.jrpiassa.minhasfinancas.service.impl.LaunchServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class LaunchServiceTest {

	@SpyBean
	private LaunchServiceImpl launchService;

	@MockBean
	private LaunchRepository launchRepository;

	@Test
	public void when_save_launch_then_return_success() {
		// scenario
		Launch launchToSave = buildLaunch();
		Mockito.doNothing().when(launchService).validateLaunch(launchToSave);

		Launch launchSaved = buildLaunch();
		launchSaved.setId(1l);
		launchSaved.setStatusLaunch(StatusLaunch.PENDING);
		Mockito.when(launchRepository.save(launchToSave)).thenReturn(launchSaved);

		// action
		Launch saveLaunch = launchService.saveLaunch(launchToSave);

		// verification
		Assertions.assertThat(saveLaunch.getId()).isEqualTo(launchSaved.getId());
		Assertions.assertThat(launchSaved.getStatusLaunch()).isEqualTo(StatusLaunch.PENDING);
	}

	@Test
	public void when_save_launch_then_return_error() {
		// scenario
		Launch launchToSave = buildLaunch();
		Mockito.doThrow(BusinesException.class).when(launchService).validateLaunch(launchToSave);

		// action and verification
		Assertions.catchThrowableOfType(() -> launchService.saveLaunch(launchToSave), BusinesException.class);
		Mockito.verify(launchRepository, Mockito.never()).save(launchToSave);
	}

	@Test
	public void when_update_launch_then_return_success() {
		// scenario
		Launch launchSaved = buildLaunch();
		launchSaved.setId(1l);
		launchSaved.setStatusLaunch(StatusLaunch.PENDING);

		Mockito.doNothing().when(launchService).validateLaunch(launchSaved);
		Mockito.when(launchRepository.save(launchSaved)).thenReturn(launchSaved);

		// action
		launchService.updateLaunch(launchSaved);

		// verification
		Mockito.verify(launchRepository, Mockito.times(1)).save(launchSaved);
	}

	@Test
	public void when_update_launch_then_return_error() {
		// scenario
		Launch launchToSave = buildLaunch();

		// action and verification
		Assertions.catchThrowableOfType(() -> launchService.updateLaunch(launchToSave), NullPointerException.class);
		Mockito.verify(launchRepository, Mockito.never()).save(launchToSave);
	}

	@Test
	public void when_delete_launch_then_return_success() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);

		// action
		launchService.deleteLaunch(launch);

		// verification
		Mockito.verify(launchRepository).delete(launch);
	}

	@Test
	public void when_delete_launch_then_return_error() {
		// scenario
		Launch launch = buildLaunch();

		// action and verification
		Assertions.catchThrowableOfType(() -> launchService.deleteLaunch(launch), NullPointerException.class);
		Mockito.verify(launchRepository, Mockito.never()).delete(launch);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void when_findlaunch_launch_then_return_success() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);

		List<Launch> lista = Arrays.asList(launch);
		Mockito.when(launchRepository.findAll(Mockito.any(Example.class))).thenReturn(lista);

		// action
		List<Launch> findLaunch = launchService.findLaunch(launch);

		// verification
		Assertions.assertThat(findLaunch).isNotEmpty().hasSize(1).contains(launch);
	}
	
	@Test
	public void when_findlaunchByID_then_return_success() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.findById(launch.getId())).thenReturn(Optional.of(launch));

		// action
		Optional<Launch> resFindById = launchService.findById(launch.getId());

		// verification
		Assertions.assertThat(resFindById.isPresent()).isTrue();
	}
	
	@Test
	public void when_findlaunchByID_then_return_error() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.findById(launch.getId())).thenReturn(Optional.empty());

		// action
		Optional<Launch> resFindById = launchService.findById(launch.getId());

		// verification
		Assertions.assertThat(resFindById.isPresent()).isFalse();
	}
	
	@Test
	public void when_getBalanceUser_then_return_zero() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.RECIPE)).thenReturn(null);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.EXPENSE)).thenReturn(null);

		// action
		BigDecimal balanceUser = launchService.getBalanceUser(launch.getId());

		// verification
		Assertions.assertThat(balanceUser).isEqualByComparingTo(new BigDecimal(0));
	}
	
	@Test
	public void when_getBalanceUser_then_return_positive_recipe() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.RECIPE)).thenReturn(new BigDecimal(5));
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.EXPENSE)).thenReturn(null);

		// action
		BigDecimal balanceUser = launchService.getBalanceUser(launch.getId());

		// verification
		Assertions.assertThat(balanceUser).isEqualByComparingTo(new BigDecimal(5));
	}
	
	@Test
	public void when_getBalanceUser_then_return_negative_expense() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.RECIPE)).thenReturn(null);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.EXPENSE)).thenReturn(new BigDecimal(5));

		// action
		BigDecimal balanceUser = launchService.getBalanceUser(launch.getId());

		// verification
		Assertions.assertThat(balanceUser).isEqualByComparingTo(new BigDecimal(-5));
	}
	
	@Test
	public void when_getBalanceUser_then_return_positive() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.RECIPE)).thenReturn(new BigDecimal(10));
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.EXPENSE)).thenReturn(new BigDecimal(5));

		// action
		BigDecimal balanceUser = launchService.getBalanceUser(launch.getId());

		// verification
		Assertions.assertThat(balanceUser).isEqualByComparingTo(new BigDecimal(5));
	}
	
	@Test
	public void when_getBalanceUser_then_return_negative() {
		// scenario
		Launch launch = buildLaunch();
		launch.setId(1l);
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.RECIPE)).thenReturn(new BigDecimal(5));
		Mockito.when(launchRepository.getBalanceTypeLaunchAndUser(launch.getId(), TypeLaunch.EXPENSE)).thenReturn(new BigDecimal(10));

		// action
		BigDecimal balanceUser = launchService.getBalanceUser(launch.getId());

		// verification
		Assertions.assertThat(balanceUser).isEqualByComparingTo(new BigDecimal(-5));
	}	

	private Launch buildLaunch() {
		Launch launch = Launch.builder().year(2019).month(1).description("Lancamento qualquer")
				.value(BigDecimal.valueOf(10)).typeLaunch(TypeLaunch.RECIPE).statusLaunch(StatusLaunch.PENDING)
				.dateRegister(LocalDate.now()).build();
		return launch;
	}

}
