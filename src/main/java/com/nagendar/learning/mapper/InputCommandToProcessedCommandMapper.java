/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 5:43 pm
 */

package com.nagendar.learning.mapper;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.executor.RangeResolver;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.util.List;

public class InputCommandToProcessedCommandMapper {
	private final RangeResolver rangeResolver;

	public InputCommandToProcessedCommandMapper(RangeResolver rangeResolver) {
		this.rangeResolver = rangeResolver;
	}

	public ProcessedCommand map(InputCommand inputCommand) {
		String rangeString = null;
		ProcessedCommand processedCommand = new ProcessedCommand();
		for (String option : inputCommand.getOptions()) {
			List<String> arguments = inputCommand.getOptionArguments(option);
			if (CommonConstants.RANGE_OPTIONS.contains(option)) {
				processedCommand.setOption(option);
				rangeString = arguments.stream()
						.reduce((a, b) -> a + CommonConstants.RANGE_SEPARATOR + b)
						.orElse("");
			}
			else {
				processedCommand.setDelimiter(arguments.get(arguments.size()-1));
			}
		}
		List<Range> ranges = rangeResolver.parseRanges(rangeString);
		List<Range> optimizedRanges = rangeResolver.mergeOverlappingRanges(ranges);
		processedCommand.setRanges(optimizedRanges);
		processedCommand.setFilePaths(inputCommand.getFilePaths());
		return processedCommand;
	}
}
