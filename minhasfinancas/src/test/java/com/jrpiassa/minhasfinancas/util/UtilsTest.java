package com.jrpiassa.minhasfinancas.util;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public class UtilsTest {
	
	@Test
	public void when_validateDescription_then_return_validation() {
		String description = "Teste01";		
		boolean validateDescription = Utils.validateDescription(description);		
		Assertions.assertThat(validateDescription).isFalse();
		
		description = "";		
		validateDescription = Utils.validateDescription(description);		
		Assertions.assertThat(validateDescription).isTrue();
		
		description = null;		
		validateDescription = Utils.validateDescription(description);		
		Assertions.assertThat(validateDescription).isTrue();		
	}
	
	@Test
	public void when_validateYear_then_return_validation() {
		Integer year = 2020;		
		boolean validateYear = Utils.validateYear(year);		
		Assertions.assertThat(validateYear).isFalse();
		
		year = 1949;		
		validateYear = Utils.validateYear(year);		
		Assertions.assertThat(validateYear).isTrue();
		
		year = 99999;		
		validateYear = Utils.validateYear(year);		
		Assertions.assertThat(validateYear).isTrue();		
	}
	
	@Test
	public void when_validateMonth_then_return_validation() {
		Integer month = 6;		
		boolean validateMonth = Utils.validateMonth(month);		
		Assertions.assertThat(validateMonth).isFalse();
		
		month = 0;		
		validateMonth = Utils.validateMonth(month);		
		Assertions.assertThat(validateMonth).isTrue();
		
		month = 13;		
		validateMonth = Utils.validateMonth(month);		
		Assertions.assertThat(validateMonth).isTrue();		
	}
	
	@Test
	public void when_validateValue_then_return_validation() {
		BigDecimal value = new BigDecimal(20);		
		boolean validateValue = Utils.validateValue(value);		
		Assertions.assertThat(validateValue).isFalse();
		
		value = new BigDecimal(0);		
		validateValue = Utils.validateValue(value);		
		Assertions.assertThat(validateValue).isTrue();
		
		value = null;		
		validateValue = Utils.validateValue(value);		
		Assertions.assertThat(validateValue).isTrue();		
	}
	
	@Test
	public void when_typeLaunchIsNull_then_return_validation() {				
		boolean validateTypeLaunch = Utils.typeLaunchIsNull(TypeLaunch.EXPENSE);		
		Assertions.assertThat(validateTypeLaunch).isFalse();
		
		validateTypeLaunch = Utils.typeLaunchIsNull(null);		
		Assertions.assertThat(validateTypeLaunch).isTrue();			
	}
}
