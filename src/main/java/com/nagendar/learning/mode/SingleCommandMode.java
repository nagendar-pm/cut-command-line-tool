/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 6:33pm
 */

package com.nagendar.learning.mode;

import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.service.CommandProcessorService;

public class SingleCommandMode implements CommandMode {
	private final CommandProcessorService commandProcessorService;

	public SingleCommandMode(CommandProcessorService commandProcessorService) {
		this.commandProcessorService = commandProcessorService;
	}
	@Override
	public void process(String input) {
		Command command = new InputCommand(input);
		command.setIsTerminal(true);
		commandProcessorService.processCommand(command);
	}
}
