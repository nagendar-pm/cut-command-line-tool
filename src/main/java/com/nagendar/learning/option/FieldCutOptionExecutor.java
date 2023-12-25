/*
 * @author: pagidimarri.nagendar
 * @createdAt: 16/12/23 12:25pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.exceptions.IllegalFlagException;
import com.nagendar.learning.factory.PrinterFactory;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.Flag;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FieldCutOptionExecutor implements OptionExecutor {
	private final PrinterFactory printerFactory;

	public FieldCutOptionExecutor(PrinterFactory printerFactory) {
		this.printerFactory = printerFactory;
	}

	@Override
	public void executeOption(Command command) {
		ProcessedCommand processedCommand = (ProcessedCommand) command;
		Printer printer = printerFactory.getPrinter(command.getIsTerminal());
		boolean isFirstFile = true;
		for (String filePath : processedCommand.getFilePaths()) {
			printerFactory.getConsolePrinter()
					.print(String.format("Executing file: %s", filePath), false);
			Path path = Paths.get(filePath);
			try {
				StringBuilder sb = new StringBuilder();
				for (String line : Files.readAllLines(path)) {
					String s = handleLine(line, processedCommand);
					sb.append(s).append("\n");
				}
				String output = sb.deleteCharAt(sb.length()-1).toString();
				printer.print(String.format("%s", output), !isFirstFile);
				isFirstFile = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String handleLine(String line, ProcessedCommand processedCommand) {
		StringBuilder stringBuilder = new StringBuilder();

		// setting -w flag
		if (processedCommand.getDelimiter().isEmpty()) {
			if (processedCommand.getFlags().contains(
					Flag.FLAG_WHITESPACE_DELIMITER)) {
				processedCommand.setDelimiter(" ");
			}
			else {
				processedCommand.setDelimiter("\t");
			}
		} else if (processedCommand.getFlags().contains(
				Flag.FLAG_WHITESPACE_DELIMITER)) {
			throw new IllegalFlagException("Expected either -d or -w, Found both");
		}

		String[] fields = line.split(processedCommand.getDelimiter());

		// setting -s flag
		if (processedCommand.getFlags().contains(
				Flag.FLAG_SUPPRESS_LINES_WITH_NO_FIELD_DELIMITER)) {
			if (fields.length == 1) {
				return "";
			}
		}
		for (Range range : processedCommand.getRanges()) {
			int from = Math.max(1, range.getFrom()) - 1;
			int to = Math.min(fields.length, range.getTo()) - 1;
			for (int i=from; i<=to; i++) {
				stringBuilder.append(fields[i])
						.append(processedCommand.getDelimiter());
			}
		}
		return stringBuilder.toString();
	}
}
