package com.jrpiassa.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrpiassa.minhasfinancas.model.entity.Launch;

public interface LaunchRepository extends JpaRepository<Launch, Long>{

}
