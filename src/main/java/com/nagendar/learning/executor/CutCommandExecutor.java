/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:14 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.CommandMetaInfo;
import com.nagendar.learning.model.Range;
import com.nagendar.learning.option.CharacterCutOptionExecutor;

import java.util.List;

public class CutCommandExecutor implements CommandExecutor {
	private final Printer printer;
	private final RangeResolver rangeResolver;

	public CutCommandExecutor(Printer printer, RangeResolver rangeResolver) {
		this.printer = printer;
		this.rangeResolver = rangeResolver;
	}

	@Override
	public void execute(Command command) {
		// 1. Resolve the ranges
		// 2. Then execute the flags and options
		String rangeString = null;
		CommandMetaInfo metaInfo = new CommandMetaInfo();
		for (String option : command.getOptions()) {
			List<String> arguments = command.getOptionArguments(option);
			if (CommonConstants.RANGE_OPTIONS.contains(option)) {
				metaInfo.setOption(option);
				rangeString = arguments.stream()
						.reduce((a, b) -> a + CommonConstants.RANGE_SEPARATOR + b)
						.orElse("");
			}
			else {
				metaInfo.setDelimiter(arguments.get(arguments.size()-1));
			}
		}
		List<Range> ranges = rangeResolver.parseRanges(rangeString);
		List<Range> optimizedRanges = rangeResolver.mergeOverlappingRanges(ranges);
		metaInfo.setRanges(optimizedRanges);
		metaInfo.setFilePaths(command.getFilePaths());

		CharacterCutOptionExecutor executor = new CharacterCutOptionExecutor(printer);
		executor.executeOption(metaInfo);
	}
}
