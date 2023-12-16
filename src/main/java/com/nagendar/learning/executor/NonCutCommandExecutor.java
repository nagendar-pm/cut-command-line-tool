/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:06 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.exceptions.UnknownCommandException;
import com.nagendar.learning.factory.PrinterFactory;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static com.nagendar.learning.constants.CommonConstants.WHITESPACE_DELIMITER;

public class NonCutCommandExecutor implements CommandExecutor {
	private final PrinterFactory printerFactory;

	public NonCutCommandExecutor(PrinterFactory printerFactory) {
		this.printerFactory = printerFactory;
	}

	@Override
	public void execute(Command command) {
		String commandStr = command.getRawCommandString();
		printerFactory.getConsolePrinter()
				.print(String.format("Executing the command `%s`...", commandStr), false);
		List<String> outputLines = new LinkedList<>();
		Printer printer = printerFactory.getPrinter(command.getIsTerminal());
		try {
			Process process = Runtime.getRuntime()
					.exec(commandStr.split(WHITESPACE_DELIMITER));
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				outputLines.add(line);
			}
			printer.print(outputLines, false);
			printerFactory.getConsolePrinter()
					.print(String.format("Output: %s", outputLines), false);
		} catch (IOException e) {
			throw new UnknownCommandException(String.format("Unknown Shell command triggered: %s", command));
		}
	}
}
