/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:39 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.model.ProcessedCommand;

public interface CommandExecutor {
	void execute(ProcessedCommand command);
}
