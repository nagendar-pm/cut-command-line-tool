/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 12:03 pm
 */

package com.nagendar.learning.model;

public abstract class Command {
	private String rawCommandString;

	protected Command(String rawCommandString) {
		this.rawCommandString = rawCommandString;
	}

	public Command() {

	}

	public String getRawCommandString() {
		return rawCommandString;
	}
}
