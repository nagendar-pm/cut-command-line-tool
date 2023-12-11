/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 9:57 pm
 */

package com.nagendar.learning.validator;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.model.Command;

public class ExitCommandValidator implements CommandValidator {
	@Override
	public boolean validate(Command command) {
		return command.getCommandType().equals(CommonConstants.EXIT_COMMAND);
	}
}
