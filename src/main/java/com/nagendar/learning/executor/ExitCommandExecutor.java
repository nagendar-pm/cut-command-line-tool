/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:04 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.factory.PrinterFactory;
import com.nagendar.learning.model.Command;

public class ExitCommandExecutor implements CommandExecutor {
	private final PrinterFactory printerFactory;

	public ExitCommandExecutor(PrinterFactory printerFactory) {
		this.printerFactory = printerFactory;
	}

	@Override
	public void execute(Command command) {
		printerFactory.getPrinter(command.getIsTerminal()).print("Shutting down...", false);
		System.exit(0);
	}
}
