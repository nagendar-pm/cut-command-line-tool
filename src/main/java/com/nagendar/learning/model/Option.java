/*
 * @author: pagidimarri.nagendar
 * @createdAt: 17/12/23 7:32pm
 */

package com.nagendar.learning.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.nagendar.learning.model.Flag.*;

public enum Option {
	OPTION_BYTE_LIST("b",
			Collections.singletonList(FLAG_NOT_SPLIT_MULTI_BYTE_CHARACTERS), true),
	OPTION_CHARACTER_LIST("c", null, true),
	OPTION_FIELD_SPECIFIER("f",
			List.of(FLAG_SUPPRESS_LINES_WITH_NO_FIELD_DELIMITER,
			FLAG_WHITESPACE_DELIMITER), true),
	OPTION_DELIMITER_SPECIFIER("d", null, false);
	private final String optionString;
	private final List<Flag> allowedFlags;
	private final boolean isRangeOption;

	Option(String optionString, List<Flag> allowedFlags, boolean isRangeOption) {
		this.optionString = optionString;
		this.allowedFlags = allowedFlags;
		this.isRangeOption = isRangeOption;
	}

	public List<Flag> getAllowedFlags() {
		return allowedFlags;
	}

	public boolean isRangeOption() {
		return isRangeOption;
	}

	public static Option getOptionFromString(String inputOption) {
		return Arrays.stream(Option.values())
				.filter(option -> option.optionString.equals(inputOption))
				.findFirst()
				.orElse(null);
	}

	public static boolean isOptionAllowed(String inputOption) {
		return Arrays.stream(Option.values())
				.anyMatch(option -> option.optionString.equals(inputOption));
	}

	public static boolean isStringRangeOption(String inputOption) {
		Option option = Arrays.stream(Option.values())
				.filter(optionStr -> optionStr.optionString.equals(inputOption))
				.findFirst()
				.orElse(null);
		return Objects.nonNull(option) && option.isRangeOption;
	}

	public static List<String> getOptionsList() {
		return Arrays.stream(Option.values())
				.map(option -> option.optionString)
				.collect(Collectors.toList());
	}
}
