/*
 * @author: pagidimarri.nagendar
 * @createdAt: 13/12/23 10:16 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.option.ByteCutOptionExecutor;
import com.nagendar.learning.option.CharacterCutOptionExecutor;
import com.nagendar.learning.option.OptionExecutor;

import java.util.HashMap;
import java.util.Map;

public class OptionExecutorFactory {
	Map<String, OptionExecutor> optionExecutorMap;

	public OptionExecutorFactory(Printer consolePrinter, Printer filePrinter) {
		optionExecutorMap = new HashMap<>();
		optionExecutorMap.put(CommonConstants.OPTION_BYTE_LIST, new ByteCutOptionExecutor(consolePrinter, filePrinter));
		optionExecutorMap.put(CommonConstants.OPTION_CHARACTER_LIST, new CharacterCutOptionExecutor(consolePrinter, filePrinter));
//		optionExecutorMap.put(CommonConstants.OPTION_FIELD_SPECIFIER, new CharacterCountOptionExecutor(printer));
	}

	public OptionExecutor getOptionExecutor(String option) {
		return optionExecutorMap.get(option);
	}
}
