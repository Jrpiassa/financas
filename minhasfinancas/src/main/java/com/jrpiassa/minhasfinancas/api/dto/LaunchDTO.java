package com.jrpiassa.minhasfinancas.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaunchDTO {
	private Long id;
	private String description;
	private Integer month;
	private Integer year;
	private BigDecimal value;
	private Long idUser;
	private String typeLaunch;
	private String statusLaunch;
}
