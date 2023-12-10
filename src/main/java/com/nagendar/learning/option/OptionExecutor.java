/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:17 pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.model.ProcessedCommand;

public interface OptionExecutor {
	void executeOption(ProcessedCommand processedCommand);
}
