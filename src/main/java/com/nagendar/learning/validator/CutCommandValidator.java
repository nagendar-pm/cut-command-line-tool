/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:38 am
 */

package com.nagendar.learning.validator;

import com.nagendar.learning.exceptions.FileDoesNotExistsException;
import com.nagendar.learning.exceptions.IllegalFlagException;
import com.nagendar.learning.exceptions.InvalidRangeException;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.Flag;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.model.Option;
import com.nagendar.learning.utils.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CutCommandValidator implements CommandValidator {
	@Override
	public boolean validate(Command command) {
		InputCommand inputCommand = (InputCommand) command;
		inputCommand.parseParams();
		validateCommandFlags(inputCommand);
		validateCommandOptions(inputCommand);
		validateCommandFilePaths(inputCommand);
		return true;
	}

	private void validateCommandFlags(InputCommand command) {
		for (String flag : command.getFlags()) {
			if (!Flag.isFlagAllowed(flag)) {
				throw new IllegalFlagException(String
						.format("Found unexpected flag %s, Expected from %s", flag, Flag.getFlagsList()));
			}
		}
	}

	private void validateCommandOptions(InputCommand command) {
		Pattern pattern = Pattern.compile("([0-9]*\\-*[0-9]*,*){1,}", Pattern.CASE_INSENSITIVE);
		for (String option : command.getOptions()) {
			if (!Option.isOptionAllowed(option)) {
				throw new IllegalFlagException(String
						.format("Found unexpected option %s, Expected from %s", option, Option.getOptionsList()));
			}
			for (String argument : command.getOptionArguments(option)) {
				if (Option.isStringRangeOption(option)) {
					Matcher matcher = pattern.matcher(argument);
					boolean matchFound = matcher.matches();
					if(!matchFound || (matcher.end() - matcher.start()) == 0) {
						throw new InvalidRangeException(String.format("Expected a numeric range, Found: %s", argument));
					}
				}
			}
		}
	}

	private void validateCommandFilePaths(InputCommand command) {
		for (String filePath : command.getFilePaths()) {
			filePath = FileUtils.toAbsolutePath(filePath);
			if (!FileUtils.checkIfFileExists(filePath)) {
				throw new FileDoesNotExistsException(String.format("File %s not exists!", filePath));
			}
			if (!FileUtils.isFile(filePath)) {
				throw new FileDoesNotExistsException(String.format("Provided path is not a file: %s", filePath));
			}
		}
	}
}
