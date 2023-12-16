/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 5:43pm
 */

package com.nagendar.learning.mapper;

import com.nagendar.learning.exceptions.IllegalOptionException;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.util.List;

import static com.nagendar.learning.constants.CommonConstants.*;

public class InputCommandToProcessedCommandMapper {
	private final RangeResolver rangeResolver;

	public InputCommandToProcessedCommandMapper(RangeResolver rangeResolver) {
		this.rangeResolver = rangeResolver;
	}

	public ProcessedCommand map(InputCommand inputCommand) {
		String rangeString = null;
		ProcessedCommand processedCommand = new ProcessedCommand(inputCommand.getRawCommandString());
		for (String option : inputCommand.getOptions()) {
			List<String> arguments = inputCommand.getOptionArguments(option);
			if (RANGE_OPTIONS.contains(option)) {
				processedCommand.setOption(option);
				rangeString = arguments.stream()
						.reduce((a, b) -> a + RANGE_SEPARATOR + b)
						.orElse("");
			}
			else {
				String delimiter = getDelimiter(arguments.get(arguments.size()-1));
				processedCommand.setDelimiter(delimiter);
			}
		}
		List<Range> ranges = rangeResolver.parseRanges(rangeString);
		List<Range> optimizedRanges = rangeResolver.mergeOverlappingRanges(ranges);
		processedCommand.setRanges(optimizedRanges);
		processedCommand.setFilePaths(inputCommand.getFilePaths());
		processedCommand.setIsTerminal(inputCommand.getIsTerminal());
		return processedCommand;
	}

	private String getDelimiter(String argument) {
		if (argument.length() == 1) {
			return argument;
		}
		else if (argument.startsWith(DOUBLE_QUOTE_CHARACTER)
				|| argument.startsWith(SINGLE_QUOTE_CHARACTER)) {
			if (!(argument.endsWith(DOUBLE_QUOTE_CHARACTER) || argument.endsWith(SINGLE_QUOTE_CHARACTER))) {
				throw new IllegalOptionException(
						String.format("Delimiter shouldn't be more than 1 character, Found %s", argument));
			}
			return argument.substring(1, argument.length()-1);
		}
		throw new IllegalOptionException(
				String.format("Delimiter shouldn't be more than 1 character, Found %s", argument));
	}
}
