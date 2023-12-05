/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:38 am
 */

package com.nagendar.learning.validator;

import com.nagendar.learning.exceptions.FileDoesNotExistsException;
import com.nagendar.learning.exceptions.IllegalFlagException;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.utils.FileUtils;

import static com.nagendar.learning.constants.CommonConstants.ALLOWED_FLAGS;
import static com.nagendar.learning.constants.CommonConstants.ALLOWED_OPTIONS;

public class CutCommandValidator implements CommandValidator {
	@Override
	public boolean validate(Command command) {
		command.parseParams();
		validateCommandFlags(command);
		validateCommandOptions(command);
		validateCommandFilePaths(command);
		return true;
	}

	private void validateCommandFlags(Command command) {
		for (String flag : command.getFlags()) {
			if (!ALLOWED_FLAGS.contains(flag)) {
				throw new IllegalFlagException(String
					.format("Found unexpected flag %s, Expected from %s", flag, ALLOWED_FLAGS));
			}
		}
	}

	private void validateCommandOptions(Command command) {
		for (String option : command.getOptions()) {
			if (!ALLOWED_OPTIONS.contains(option)) {
				throw new IllegalFlagException(String
						.format("Found unexpected option %s, Expected from %s", option, ALLOWED_OPTIONS));
			}
		}
	}

	private void validateCommandFilePaths(Command command) {
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
