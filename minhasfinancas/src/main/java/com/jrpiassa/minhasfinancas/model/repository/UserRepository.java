package com.jrpiassa.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrpiassa.minhasfinancas.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
