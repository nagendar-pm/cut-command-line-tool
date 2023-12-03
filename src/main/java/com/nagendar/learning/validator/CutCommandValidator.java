/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:38 am
 */

package com.nagendar.learning.validator;

import com.nagendar.learning.model.Command;

public class CutCommandValidator implements CommandValidator {
	@Override
	public boolean validate(Command command) {
		return false;
	}
}
