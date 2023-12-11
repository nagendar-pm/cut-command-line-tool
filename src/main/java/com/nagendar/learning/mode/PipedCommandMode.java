/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 6:34pm
 */

package com.nagendar.learning.mode;

import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.service.CommandProcessorService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static com.nagendar.learning.constants.CommonConstants.*;

public class PipedCommandMode implements CommandMode {
	private final CommandProcessorService commandProcessorService;

	public PipedCommandMode(CommandProcessorService commandProcessorService) {
		this.commandProcessorService = commandProcessorService;
	}
	@Override
	public void process(String input) {
		// 1. List the commands split by the | operator
		// 2. Run whatever commands we get by using runtime process
		// 3. Store the output of other commands in the file and use it with the wc later
		// For now we will think of wc as a terminal command
		LinkedList<String> commands = Arrays.stream(input.split(PIPE_OPERATOR))
				.map(String::trim)
				.collect(Collectors.toCollection(LinkedList::new));

		int size = commands.size();
		boolean isFirstCommand = true;
		while (size-- > 0) {
			String commandStr = commands.poll();
			commandStr = buildCommandWithFilepath(commandStr, isFirstCommand);
			Command command = new InputCommand(commandStr);
			commandProcessorService.processCommand(command);
			isFirstCommand = false;
		}
	}

	private String buildCommandWithFilepath(String command, boolean isFirstCommand) {
		if (isFirstCommand) return command;
		return command + WHITESPACE_DELIMITER + TEMP_FILE_PATH;
	}
}
