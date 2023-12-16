/*
 * @author: pagidimarri.nagendar
 * @createdAt: 16/12/23 11:33 am
 */

package com.nagendar.learning.factory;

import com.nagendar.learning.io.ConsolePrinter;
import com.nagendar.learning.io.FilePrinter;
import com.nagendar.learning.io.Printer;

import java.util.HashMap;
import java.util.Map;

public class PrinterFactory {
	private final Map<Boolean, Printer> printerMap;

	public PrinterFactory() {
		this.printerMap = new HashMap<>();
		printerMap.put(Boolean.TRUE, new ConsolePrinter());
		printerMap.put(Boolean.FALSE, new FilePrinter());
	}

	public Printer getPrinter(boolean isTerminalCommand) {
		return this.printerMap.get(isTerminalCommand);
	}

	public Printer getConsolePrinter() {
		return this.printerMap.get(true);
	}
}
