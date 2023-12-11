/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:39 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.model.Command;

public interface CommandExecutor {
	default void execute(Command command){}
}
