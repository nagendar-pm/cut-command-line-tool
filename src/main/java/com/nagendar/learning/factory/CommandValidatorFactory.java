/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 5:52 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.validator.CommandValidator;
import com.nagendar.learning.validator.CutCommandValidator;
import com.nagendar.learning.validator.ExitCommandValidator;
import com.nagendar.learning.validator.NonCutCommandValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandValidatorFactory {
	private final Map<String, CommandValidator> commandValidatorMap;

	public CommandValidatorFactory() {
		this.commandValidatorMap = new HashMap<>();
		commandValidatorMap.put(CommonConstants.CUT_COMMAND, new CutCommandValidator());
		commandValidatorMap.put(CommonConstants.EXIT_COMMAND, new ExitCommandValidator());
		commandValidatorMap.put(CommonConstants.NON_CUT_COMMAND, new NonCutCommandValidator());
	}

	public CommandValidator getCommandValidator(InputCommand command) {
		CommandValidator commandValidator = commandValidatorMap.get(command.getCommandType());
		if (Objects.isNull(commandValidator)) {
			commandValidator = commandValidatorMap.get(CommonConstants.NON_CUT_COMMAND);
		}
		return commandValidator;
	}
}
