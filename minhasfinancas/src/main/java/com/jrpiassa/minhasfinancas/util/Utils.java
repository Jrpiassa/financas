package com.jrpiassa.minhasfinancas.util;

import java.math.BigDecimal;

import com.jrpiassa.minhasfinancas.model.enuns.TypeLaunch;

public class Utils {

	public static boolean validateDescription(String description) {
		return null == description || description.trim().isEmpty();
	}

	public static boolean validateYear(Integer year) {
		return null == year || (year < 1950 || year > 9999);
	}

	public static boolean validateMonth(Integer month) {
		return null == month || (month < 1 || month > 12);
	}

	public static boolean validateValue(BigDecimal value) {
		return null == value || value.compareTo(BigDecimal.ZERO) < 1;
	}

	public static boolean typeLaunchIsNull(TypeLaunch typeLaunch) {
		return null == typeLaunch;
	}

}
