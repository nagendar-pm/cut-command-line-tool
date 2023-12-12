/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 6:36 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.mode.CommandMode;
import com.nagendar.learning.mode.PipedCommandMode;
import com.nagendar.learning.mode.SingleCommandMode;
import com.nagendar.learning.service.CommandProcessorService;

import java.util.HashMap;
import java.util.Map;

public class CommandModeFactory {
	private final Map<Boolean, CommandMode> commandModeMap;

	public CommandModeFactory(CommandProcessorService commandProcessorService) {
		this.commandModeMap = new HashMap<>();
		commandModeMap.put(Boolean.TRUE, new PipedCommandMode(commandProcessorService));
		commandModeMap.put(Boolean.FALSE, new SingleCommandMode(commandProcessorService));
	}

	public CommandMode getCommandMode(String input) {
		return this.commandModeMap.get(input.contains("|"));
	}
}
