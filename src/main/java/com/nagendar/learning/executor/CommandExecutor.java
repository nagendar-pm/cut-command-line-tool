/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:39 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.model.ProcessedCommand;

public interface CommandExecutor {
	default void execute(ProcessedCommand command){}

	default void execute(InputCommand command){}
}
