/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 12:15 pm
 */

package com.nagendar.learning.service;

import com.nagendar.learning.factory.CommandExecutorFactory;
import com.nagendar.learning.factory.CommandProcessorFactory;
import com.nagendar.learning.factory.CommandValidatorFactory;
import com.nagendar.learning.model.Command;

public class CommandProcessorServiceImpl implements CommandProcessorService {
	private final CommandValidatorFactory commandValidatorFactory;
	private final CommandProcessorFactory commandProcessorFactory;
	private final CommandExecutorFactory commandExecutorFactory;

	public CommandProcessorServiceImpl(CommandValidatorFactory commandValidatorFactory,
	                                   CommandProcessorFactory commandProcessorFactory,
	                                   CommandExecutorFactory commandExecutorFactory) {
		this.commandValidatorFactory = commandValidatorFactory;
		this.commandProcessorFactory = commandProcessorFactory;
		this.commandExecutorFactory = commandExecutorFactory;
	}


	@Override
	public void processCommand(Command command) {
		boolean isValidCommand = commandValidatorFactory.getCommandValidator(command)
				.validate(command);
		if (isValidCommand) {
			Command processedCommand = commandProcessorFactory.processCommandForExecution(command);
			commandExecutorFactory.getCommandExecutor(command)
					.execute(processedCommand);

		}
	}
}
