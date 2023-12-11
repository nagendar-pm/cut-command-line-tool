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
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.InputCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandExecutorFactory {
	Map<String, CommandExecutor> commandExecutorMap;

	public CommandExecutorFactory(Printer consolePrinter, Printer filePrinter) {
		this.commandExecutorMap = new HashMap<>();
		commandExecutorMap.put(CommonConstants.CUT_COMMAND, new CutCommandExecutor(consolePrinter));
		commandExecutorMap.put(CommonConstants.EXIT_COMMAND, new ExitCommandExecutor(consolePrinter));
		commandExecutorMap.put(CommonConstants.NON_CUT_COMMAND, new NonCutCommandExecutor(consolePrinter, filePrinter));
	}

	public CommandExecutor getCommandExecutor(Command command) {
		CommandExecutor commandExecutor = commandExecutorMap.get(command.getCommandType());
		if (Objects.isNull(commandExecutor)) {
			commandExecutor = commandExecutorMap.get(CommonConstants.NON_CUT_COMMAND);
		}
		return commandExecutor;
	}
}
