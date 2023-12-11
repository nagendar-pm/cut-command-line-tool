/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 9:56 pm
 */

package com.nagendar.learning.validator;

import com.nagendar.learning.model.Command;

public class NonCutCommandValidator implements CommandValidator {
	@Override
	public boolean validate(Command command) {
		return true;
	}
}
