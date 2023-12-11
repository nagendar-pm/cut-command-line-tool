/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 12:15 pm
 */

package com.nagendar.learning.service;

import com.nagendar.learning.model.Command;

public interface CommandProcessorService {
	void processCommand(Command command);
}
