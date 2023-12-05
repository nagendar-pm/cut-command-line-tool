/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:18 pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.CommandMetaInfo;
import com.nagendar.learning.model.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class CharacterCutOptionExecutor implements OptionExecutor {
	private final Printer printer;

	public CharacterCutOptionExecutor(Printer printer) {
		this.printer = printer;
	}

	@Override
	public void executeOption(CommandMetaInfo metaInfo) {
		Path path = Paths.get(metaInfo.getFilePath());
		try {
			Optional<String> output = Files.readAllLines(path)
					.stream()
					.map(line -> handleLine(line, metaInfo))
					.reduce((a, b) -> a + " " + b);
			printer.print(String.format("%s", output));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String handleLine(String line, CommandMetaInfo metaInfo) {
		String[] words = line.split(metaInfo.getDelimiter());
		StringBuilder stringBuilder = new StringBuilder();
		for (Range range : metaInfo.getRanges()) {
			for (int i=range.getFrom(); i<=range.getTo(); i++) {
				if (i<0 || i>words.length) break;
				stringBuilder.append(words[i-1]);
			}
		}
		return stringBuilder.toString();
	}
}
