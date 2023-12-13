/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:14 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.exceptions.IllegalOptionException;
import com.nagendar.learning.factory.OptionExecutorFactory;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.option.OptionExecutor;

import java.util.Objects;

public class CutCommandExecutor implements CommandExecutor {
	private final Printer consolePrinter;
	private final Printer filePrinter;
	private final OptionExecutorFactory optionExecutorFactory;

	public CutCommandExecutor(Printer consolePrinter, Printer filePrinter) {
		this.consolePrinter = consolePrinter;
		this.filePrinter = filePrinter;
		this.optionExecutorFactory = new OptionExecutorFactory(consolePrinter, filePrinter);
	}

	@Override
	public void execute(Command command) {
		ProcessedCommand processedCommand = (ProcessedCommand) command;
		OptionExecutor optionExecutor = optionExecutorFactory.getOptionExecutor(processedCommand.getOption());
		if (Objects.isNull(optionExecutor)) {
			throw new IllegalOptionException(String.format("Expected options: %s, Found %s",
					CommonConstants.ALLOWED_OPTIONS, processedCommand.getOption()));
		}
		optionExecutor.executeOption(processedCommand);
	}
}
