package com.jrpiassa.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jrpiassa.minhasfinancas.model.enuns.StatusLaunch;
import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "launch", schema = "financas")
@Builder@Data@NoArgsConstructor@AllArgsConstructor
public class Launch {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "month")
	private Integer month;

	@Column(name = "year")
	private Integer year;

	@Column(name = "value")
	private BigDecimal value;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@Column(name = "date_register")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonIgnore
	private LocalDate dateRegister;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private TypeLaunch typeLaunch;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusLaunch statusLaunch;

}
