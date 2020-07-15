package com.jrpiassa.minhasfinancas.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.repository.LaunchRepository;
import com.jrpiassa.minhasfinancas.service.LaunchService;

@Service
public class LaunchServiceImpl implements LaunchService {

	private LaunchRepository launchRepository;

	public LaunchServiceImpl(LaunchRepository launchRepository) {
		this.launchRepository = launchRepository;
	}

	@Override
	@Transactional
	public Launch saveLaunch(Launch launch) {
		return launchRepository.save(launch);
	}

	@Override
	@Transactional
	public Launch updateLaunch(Launch launch) {
		Objects.requireNonNull(launch.getId());
		return launchRepository.save(launch);
	}

	@Override
	@Transactional
	public void deleteLaunch(Launch launch) {
		Objects.requireNonNull(launch.getId());
		launchRepository.delete(launch);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Launch> findLaunch(Launch launch) {
		Example<Launch> example = Example.of(launch,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return launchRepository.findAll(example);
	}

}
