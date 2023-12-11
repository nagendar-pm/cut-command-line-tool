/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:14 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.option.CharacterCutOptionExecutor;

public class CutCommandExecutor implements CommandExecutor {
	private final Printer printer;

	public CutCommandExecutor(Printer printer) {
		this.printer = printer;
	}

	@Override
	public void execute(Command command) {
		CharacterCutOptionExecutor executor = new CharacterCutOptionExecutor(printer);
		executor.executeOption(command);
	}
}
