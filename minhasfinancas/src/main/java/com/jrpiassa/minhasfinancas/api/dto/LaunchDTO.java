package com.jrpiassa.minhasfinancas.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaunchDTO {
	private Long id;
	private String description;
	private Integer month;
	private Integer year;
	private BigDecimal value;
	private Long idUser;
	private String launchType;
	private String launchStatus;
}
