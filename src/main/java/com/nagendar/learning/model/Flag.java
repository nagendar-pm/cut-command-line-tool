/*
 * @author: pagidimarri.nagendar
 * @createdAt: 17/12/23 7:34pm
 */

package com.nagendar.learning.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Flag {
	FLAG_NOT_SPLIT_MULTI_BYTE_CHARACTERS("n"),
	FLAG_SUPPRESS_LINES_WITH_NO_FIELD_DELIMITER("s"),
	FLAG_WHITESPACE_DELIMITER("w");
	private final String flagString;

	Flag(String flagString) {
		this.flagString = flagString;
	}

	public static Flag getFlagFromString(String inputFlag) {
		return Arrays.stream(Flag.values())
				.filter(flag -> flag.flagString.equals(inputFlag))
				.findFirst()
				.orElse(null);
	}

	public static boolean isFlagAllowed(String inputFlag) {
		return Arrays.stream(Flag.values())
				.anyMatch(flag -> flag.flagString.equals(inputFlag));
	}

	public static List<String> getFlagsList() {
		return Arrays.stream(Flag.values())
				.map(flag -> flag.flagString)
				.collect(Collectors.toList());
	}
}
