package com.jrpiassa.minhasfinancas.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrpiassa.minhasfinancas.api.dto.LaunchDTO;
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
	UserService userService;

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
	public ResponseEntity updateLaunch(@PathVariable("{id}") Long id, @RequestBody LaunchDTO launchDTO) {
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

	private Launch buildLaunch(LaunchDTO launchDTO) {

		User user = userService.findById(launchDTO.getIdUser())
				.orElseThrow(() -> new BusinesException("Usuario não localizado !"));

		return Launch.builder().id(launchDTO.getId()).description(launchDTO.getDescription())
				.month(launchDTO.getMonth()).year(launchDTO.getYear())
				.typeLaunch(TypeLaunch.valueOf(launchDTO.getLaunchType())).value(launchDTO.getValue()).user(user)
				.statusLaunch(StatusLaunch.valueOf(launchDTO.getLaunchStatus())).build();

	}

}
