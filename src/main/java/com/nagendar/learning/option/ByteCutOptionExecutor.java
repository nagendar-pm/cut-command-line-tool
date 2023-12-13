/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 6:55 pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ByteCutOptionExecutor implements OptionExecutor {
	private final Printer consolePrinter;
	private final Printer filePrinter;

	public ByteCutOptionExecutor(Printer consolePrinter, Printer filePrinter) {
		this.consolePrinter = consolePrinter;
		this.filePrinter = filePrinter;
	}

	@Override
	public void executeOption(Command command) {
		ProcessedCommand processedCommand = (ProcessedCommand) command;
		Printer printer;
		if (command.getIsTerminal()) {
			printer = consolePrinter;
		}
		else {
			printer = filePrinter;
		}
		for (String filePath : processedCommand.getFilePaths()) {
			printer.print(String.format("Executing file: %s", filePath));
			Path path = Paths.get(filePath);
			try {
				StringBuilder sb = new StringBuilder();
				for (String line : Files.readAllLines(path)) {
					String s = handleLine(line, processedCommand);
					sb.append(s).append("\n");
				}
				String output = sb.toString();
				printer.print(String.format("%s", output));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String handleLine(String line, ProcessedCommand processedCommand) {
		StringBuilder stringBuilder = new StringBuilder();
		// TODO: learn about the charsets and their usage
		byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
		for (Range range : processedCommand.getRanges()) {
			int from = Math.max(1, range.getFrom()) - 1;
			int to = Math.min(line.length(), range.getTo()) - 1;
			byte[] bytesOfRange = Arrays.copyOfRange(bytes, from, to + 1);
			stringBuilder.append(Arrays.toString(bytesOfRange));
		}
		return stringBuilder.toString();
	}
}
