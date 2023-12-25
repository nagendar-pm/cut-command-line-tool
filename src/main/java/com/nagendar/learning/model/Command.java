/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 12:03 pm
 */

package com.nagendar.learning.model;

import static com.nagendar.learning.constants.CommonConstants.*;

public abstract class Command {
	private String rawCommandString;
	private String commandType;
	private boolean isTerminal;

	protected Command(String rawCommandString) {
		this.rawCommandString = rawCommandString;
	}

	public Command() {}

	public String getRawCommandString() {
		return rawCommandString;
	}

	public void setCommandType(String commandType) {
		if (!(commandType.equals(CUT_COMMAND)
				|| commandType.equals(EXIT_COMMAND))) {
			this.commandType = NON_CUT_COMMAND;
		}
		else {
			this.commandType = commandType;
		}
	}

	public String getCommandType() {
		return commandType;
	}

	public boolean getIsTerminal() {
		return isTerminal;
	}

	public void setIsTerminal(boolean isTerminal) {
		this.isTerminal = isTerminal;
	}
}
