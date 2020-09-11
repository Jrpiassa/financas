package com.jrpiassa.minhasfinancas.model.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LaunchRepositoryTest {
	
	@Autowired
	LaunchRepository launchRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void when_save_launch_then_return_success() {
		//scenario
		Launch launch = buildLaunch();
		
		//action
		launch = launchRepository.save(launch);
		
		//verification
		assertThat(launch.getId()).isNotNull();
	}
	
	@Test
	public void when_delete_launch_then_return_success() {
		//scenario
		Launch launch = buildAndPersistLaunch();
		
		launch = entityManager.find(Launch.class, launch.getId());
		
		//action
		launchRepository.delete(launch);
		
		Launch launchDeleted = entityManager.find(Launch.class, launch.getId());
		
		//verification
		assertThat(launchDeleted).isNull();
	}
	
	@Test
	public void when_update_launch_then_return_success() {
		//scenario
		Launch launch = buildAndPersistLaunch();
		
		//action
		launch.setYear(2018);
		String text = "Teste Atualizar";
		launch.setDescription(text);
		launch.setStatusLaunch(StatusLaunch.CANCELED);
		
		launchRepository.save(launch);
		
		Launch launchUpdated = entityManager.find(Launch.class, launch.getId());		
		
		//verification
		assertThat(launchUpdated.getYear()).isEqualTo(2018);
		assertThat(launchUpdated.getDescription()).isEqualTo(text);
		assertThat(launchUpdated.getStatusLaunch()).isEqualTo(StatusLaunch.CANCELED);
	}
	
	@Test
	public void when_findbyid_launch_then_return_success() {
		//scenario
		Launch launch = buildAndPersistLaunch();
		
		//action		
		Optional<Launch> launchFound = launchRepository.findById(launch.getId());		
		
		//verification
		assertThat(launchFound.isPresent()).isTrue();		
	}

	private Launch buildAndPersistLaunch() {		
		Launch launch = buildLaunch();		
		launch = entityManager.persist(launch);
		return launch;
	}

	private Launch buildLaunch() {
		Launch launch = Launch.builder()
				.year(2019)
				.month(1)
				.description("Lancamento qualquer")
				.value(BigDecimal.valueOf(10))
				.typeLaunch(TypeLaunch.RECIPE)
				.statusLaunch(StatusLaunch.PENDING)
				.dateRegister(LocalDate.now())
				.build();
		return launch;
	}
	
	
}
