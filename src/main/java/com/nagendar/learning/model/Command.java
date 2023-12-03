/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:06 am
 */

package com.nagendar.learning.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.nagendar.learning.constants.CommonConstants.*;

public class Command {
	private final String rawCommandString;
	private String commandType;
	private final List<String> commandParams;
	private Set<String> flags;
	private Map<String, List<String>> optionsAndArguments;
	private Set<String> filePaths;

	public Command(String rawCommandString) {
		this.rawCommandString = rawCommandString;
		this.commandParams = Arrays.stream(rawCommandString.trim()
				.split(WHITESPACE_DELIMITER)).collect(Collectors.toList());
		if (!this.commandParams.isEmpty()) {
			String commandName = commandParams.remove(0);
			this.setCommandType(commandName);
		}
	}

	private void setCommandType(String commandType) {
		if (!(commandType.equals(CUT_COMMAND)
				|| commandType.equals(EXIT_COMMAND))) {
			this.commandType = NON_CUT_COMMAND;
		}
		else {
			this.commandType = commandType;
		}
	}

	public void parseParams() {
		tokenizeParams();
	}

	private void tokenizeParams() {
		List<String> rawFlags = new ArrayList<>();
		Map<String, List<String>> rawOptionsAndArguments = new HashMap<>();
		List<String> rawFilePaths = new ArrayList<>();

		int index = 0;
		boolean isArgument = false;
		String currentOption = null;

		while (index < this.commandParams.size()) {
			String param = this.commandParams.get(index);
			if (param.startsWith(COMMAND_OPTION_AND_FLAG_DELIMITER)) {
				if (param.length() == 2) {
					rawFlags.add(param);
				}
				else {
					currentOption = param;
					isArgument = true;
				}
			}
			else if (isArgument) {
				// TODO: add support to args of options without whitespace in b/w option and arg
				rawOptionsAndArguments.putIfAbsent(currentOption, new ArrayList<>());
				// the below handles the delimiter option being set to ' ' or " "
				if (param.equals(DOUBLE_QUOTE_CHARACTER)) {
					rawOptionsAndArguments.get(currentOption).add(WHITESPACE_DELIMITER);
				}
				else if (param.equals(SINGLE_QUOTE_CHARACTER)) {
					rawOptionsAndArguments.get(currentOption).add(WHITESPACE_DELIMITER);
				}
				else rawOptionsAndArguments.get(currentOption).add(param);
				isArgument = false;
			}
			else if (!param.equals(DOUBLE_QUOTE_CHARACTER)
					&& !param.equals(SINGLE_QUOTE_CHARACTER)) {
				rawFilePaths.add(param.trim());
			}
			index++;
		}
		parseFlags(rawFlags);
		parseOptions(rawOptionsAndArguments);
		parseFilePaths(rawFilePaths);
	}

	private void parseFlags(List<String> rawFlags) {
		this.flags = rawFlags.stream()
				.map(String::trim)
				.filter(option -> option.startsWith(COMMAND_OPTION_AND_FLAG_DELIMITER))
				.map(option -> option.substring(1))
				.flatMap(option -> option.chars().mapToObj(c -> String.valueOf((char) c)))
				.collect(Collectors.toCollection(HashSet::new));
	}

	private void parseOptions(Map<String, List<String>> rawOptionsAndArguments) {
		this.optionsAndArguments = new HashMap<>();
		for (String option : rawOptionsAndArguments.keySet()) {
			String optionWithoutDelimiter = option.substring(1);
			optionsAndArguments.put(optionWithoutDelimiter, rawOptionsAndArguments.get(option));
		}
	}

	private void parseFilePaths(List<String> rawFilePaths) {
		List<String> filePathsWithSpaces = new ArrayList<>();

		StringBuilder prefix = null;
		for (String rawFilePath : rawFilePaths) {
			if ((rawFilePath.startsWith(DOUBLE_QUOTE_CHARACTER)
					|| rawFilePath.startsWith(SINGLE_QUOTE_CHARACTER))
				&& (rawFilePath.endsWith(DOUBLE_QUOTE_CHARACTER)
					|| rawFilePath.endsWith(SINGLE_QUOTE_CHARACTER))) {
				prefix = new StringBuilder();
				prefix.append(rawFilePath, 1, rawFilePath.length()-1);
				filePathsWithSpaces.add(prefix.toString());
				prefix = null;
			}
			else if (rawFilePath.startsWith(DOUBLE_QUOTE_CHARACTER)
					|| rawFilePath.startsWith(SINGLE_QUOTE_CHARACTER)) {
				prefix = new StringBuilder();
				prefix.append(rawFilePath.substring(1))
						.append(WHITESPACE_DELIMITER);
			}
			else if ((rawFilePath.endsWith(DOUBLE_QUOTE_CHARACTER)
					|| rawFilePath.endsWith(SINGLE_QUOTE_CHARACTER))
					&& Objects.nonNull(prefix)) {
				prefix.append(rawFilePath, 0, rawFilePath.length()-1);
				filePathsWithSpaces.add(prefix.toString());
				prefix = null;
			}
			else if (Objects.nonNull(prefix)) {
				prefix.append(rawFilePath)
						.append(WHITESPACE_DELIMITER);
			}
			else {
				filePathsWithSpaces.add(rawFilePath);
			}
		}

		this.filePaths = new HashSet<>(filePathsWithSpaces);
	}

	public Set<String> getFlags() {
		return flags;
	}

	public Set<String> getOptions() {
		return optionsAndArguments.keySet();
	}

	public Set<String> getFilePaths() {
		return filePaths;
	}

	public String getCommandType() {
		return commandType;
	}
}
