/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:18 pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterCutOptionExecutor implements OptionExecutor {
	private final Printer printer;

	public CharacterCutOptionExecutor(Printer printer) {
		this.printer = printer;
	}

	@Override
	public void executeOption(Command command) {
		ProcessedCommand metaInfo = (ProcessedCommand) command;
		for (String filePath : metaInfo.getFilePaths()) {
			Path path = Paths.get(filePath);
			try {
				StringBuilder sb = new StringBuilder();
				for (String line : Files.readAllLines(path)) {
					String s = handleLine(line, metaInfo);
					sb.append(s).append("\n");
				}
				String output = sb.toString();
				printer.print(String.format("%s", output));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String handleLine(String line, ProcessedCommand metaInfo) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Range range : metaInfo.getRanges()) {
			int from = Math.max(1, range.getFrom()) - 1;
			int to = Math.min(line.length(), range.getTo()) - 1;
			stringBuilder.append(line, from, to + 1);
		}
		return stringBuilder.toString();
	}
}
