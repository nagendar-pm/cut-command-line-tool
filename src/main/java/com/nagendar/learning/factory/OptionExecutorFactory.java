/*
 * @author: pagidimarri.nagendar
 * @createdAt: 13/12/23 10:16 pm
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.option.ByteCutOptionExecutor;
import com.nagendar.learning.option.CharacterCutOptionExecutor;
import com.nagendar.learning.option.FieldCutOptionExecutor;
import com.nagendar.learning.option.OptionExecutor;

import java.util.HashMap;
import java.util.Map;

public class OptionExecutorFactory {
	Map<String, OptionExecutor> optionExecutorMap;

	public OptionExecutorFactory(PrinterFactory printerFactory) {
		optionExecutorMap = new HashMap<>();
		optionExecutorMap.put(CommonConstants.OPTION_BYTE_LIST, new ByteCutOptionExecutor(printerFactory));
		optionExecutorMap.put(CommonConstants.OPTION_CHARACTER_LIST, new CharacterCutOptionExecutor(printerFactory));
		optionExecutorMap.put(CommonConstants.OPTION_FIELD_SPECIFIER, new FieldCutOptionExecutor(printerFactory));
	}

	public OptionExecutor getOptionExecutor(String option) {
		return optionExecutorMap.get(option);
	}
}
