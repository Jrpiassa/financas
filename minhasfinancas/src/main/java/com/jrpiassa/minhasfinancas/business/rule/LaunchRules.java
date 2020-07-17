package com.jrpiassa.minhasfinancas.business.rule;

import com.jrpiassa.minhasfinancas.model.entity.Launch;
import com.jrpiassa.minhasfinancas.util.Utils;

public class LaunchRules {

	private Launch launch;

	public LaunchRules(Launch launch) {
		this.launch = launch;
	}

	public String validateLaunch() {

		if (Utils.validateDescription(launch.getDescription()))
			return "Informe uma descrição válida !";

		if (Utils.validateMonth(launch.getMonth()))
			return "Informe um mês válido !";

		if (Utils.validateYear(launch.getYear()))
			return "Informe um ano válido !";

		if (null == launch.getUser() || null == launch.getUser().getId())
			return "Informe um usuário !";

		if (Utils.validateValue(launch.getValue()))
			return "Informe um valor válido !";

		if (Utils.typeLaunchIsNull(launch.getTypeLaunch()))
			return "Informe o tipo do lançamento !";

		return null;
	}

}
