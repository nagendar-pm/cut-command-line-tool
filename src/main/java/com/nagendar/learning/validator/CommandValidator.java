package com.nagendar.learning.validator;
/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:05 am
 */

import com.nagendar.learning.model.InputCommand;

public interface CommandValidator {
	boolean validate(InputCommand command);
}
