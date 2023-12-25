/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:02 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.executor.CommandExecutor;
import com.nagendar.learning.executor.CutCommandExecutor;
import com.nagendar.learning.executor.ExitCommandExecutor;
import com.nagendar.learning.executor.NonCutCommandExecutor;
import com.nagendar.learning.model.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandExecutorFactory {
	Map<String, CommandExecutor> commandExecutorMap;

	public CommandExecutorFactory(PrinterFactory printerFactory) {
		this.commandExecutorMap = new HashMap<>();
		commandExecutorMap.put(CommonConstants.CUT_COMMAND, new CutCommandExecutor(printerFactory));
		commandExecutorMap.put(CommonConstants.EXIT_COMMAND, new ExitCommandExecutor(printerFactory));
		commandExecutorMap.put(CommonConstants.NON_CUT_COMMAND, new NonCutCommandExecutor(printerFactory));
	}

	public CommandExecutor getCommandExecutor(Command command) {
		CommandExecutor commandExecutor = commandExecutorMap.get(command.getCommandType());
		if (Objects.isNull(commandExecutor)) {
			commandExecutor = commandExecutorMap.get(CommonConstants.NON_CUT_COMMAND);
		}
		return commandExecutor;
	}
}
