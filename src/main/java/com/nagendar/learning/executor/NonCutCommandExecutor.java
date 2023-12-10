/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:06 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.exceptions.UnknownCommandException;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.InputCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static com.nagendar.learning.constants.CommonConstants.WHITESPACE_DELIMITER;

public class NonCutCommandExecutor implements CommandExecutor {
	private final Printer consolePrinter;
	private final Printer filePrinter;

	public NonCutCommandExecutor(Printer consolePrinter, Printer filePrinter) {
		this.consolePrinter = consolePrinter;
		this.filePrinter = filePrinter;
	}

	@Override
	public void execute(InputCommand command) {
		String commandStr = command.getRawCommandString();
		consolePrinter.print(String.format("Executing the command `%s`...", commandStr));
		List<String> outputLines = new LinkedList<>();
		try {
			Process process = Runtime.getRuntime()
					.exec(commandStr.split(WHITESPACE_DELIMITER));
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				outputLines.add(line);
			}
			filePrinter.print(outputLines);
			consolePrinter.print(String.format("Output: %s", outputLines));
		} catch (IOException e) {
			throw new UnknownCommandException(String.format("Unknown Shell command triggered: %s", command));
		}
	}
}
