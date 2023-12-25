/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 12:30 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.mapper.InputCommandToProcessedCommandMapper;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.InputCommand;

public class CommandProcessorFactory {
	private final InputCommandToProcessedCommandMapper inputCommandToProcessedCommandMapper;

	public CommandProcessorFactory(InputCommandToProcessedCommandMapper inputCommandToProcessedCommandMapper) {
		this.inputCommandToProcessedCommandMapper = inputCommandToProcessedCommandMapper;
	}

	public Command processCommandForExecution(Command command) {
		switch (command.getCommandType()) {
			case CommonConstants.CUT_COMMAND -> {
				return inputCommandToProcessedCommandMapper.map((InputCommand) command);
			}
			case CommonConstants.NON_CUT_COMMAND, CommonConstants.EXIT_COMMAND -> {
				return command;
			}
		}
		return command;
	}
}
