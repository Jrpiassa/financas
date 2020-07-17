package com.jrpiassa.minhasfinancas.service;

import java.util.List;
import java.util.Optional;

import com.jrpiassa.minhasfinancas.model.entity.Launch;

public interface LaunchService {
	
	Launch saveLaunch(Launch launch);
	
	Launch updateLaunch(Launch launch);
	
	void deleteLaunch(Launch launch);
	
	List<Launch> findLaunch(Launch launch);
	
	void validateLaunch(Launch launch);
	
	Optional<Launch> findById(Long id);
}
