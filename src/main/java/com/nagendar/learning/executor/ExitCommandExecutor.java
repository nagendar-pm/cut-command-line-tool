/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:04 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.InputCommand;

public class ExitCommandExecutor implements CommandExecutor {
	private final Printer printer;

	public ExitCommandExecutor(Printer printer) {
		this.printer = printer;
	}

	@Override
	public void execute(Command command) {
		printer.print("Shutting down...");
		System.exit(0);
	}
}
