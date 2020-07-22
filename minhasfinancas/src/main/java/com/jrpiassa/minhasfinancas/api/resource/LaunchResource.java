package com.jrpiassa.minhasfinancas.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrpiassa.minhasfinancas.api.dto.LaunchDTO;
import com.jrpiassa.minhasfinancas.api.dto.LaunchUpdateStatus;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.entity.User;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;
import com.jrpiassa.minhasfinancas.service.LaunchService;
import com.jrpiassa.minhasfinancas.service.UserService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("/api/launches")
public class LaunchResource {

	@Autowired
	private LaunchService launchService;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity saveLaunch(@RequestBody LaunchDTO launchDTO) {
		try {
			Launch launch = buildLaunch(launchDTO);
			launchService.saveLaunch(launch);
			return ResponseEntity.ok(launch);

		} catch (BusinesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity updateLaunch(@PathVariable("id") Long id, @RequestBody LaunchDTO launchDTO) {
		return launchService.findById(id).map(entity -> {
			try {
				Launch launch = buildLaunch(launchDTO);
				launch.setId(entity.getId());
				launchService.updateLaunch(launch);
				return ResponseEntity.ok(launch);

			} catch (BusinesException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados !", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity deleteLaunch(@PathVariable("id") Long id) {
		return launchService.findById(id).map(entity -> {
			launchService.deleteLaunch(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado nabase de dados.", HttpStatus.BAD_REQUEST));
	}

	@GetMapping
	public ResponseEntity getLaunch(@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year, @RequestParam("user") Long idUser) {
		Launch launchFilter = new Launch();
		launchFilter.setDescription(description);
		launchFilter.setMonth(month);
		launchFilter.setYear(year);

		Optional<User> user = userService.findById(idUser);
		if (!user.isPresent())
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta, usuário não localizado!");
		else
			launchFilter.setUser(user.get());

		List<Launch> launches = launchService.findLaunch(launchFilter);
		return ResponseEntity.ok(launches);
	}
	
	@PutMapping("{id}/launch-update-status")
	public ResponseEntity launchUpdateStatus(@PathVariable("id") Long id , @RequestBody LaunchUpdateStatus launchUpdateStatus) {
		return launchService.findById(id).map(entity ->{
			StatusLaunch status = StatusLaunch.valueOf(launchUpdateStatus.getStatus());
			if(null == status)
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento! ");
			try {
				entity.setStatusLaunch(status);
				launchService.updateLaunch(entity);
				return ResponseEntity.ok(entity);
			} catch (BusinesException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado nabase de dados.", HttpStatus.BAD_REQUEST));
	}

	private Launch buildLaunch(LaunchDTO launchDTO) {

		User user = userService.findById(launchDTO.getIdUser())
				.orElseThrow(() -> new BusinesException("Usuario não localizado !"));

		Launch launch = new Launch();
		launch.setId(launchDTO.getId());
		launch.setDescription(launchDTO.getDescription());
		launch.setMonth(launchDTO.getMonth());
		launch.setYear(launchDTO.getYear());
		launch.setValue(launchDTO.getValue());
		launch.setUser(user);
		if (null != launchDTO.getTypeLaunch())
			launch.setTypeLaunch(TypeLaunch.valueOf(launchDTO.getTypeLaunch()));
		if (null != launchDTO.getStatusLaunch())
			launch.setStatusLaunch(StatusLaunch.valueOf(launchDTO.getStatusLaunch()));

		return launch;

	}

}
