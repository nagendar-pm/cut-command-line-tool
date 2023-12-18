/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 5:43pm
 */

package com.nagendar.learning.mapper;

import com.nagendar.learning.exceptions.IllegalFlagException;
import com.nagendar.learning.exceptions.IllegalOptionException;
import com.nagendar.learning.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.nagendar.learning.constants.CommonConstants.*;

public class InputCommandToProcessedCommandMapper {
	private final RangeResolver rangeResolver;

	public InputCommandToProcessedCommandMapper(RangeResolver rangeResolver) {
		this.rangeResolver = rangeResolver;
	}

	public ProcessedCommand map(InputCommand inputCommand) {
		String rangeString = null;
		ProcessedCommand processedCommand = new ProcessedCommand(inputCommand.getRawCommandString());
		processedCommand.setFilePaths(inputCommand.getFilePaths());
		processedCommand.setIsTerminal(inputCommand.getIsTerminal());

		for (String optionStr : inputCommand.getOptions()) {
			Option option = Option.getOptionFromString(optionStr);
			List<String> arguments = inputCommand.getOptionArguments(optionStr);
			if (option.isRangeOption()) {
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
		if (processedCommand.getOption() != Option.OPTION_FIELD_SPECIFIER
			&& (Objects.nonNull(processedCommand.getDelimiter()) && !processedCommand.getDelimiter().isEmpty())) {
			throw new IllegalOptionException(String.format("Unexpected Option - delimiter found: '%s'",
					processedCommand.getDelimiter()));
		}
		Set<Flag> flags = new HashSet<>();
		for (String flagStr : inputCommand.getFlags()) {
			Flag flag = Flag.getFlagFromString(flagStr);
			List<Flag> allowedFlagsOfOption = processedCommand.getOption().getAllowedFlags();
			if (Objects.isNull(allowedFlagsOfOption) || !allowedFlagsOfOption.contains(flag)) {
				throw new IllegalFlagException(String.format("The allowed flags for option %s are %s, Found %s",
						processedCommand.getOption(),
						processedCommand.getOption().getAllowedFlags(),
						flag));
			}
			flags.add(flag);
		}
		processedCommand.setFlags(flags);
		List<Range> ranges = rangeResolver.parseRanges(rangeString);
		List<Range> optimizedRanges = rangeResolver.mergeOverlappingRanges(ranges);
		processedCommand.setRanges(optimizedRanges);

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
