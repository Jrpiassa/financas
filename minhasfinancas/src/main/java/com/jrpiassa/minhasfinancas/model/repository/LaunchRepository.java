package com.jrpiassa.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

public interface LaunchRepository extends JpaRepository<Launch, Long> {

	@Query(value = "select sum(l.value) from Launch l join l.user u "
			+ " where u.id= :idUser and l.typeLaunch= :typeLaunch group by u")
	BigDecimal getBalanceTypeLaunchAndUser(@Param("idUser") Long idUser, @Param("typeLaunch") TypeLaunch typeLaunch);

}
