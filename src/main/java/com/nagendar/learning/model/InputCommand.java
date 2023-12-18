/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:06 am
 */

package com.nagendar.learning.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.nagendar.learning.constants.CommonConstants.*;

public class InputCommand extends Command{
	private final List<String> commandParams;
	private Set<String> flags;
	private Map<String, List<String>> optionsAndArguments;
	private Set<String> filePaths;

	public InputCommand(String rawCommandString) {
		super(rawCommandString);
		this.commandParams = Arrays.stream(rawCommandString.trim()
				.split(WHITESPACE_DELIMITER)).collect(Collectors.toList());
		if (!this.commandParams.isEmpty()) {
			String commandName = commandParams.remove(0);
			setCommandType(commandName);
		}
	}

	public void parseParams() {
		tokenizeParams();
	}

	private void tokenizeParams() {
		LinkedList<String> rawFlags = new LinkedList<>();
		Map<String, List<String>> rawOptionsAndArguments = new HashMap<>();
		List<String> rawFilePaths = new ArrayList<>();

		int index = 0;
		String lastOption = "";
		boolean isArgumentCandidate = false;

		while (index < this.commandParams.size()) {
			String param = this.commandParams.get(index);
			if (param.startsWith(COMMAND_OPTION_AND_FLAG_DELIMITER)
					&& Flag.isFlagAllowed(param.substring(1, 2))) {
				rawFlags.offerLast(param);
			}
			else if (param.startsWith(COMMAND_OPTION_AND_FLAG_DELIMITER)
					&& Option.isOptionAllowed(param.substring(1, 2))) {
				String option = param.substring(1, 2);
				rawOptionsAndArguments.putIfAbsent(option, new ArrayList<>());
				if (param.length() > 2) {
					rawOptionsAndArguments.get(option).add(param.substring(2));
				}
				else {
					lastOption = option;
					isArgumentCandidate = true;
				}
			}
			else if (isArgumentCandidate) {
				rawOptionsAndArguments.putIfAbsent(lastOption, new ArrayList<>());
				// the below handles the delimiter option being set to ' ' or " "
				if (param.equals(DOUBLE_QUOTE_CHARACTER)) {
					rawOptionsAndArguments.get(lastOption).add(WHITESPACE_DELIMITER);
				}
				else if (param.equals(SINGLE_QUOTE_CHARACTER)) {
					rawOptionsAndArguments.get(lastOption).add(WHITESPACE_DELIMITER);
				}
				else rawOptionsAndArguments.get(lastOption).add(param);
				isArgumentCandidate = false;
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
			// we can specify a list of ranges like `-f 1,2 -f 3-4`
			optionsAndArguments.put(option, rawOptionsAndArguments.get(option));
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

	public List<String> getOptionArguments(String option) {
		return optionsAndArguments.get(option);
	}

	public Set<String> getFilePaths() {
		return filePaths;
	}

}
