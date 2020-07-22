package com.jrpiassa.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrpiassa.minhasfinancas.business.rule.LaunchRules;
import com.jrpiassa.minhasfinancas.exception.BusinesException;
import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;
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
		validateLaunch(launch);
		launch.setStatusLaunch(StatusLaunch.PENDING);
		return launchRepository.save(launch);
	}

	@Override
	@Transactional
	public Launch updateLaunch(Launch launch) {
		Objects.requireNonNull(launch.getId());
		validateLaunch(launch);
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

	@Override
	public void validateLaunch(Launch launch) {
		String validation = new LaunchRules(launch).validateLaunch();

		if (null != validation)
			throw new BusinesException(validation);

	}

	@Override
	public Optional<Launch> findById(Long id) {		
		return launchRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getBalanceUser(Long id) {
		BigDecimal recipe = launchRepository.getBalanceTypeLaunchAndUser(id, TypeLaunch.RECIPE);
		BigDecimal expense = launchRepository.getBalanceTypeLaunchAndUser(id, TypeLaunch.EXPENSE);
		
		if(null == recipe)
			recipe = BigDecimal.ZERO;
		if(null == expense)
			expense = BigDecimal.ZERO;
		
		return recipe.subtract(expense);
	}

}
