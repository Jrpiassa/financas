package com.jrpiassa.minhasfinancas.service;

import java.util.List;

import com.jrpiassa.minhasfinancas.model.entity.Launch;

public interface LaunchService {
	
	Launch saveLaunch(Launch launch);
	
	Launch updateLaunch(Launch launch);
	
	void deleteLaunch(Launch launch);
	
	List<Launch> findLaunch(Launch launch);
}
