/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:14 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.exceptions.IllegalOptionException;
import com.nagendar.learning.factory.OptionExecutorFactory;
import com.nagendar.learning.factory.PrinterFactory;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.option.OptionExecutor;

import java.util.Objects;

public class CutCommandExecutor implements CommandExecutor {
	private final OptionExecutorFactory optionExecutorFactory;
	private final PrinterFactory printerFactory;

	public CutCommandExecutor(PrinterFactory printerFactory) {
		this.printerFactory = printerFactory;
		this.optionExecutorFactory = new OptionExecutorFactory(printerFactory);
	}

	@Override
	public void execute(Command command) {
		ProcessedCommand processedCommand = (ProcessedCommand) command;
		printerFactory.getConsolePrinter()
				.print(String.format("Executing the command `%s`...", command.getRawCommandString()), false);
		OptionExecutor optionExecutor = optionExecutorFactory.getOptionExecutor(processedCommand.getOption());
		if (Objects.isNull(optionExecutor)) {
			throw new IllegalOptionException(String.format("Expected options: %s, Found %s",
					CommonConstants.ALLOWED_OPTIONS, processedCommand.getOption()));
		}
		optionExecutor.executeOption(processedCommand);
	}
}
